package impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.inject.Qualifier;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;

import api.BeanType;
import api.Binding;
import api.InstancesManager;
import api.InterceptorBinding;
import api.NanoContainer;
import api.Producer;
import api.Registry;
import api.exception.NanoContainerException;
import api.exception.NanoContainerRuntimeException;

public class NanoContainerImpl implements NanoContainer {
	
	private InstancesManager instancesManager = new InstancesManagerImpl((NanoContainer)this);
	private Registry registry = new RegistryImpl();
	
	@SuppressWarnings("unchecked")
	public <T> T getInstance(BeanType beanType) {
		if(!this.registry.isInjectable(beanType)) {
			throw new NanoContainerRuntimeException("Unregisted bean type : " + beanType.getDescription());
		}
		T beanInstance = null;
		Binding binding = this.registry.getBinding(beanType);
		if(binding != null) {
			try {
				beanInstance = this.instancesManager.getInstance(binding);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		else {
			Producer producer = this.registry.getProducer(beanType);
			Class<?> declaringClass = producer.getDeclaringClass();
			BeanType declaringClassBeanType = new BeanTypeImpl(declaringClass, this.getQualifiers(declaringClass));
			if(!this.registry.isInjectable(declaringClassBeanType)) {
				throw new NanoContainerRuntimeException("Unregisted producer bean type : " + declaringClassBeanType.getDescription());
			}
			if(declaringClassBeanType == beanType) {
				throw new NanoContainerRuntimeException("The producer bean type : " + declaringClassBeanType.getDescription() + " should not be produced using one of its method.");
			}
			try {
				beanInstance = this.instancesManager.getInstance(producer);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		InvocationHandler invocationHandler = new NanoContainerInvocationHandlerImpl(this, beanInstance, beanType);
		if(beanType.getType().isInterface()) {
			return (T)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] {beanType.getType()}, invocationHandler);
		}
		else if(beanInstance.getClass().getInterfaces().length > 0) {
			return (T)Proxy.newProxyInstance(this.getClass().getClassLoader(), this.getAllInterfaces(beanInstance.getClass()), invocationHandler);
		}
		else {
			throw new NanoContainerRuntimeException("The bean type : " + beanType.getDescription() + " (or its superclasses) have no interface. Unable to proxy it, it may be used only for producers method.");
		}
	}
	
	public <T> T getInstance(Class<?> type) {
		return getInstance(new BeanTypeImpl(type));
	}
	
	public InstancesManager getInstancesManager() {
		return instancesManager;
	}
	
	public Set<Annotation> getInterceptorBindings(Class<?> implementation) {
		Set<Annotation> result = new HashSet<Annotation>();		
		for(Annotation annotation : implementation.getAnnotations()) {
			if(annotation.annotationType().isAnnotationPresent(javax.interceptor.InterceptorBinding.class)) {
				result.add(annotation);
			}
		}
		return result;
	}

	public Set<Annotation> getInterceptorBindings(Method method) {
		Set<Annotation> result = new HashSet<Annotation>();		
		for(Annotation annotation : method.getAnnotations()) {
			if(annotation.annotationType().isAnnotationPresent(javax.interceptor.InterceptorBinding.class)) {
				result.add(annotation);
			}
		}
		return result;
	}
	
	public api.Interceptor[] getInterceptors(BeanType callingType, Method callingMethod) {
		List<api.Interceptor> result = new ArrayList<api.Interceptor>();
		Set<Annotation> qualifiers = this.getInterceptorBindings(callingType.getType());
		qualifiers = this.getInterceptorBindings(callingMethod);
		List<InterceptorBinding> interceptorBindings = this.registry.getInterceptorBindings(qualifiers);
		Method method = null;
		Object[] parameters = null;
		Object target = null;
		for(InterceptorBinding interceptorBinding : interceptorBindings) {
			method = interceptorBinding.getMethod();
			parameters = this.instancesManager.getInstanciedParameters(method);
			target = this.getNewInterceptorInstance(interceptorBinding.getType());
			result.add(new InterceptorImpl(method, parameters, target));
		}
		return result.toArray(new api.Interceptor[0]);
	}
	
	public <T> T getNewProducerInstance(BeanType beanType) {
		if(!this.registry.isInjectable(beanType)) {
			throw new NanoContainerRuntimeException("Unregisted bean type : " + beanType.getDescription());
		}
		T beanInstance = null;
		Binding binding = this.registry.getBinding(beanType);
		if(binding != null) {
			try {
				beanInstance = this.instancesManager.createNewProducerInstance(binding);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		else {
			Producer producer = this.registry.getProducer(beanType);
			Class<?> declaringClass = producer.getDeclaringClass();
			BeanType declaringClassBeanType = new BeanTypeImpl(declaringClass, this.getQualifiers(declaringClass));
			if(!this.registry.isInjectable(declaringClassBeanType)) {
				throw new NanoContainerRuntimeException("Unregisted producer bean type : " + declaringClassBeanType.getDescription());
			}
			if(declaringClassBeanType == beanType) {
				throw new NanoContainerRuntimeException("The producer bean type : " + declaringClassBeanType.getDescription() + " should not be produced using one of its method.");
			}
			try {
				beanInstance = this.instancesManager.createNewProducerInstance(producer);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		return beanInstance;
	}
	
	public <T> T getNewProducerInstance(Class<?> type) {
		return getNewUnmanagedInstance(new BeanTypeImpl(type));
	}
	
	public <T> T getNewUnmanagedInstance(BeanType beanType) {
		if(!this.registry.isInjectable(beanType)) {
			throw new NanoContainerRuntimeException("Unregisted bean type : " + beanType.getDescription());
		}
		T beanInstance = null;
		Binding binding = this.registry.getBinding(beanType);
		if(binding != null) {
			try {
				beanInstance = this.instancesManager.createNewInstance(binding);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		else {
			Producer producer = this.registry.getProducer(beanType);
			Class<?> declaringClass = producer.getDeclaringClass();
			BeanType declaringClassBeanType = new BeanTypeImpl(declaringClass, this.getQualifiers(declaringClass));
			if(!this.registry.isInjectable(declaringClassBeanType)) {
				throw new NanoContainerRuntimeException("Unregisted producer bean type : " + declaringClassBeanType.getDescription());
			}
			if(declaringClassBeanType == beanType) {
				throw new NanoContainerRuntimeException("The producer bean type : " + declaringClassBeanType.getDescription() + " should not be produced using one of its method.");
			}
			try {
				beanInstance = this.instancesManager.createNewInstance(producer);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		return beanInstance;
	}
	
	public <T> T getNewUnmanagedInstance(Class<?> type) {
		return getNewUnmanagedInstance(new BeanTypeImpl(type));
	}

	public Set<Annotation> getQualifiers(Annotation[] annotations) {
		Set<Annotation> result = new HashSet<Annotation>();		
		for(Annotation annotation : annotations) {
			if(annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
				result.add(annotation);
			}
		}
		return result;
	}
	
	public Set<Annotation> getQualifiers(Class<?> implementation) {
		Set<Annotation> result = new HashSet<Annotation>();		
		for(Annotation annotation : implementation.getAnnotations()) {
			if(annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
				result.add(annotation);
			}
		}
		return result;
	}
	
	public Set<Annotation> getQualifiers(Field field) {
		Set<Annotation> result = new HashSet<Annotation>();		
		for(Annotation annotation : field.getAnnotations()) {
			if(annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
				result.add(annotation);
			}
		}
		return result;
	}
	
	public Set<Annotation> getQualifiers(Method method) {
		Set<Annotation> result = new HashSet<Annotation>();		
		for(Annotation annotation : method.getAnnotations()) {
			if(annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
				result.add(annotation);
			}
		}
		return result;
	}
	
	public Registry getRegistry() {
		return registry;
	}
	
	public void register(Class<?> implementation) {
		int modifiers = implementation.getModifiers();
		if(Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
			throw new NanoContainerRuntimeException("Unable to register interface, array or abstract class. You may provide an instanciable class, " + implementation.getSimpleName() + " is not.");
		}
		List<Binding> bindings = this.getBindings(implementation);
		List<Producer> producers = this.getProducers(implementation);
		List<InterceptorBinding> interceptors = this.getInterceptors(implementation);
		if(bindings.size() > 0) {
			try {
				this.registry.addBindings(bindings);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
			try {
				this.registry.addProducers(producers);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
			try {
				this.registry.addInterceptors(interceptors);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		else if(producers.size() > 0 || interceptors.size() > 0) {
			try {
				this.registry.addProducers(producers);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
			try {
				this.registry.addInterceptors(interceptors);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
			Set<Annotation> qualifiers = this.getQualifiers(implementation);
			try {
				this.registry.addBinding(new BindingImpl(implementation, qualifiers, implementation));
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		else {			
			throw new NanoContainerRuntimeException("Unable to register unproxyable class. The class " + implementation.getSimpleName() + " (or its superclasses) may implement at least one interface or define producers or interceptor method.");
		}
	}
	
	public void start() {
		
	}
	
	public void stop() {
		try {
			this.instancesManager.terminate();
		} catch (NanoContainerException e) {
			throw new NanoContainerRuntimeException(e.getMessage());
		}
		this.registry.close();
	}
	
	private Class<?>[] getAllInterfaces(Class<?> implementation) {
		List<Class<?>> result = new ArrayList<Class<?>>();
		result.addAll(Arrays.asList(implementation.getInterfaces()));
		Class<?> superClass = implementation.getSuperclass();
		while(superClass != null) {
			result.addAll(Arrays.asList(superClass.getInterfaces()));
			superClass = superClass.getSuperclass();
		}
		return result.toArray(new Class<?>[0]);
	}

	private List<Binding> getBindings(Class<?> implementation) {
		List<Binding> result = new ArrayList<Binding>();
		Set<Annotation> qualifiers = this.getQualifiers(implementation);
		if(this.getAllInterfaces(implementation).length > 0 ) {
			result.add(new BindingImpl(implementation, qualifiers, implementation));
		}		
		for(Class<?> type : implementation.getInterfaces()) {
			result.add(new BindingImpl(type, qualifiers, implementation));
		}
		return result;
	}
	
	private List<InterceptorBinding> getInterceptors(Class<?> implementation) {
		List<InterceptorBinding> result = new ArrayList<InterceptorBinding>();
		if(implementation.isAnnotationPresent(Interceptor.class)) {
			Set<Annotation> classQualifiers = this.getInterceptorBindings(implementation);
			Set<Annotation> qualifiers = null;
			for(Method method : implementation.getDeclaredMethods()) {
				if(method.isAnnotationPresent(AroundInvoke.class)) {
					qualifiers = this.getInterceptorBindings(method);
					qualifiers.addAll(classQualifiers);
					result.add(new InterceptorBindingImpl(new BeanTypeImpl(implementation, this.getQualifiers(implementation)), method, qualifiers));
				}
			}
		}
		return result;
	}

	private <T> T getNewInterceptorInstance(BeanType type) {
		if(!this.registry.isInjectable(type)) {
			throw new NanoContainerRuntimeException("Unregisted bean type : " + type.getDescription());
		}
		T beanInstance = null;
		Binding binding = this.registry.getBinding(type);
		if(binding != null) {
			try {
				beanInstance = this.instancesManager.createNewInterceptorInstance(binding);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		else {
			Producer producer = this.registry.getProducer(type);
			Class<?> declaringClass = producer.getDeclaringClass();
			BeanType declaringClassBeanType = new BeanTypeImpl(declaringClass, this.getQualifiers(declaringClass));
			if(!this.registry.isInjectable(declaringClassBeanType)) {
				throw new NanoContainerRuntimeException("Unregisted producer bean type : " + declaringClassBeanType.getDescription());
			}
			try {
				beanInstance = this.instancesManager.createNewInterceptorInstance(producer);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		return beanInstance;
	}

	private List<Producer> getProducers(Class<?> implementation) {
		List<Producer> result = new ArrayList<Producer>();
		Set<Annotation> qualifiers = null;
		for(Method method : implementation.getDeclaredMethods()) {
			if(method.isAnnotationPresent(Produces.class)) {
				qualifiers = this.getQualifiers(method);
				result.add(new ProducerImpl(method.getReturnType(), qualifiers, method));
			}
		}
		return result;
	}
	
}
