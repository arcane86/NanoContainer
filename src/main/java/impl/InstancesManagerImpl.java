package impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import api.BeanType;
import api.Binding;
import api.InstancesManager;
import api.NanoContainer;
import api.Producer;
import api.exception.NanoContainerException;

/**
 * @author hp
 *
 */
public class InstancesManagerImpl implements InstancesManager {

	/**
	 * 
	 */
	private NanoContainer container;
	/**
	 * 
	 */
	private Map<Binding,Object> instances;
	
	/**
	 * @param container
	 */
	public InstancesManagerImpl(NanoContainer container) {
		this.instances = new HashMap<Binding, Object>();
		this.container = container;
	}
	
	/* (non-Javadoc)
	 * @see api.InstancesManager#createNewInstance(api.Binding)
	 */
	public <T> T createNewInstance(Binding binding) throws NanoContainerException {
		Class<?> bean = binding.getImplementation();
		@SuppressWarnings("unchecked")
		T instance = (T)this.construct(bean);
		this.initialize(bean, instance);
		this.postConstruct(bean, instance);
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see api.InstancesManager#createNewInstance(api.Producer)
	 */
	public <T> T createNewInstance(Producer producer) throws NanoContainerException {
		Class<?> bean = producer.getMethod().getReturnType();
		@SuppressWarnings("unchecked")
		T instance = (T)this.produce(bean, producer);
		this.initialize(bean, instance);
		this.postConstruct(bean, instance);
		return instance;
	}

	/* (non-Javadoc)
	 * @see api.InstancesManager#createNewInterceptorInstance(api.Binding)
	 */
	public <T> T createNewInterceptorInstance(Binding binding) throws NanoContainerException {
		Class<?> bean = binding.getImplementation();
		@SuppressWarnings("unchecked")
		T instance = (T)this.construct(bean);
		return instance;
	}
	
	
	/* (non-Javadoc)
	 * @see api.InstancesManager#createNewInterceptorInstance(api.Producer)
	 */
	public <T> T createNewInterceptorInstance(Producer producer) throws NanoContainerException {
		Class<?> bean = producer.getMethod().getReturnType();
		@SuppressWarnings("unchecked")
		T instance = (T)this.produce(bean, producer);
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see api.InstancesManager#createNewProducerInstance(api.Binding)
	 */
	public <T> T createNewProducerInstance(Binding binding) throws NanoContainerException {
		Class<?> bean = binding.getImplementation();
		@SuppressWarnings("unchecked")
		T instance = (T)this.construct(bean);
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see api.InstancesManager#createNewProducerInstance(api.Producer)
	 */
	public <T> T createNewProducerInstance(Producer producer) throws NanoContainerException {
		Class<?> bean = producer.getMethod().getReturnType();
		@SuppressWarnings("unchecked")
		T instance = (T)this.produce(bean, producer);
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see api.InstancesManager#getContainer()
	 */
	public NanoContainer getContainer() {
		return this.container;
	}
	
	/* (non-Javadoc)
	 * @see api.InstancesManager#getInstance(api.Binding)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getInstance(Binding binding) throws NanoContainerException {
		if(this.instances.containsKey(binding)) {
			return (T)this.instances.get(binding);
		}
		return this.createInstance(binding);
	}
	
	/* (non-Javadoc)
	 * @see api.InstancesManager#getInstance(api.Producer)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getInstance(Producer producer) throws NanoContainerException {
		Binding binding = new BindingImpl(producer.getType(), producer.getMethod().getReturnType());
		if(this.instances.containsKey(binding)) {
			return (T)this.instances.get(binding);
		}
		return this.createInstance(producer);
	}
	
	/* (non-Javadoc)
	 * @see api.InstancesManager#getInstances()
	 */
	public Map<Binding, Object> getInstances() {
		return this.instances;
	}
	
	/* (non-Javadoc)
	 * @see api.InstancesManager#getInstanciedParameters(java.lang.reflect.Method)
	 */
	public Object[] getInstanciedParameters(Method method) {
		Class<?>[] parameters = method.getParameterTypes();
		if(parameters.length == 0) {
			return new Object[0];
		}
		Object[] instanciedParameters = new Object[parameters.length];
		Annotation[][] annotations = method.getParameterAnnotations();
		BeanType beanType= null;
		Set<Annotation> qualifiers = null;
		Class<?> type = null;
		for(int i = 0;i < parameters.length;i++) {
			type = parameters[i];
			qualifiers = this.container.getQualifiers(annotations[i]);
			beanType = new BeanTypeImpl(type, qualifiers);
			instanciedParameters[i] = this.container.getInstance(beanType);
		}
		return instanciedParameters;
	}
	
	/* (non-Javadoc)
	 * @see api.InstancesManager#terminate()
	 */
	public void terminate() throws NanoContainerException {
		for(Entry<Binding, Object> instance : this.instances.entrySet()) {
			this.preDestroy(instance.getKey().getImplementation(),instance.getValue());
		}
		this.instances.clear();
	}

	/**
	 * @param <T>
	 * @param bean
	 * @return
	 * @throws NanoContainerException
	 */
	@SuppressWarnings("unchecked")
	private <T> T construct(Class<T> bean) throws NanoContainerException {
		T instance = null;
		Constructor<?> constructor = this.getInjectionConstructor(bean);
		Object[] parameters = this.getInstanciedParameters(constructor);
		boolean accessible = constructor.isAccessible();
		if(!accessible) {
			constructor.setAccessible(true);
		}
		try {
			instance = (T)constructor.newInstance(parameters);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		finally {
			if(!accessible) {
				constructor.setAccessible(accessible);
			}
		}
		return instance;
	}
	
	/**
	 * @param <T>
	 * @param binding
	 * @return
	 * @throws NanoContainerException
	 */
	private <T> T createInstance(Binding binding) throws NanoContainerException {
		T instance = this.createNewInstance(binding);
		this.instances.put(binding, instance);
		return instance;
	}
	
	/**
	 * @param <T>
	 * @param producer
	 * @return
	 * @throws NanoContainerException
	 */
	private <T> T createInstance(Producer producer) throws NanoContainerException {
		T instance = this.createNewInstance(producer);
		Binding binding = new BindingImpl(producer.getType(), producer.getMethod().getReturnType());
		this.instances.put(binding, instance);
		return instance;
	}
	
	/**
	 * @param <T>
	 * @param bean
	 * @param instance
	 * @throws NanoContainerException
	 */
	private <T> void fieldInjection(Class<T> bean, Object instance) throws NanoContainerException {
		Field[] fields = bean.getDeclaredFields();
		for(Field field : fields) {
			if(field.isAnnotationPresent(Inject.class)) {
				boolean accessible = field.isAccessible();
				if(!accessible) {
					field.setAccessible(true);
				}
				try {
					field.set(instance, this.getInstanciedField(field));
				} catch (Exception e) {
					throw new NanoContainerException("Unable to perform the field injection of a " + field.getType().getSimpleName() + " in " + instance + " due to " + e.getClass().getSimpleName() + " : " + e.getMessage());
				}
				finally {
					if(!accessible) {
						field.setAccessible(accessible);
					}
				}
			}
		}
	}
	
	/**
	 * @param bean
	 * @return
	 * @throws NanoContainerException
	 */
	private Constructor<?> getInjectionConstructor(Class<?> bean) throws NanoContainerException {
		Constructor<?>[] constructors = bean.getDeclaredConstructors();
		List<Constructor<?>> npConstructors = new ArrayList<Constructor<?>>();
		List<Constructor<?>> injectedConstructors = new ArrayList<Constructor<?>>();
		
		for(Constructor<?> constructor : constructors) {
			if(constructor.isAnnotationPresent(Inject.class)) {
				injectedConstructors.add(constructor);
			}
			if((constructor.getParameterTypes().length == 0) && (Modifier.isPublic(constructor.getModifiers()))) {
				npConstructors.add(constructor);			
			}
		}
		
		if(injectedConstructors.size() > 0) {
			if(injectedConstructors.size() == 1) {
				return injectedConstructors.get(0);
			}
			throw new NanoContainerException("More than one @Inject constructor in " + bean.getSimpleName() + ". You may define only one @Inject annotated constructor.");
		}
		if(npConstructors.size() > 0) {
			return npConstructors.get(0);
		}		
		throw new NanoContainerException("No constructor available for injector in " + bean.getSimpleName() + ". You may define a @Inject annotated constructor or a public np-constructor.");
	}
	
	/**
	 * @param <T>
	 * @param field
	 * @return
	 */
	private <T> T getInstanciedField(Field field) {
		Set<Annotation> qualifiers = this.container.getQualifiers(field);
		BeanType beanType = new BeanTypeImpl(field.getType(),qualifiers);		
		return this.container.getInstance(beanType);
	}
	
	/**
	 * @param constructor
	 * @return
	 */
	private Object[] getInstanciedParameters(Constructor<?> constructor) {
		Class<?>[] parameters = constructor.getParameterTypes();
		if(parameters.length == 0) {
			return new Object[0];
		}
		Object[] instanciedParameters = new Object[parameters.length];
		Annotation[][] annotations = constructor.getParameterAnnotations();
		BeanType beanType= null;
		Set<Annotation> qualifiers = null;
		Class<?> type = null;
		for(int i = 0;i < parameters.length;i++) {
			type = parameters[i];
			qualifiers = this.container.getQualifiers(annotations[i]);
			beanType = new BeanTypeImpl(type, qualifiers);
			instanciedParameters[i] = this.container.getInstance(beanType);
		}
		return instanciedParameters;
	}
	
	/**
	 * @param <T>
	 * @param bean
	 * @param instance
	 * @throws NanoContainerException
	 */
	private <T> void initialize(Class<?> bean, T instance) throws NanoContainerException {
		mutatorInjection(bean, instance);
		fieldInjection(bean, instance);
	}
	
	/**
	 * @param <T>
	 * @param bean
	 * @param instance
	 * @throws NanoContainerException
	 */
	private <T> void mutatorInjection(Class<T> bean, Object instance) throws NanoContainerException {
		Method[] methods = bean.getDeclaredMethods();
		for(Method method : methods) {
			if(method.isAnnotationPresent(Inject.class)) {
				Object[] parameters = this.getInstanciedParameters(method);
				boolean accessible = method.isAccessible();
				if(!accessible) {
					method.setAccessible(true);
				}
				try {
					method.invoke(instance, parameters);
				} catch (Exception e) {
					throw new NanoContainerException("Unable to perform the mutator injection with " + method.getName() + " in " + instance + " due to " + e.getClass().getSimpleName() + " : " + e.getMessage());
				}
				finally {
					if(!accessible) {
						method.setAccessible(accessible);
					}
				}
			}
		}
	}
	
	/**
	 * @param <T>
	 * @param bean
	 * @param instance
	 * @throws NanoContainerException
	 */
	private <T> void postConstruct(Class<?> bean, T instance) throws NanoContainerException {
		Method[] methods = bean.getDeclaredMethods();
		for(Method method : methods) {
			if(method.isAnnotationPresent(PostConstruct.class)) {
				Class<?>[] parameters = method.getParameterTypes();
				if(parameters.length > 0) {
					throw new NanoContainerException("PostConstruct method may have no parameter");
				}
				try {
					boolean accessible = method.isAccessible();
					if(!accessible) {
						method.setAccessible(true);
					}
					method.invoke(instance);
					if(!accessible) {
						method.setAccessible(accessible);
					}
				} catch (Exception e) {
					throw new NanoContainerException("Unable to perform the post construction of " + instance + " due to " + e.getClass().getSimpleName() + " : " + e.getMessage());
				}
			}
		}
	}

	/**
	 * @param bean
	 * @param instance
	 * @throws NanoContainerException
	 */
	private void preDestroy(Class<?> bean, Object instance) throws NanoContainerException {
		Method[] methods = bean.getDeclaredMethods();
		for(Method method : methods) {
			if(method.isAnnotationPresent(PreDestroy.class)) {
				Class<?>[] parameters = method.getParameterTypes();
				if(parameters.length > 0) {
					throw new NanoContainerException("PreDestroy method may have no parameter");
				}
				try {
					boolean accessible = method.isAccessible();
					if(!accessible) {
						method.setAccessible(true);
					}
					method.invoke(instance);
					if(!accessible) {
						method.setAccessible(accessible);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param <T>
	 * @param bean
	 * @param producer
	 * @return
	 * @throws NanoContainerException
	 */
	@SuppressWarnings("unchecked")
	private <T> T produce(Class<T> bean, Producer producer) throws NanoContainerException {
		T instance = null;
		Method method = producer.getMethod();
		Object[] parameters = this.getInstanciedParameters(method);
		boolean accessible = method.isAccessible();
		if(!accessible) {
			method.setAccessible(true);
		}
		Class<?> declaringClass = producer.getDeclaringClass();
		BeanType declaringClassBeanType = new BeanTypeImpl(declaringClass, this.container.getQualifiers(declaringClass));
		try {
			instance = (T)method.invoke(this.container.getNewProducerInstance(declaringClassBeanType), parameters);
		} catch (Exception e) {
			throw new NanoContainerException("Unable to produce a " + bean.getSimpleName() + " with " + producer.getMethod().getName() + " due to " + e.getClass().getSimpleName() + " : " + e.getMessage());
		}
		return instance;
	}
}
