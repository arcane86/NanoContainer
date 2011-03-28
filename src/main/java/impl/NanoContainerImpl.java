package impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.inject.Qualifier;

import api.BeanType;
import api.Binding;
import api.InstancesManager;
import api.NanoContainer;
import api.Producer;
import api.Registry;
import api.exception.NanoContainerException;
import api.exception.NanoContainerRuntimeException;

public class NanoContainerImpl implements NanoContainer {
	
	private Registry registry = new RegistryImpl();
	private InstancesManager instancesManager = new InstancesManagerImpl((NanoContainer)this);
	
	public void register(Class<?> implementation) {
		int modifiers = implementation.getModifiers();
		if(Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
			throw new NanoContainerRuntimeException("Unable to register interface, array or abstract class. You may provide an instanciable class.");
		}
		List<Binding> bindings = this.getBindings(implementation);
		try {
			this.registry.addBindings(bindings);
		} catch (NanoContainerException e) {
			throw new NanoContainerRuntimeException(e.getMessage());
		}
		List<Producer> producers = this.getProducers(implementation);
		try {
			this.registry.addProducers(producers);
		} catch (NanoContainerException e) {
			throw new NanoContainerRuntimeException(e.getMessage());
		}
	}
	
	private List<Binding> getBindings(Class<?> implementation) {
		List<Binding> result = new ArrayList<Binding>();
		Set<Annotation> qualifiers = this.getQualifiers(implementation);
		result.add(new BindingImpl(implementation, qualifiers, implementation));
		for(Class<?> type : implementation.getInterfaces()) {
			result.add(new BindingImpl(type, qualifiers, implementation));
		}
		Class<?> superClass = implementation.getSuperclass();
		while(superClass != null && superClass != Object.class) {
			result.add(new BindingImpl(superClass, qualifiers, implementation));
			superClass = superClass.getSuperclass();
		}
		return result;
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
	
	public Set<Annotation> getQualifiers(Class<?> implementation) {
		Set<Annotation> result = new HashSet<Annotation>();		
		for(Annotation annotation : implementation.getAnnotations()) {
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
	
	public Set<Annotation> getQualifiers(Field field) {
		Set<Annotation> result = new HashSet<Annotation>();		
		for(Annotation annotation : field.getAnnotations()) {
			if(annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
				result.add(annotation);
			}
		}
		return result;
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
	
	public <T> T getInstance(Class<?> type) {
		return getInstance(new BeanTypeImpl(type));
	}
	
	public <T> T getInstance(BeanType beanType) {
		if(!this.registry.isRegistred(beanType)) {
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
			if(!this.registry.isRegistred(declaringClassBeanType)) {
				throw new NanoContainerRuntimeException("Unregisted producer bean type : " + declaringClassBeanType.getDescription());
			}
			try {
				beanInstance = this.instancesManager.getInstance(producer);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		return beanInstance;
	}
	
	public <T> T getNewUnmanagedInstance(Class<?> type) {
		return getNewUnmanagedInstance(new BeanTypeImpl(type));
	}
	
	public <T> T getNewUnmanagedInstance(BeanType beanType) {
		if(!this.registry.isRegistred(beanType)) {
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
			if(!this.registry.isRegistred(declaringClassBeanType)) {
				throw new NanoContainerRuntimeException("Unregisted producer bean type : " + declaringClassBeanType.getDescription());
			}
			try {
				beanInstance = this.instancesManager.createNewInstance(producer);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		return beanInstance;
	}
	
	public <T> T getNewProducerInstance(Class<?> type) {
		return getNewUnmanagedInstance(new BeanTypeImpl(type));
	}
	
	public <T> T getNewProducerInstance(BeanType beanType) {
		if(!this.registry.isRegistred(beanType)) {
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
			if(!this.registry.isRegistred(declaringClassBeanType)) {
				throw new NanoContainerRuntimeException("Unregisted producer bean type : " + declaringClassBeanType.getDescription());
			}
			try {
				beanInstance = this.instancesManager.createNewProducerInstance(producer);
			} catch (NanoContainerException e) {
				throw new NanoContainerRuntimeException(e.getMessage());
			}
		}
		return beanInstance;
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

	public Registry getRegistry() {
		return registry;
	}

	public InstancesManager getInstancesManager() {
		return instancesManager;
	}
	
}
