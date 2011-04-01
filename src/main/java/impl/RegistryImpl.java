package impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import api.BeanType;
import api.Binding;
import api.InterceptorBinding;
import api.Producer;
import api.Registry;
import api.exception.NanoContainerException;

/**
 * Implementation of the registry used by Nanocontainer to memorize the registed
 *  {@link Binding}, {@link Producer} and {@link InterceptorBinding}.
 * @author Matthieu Clochard
 */
public class RegistryImpl implements Registry {
	
	/**
	 * The list of all registered {@link Binding}.
	 */
	private Map<BeanType, Binding> bindings = new HashMap<BeanType, Binding>();
	/**
	 * The list of all registered {@link InterceptorBinding}.
	 */
	private Map<BeanType, InterceptorBinding> interceptors = new HashMap<BeanType, InterceptorBinding>();
	/**
	 * The list of all registered {@link Producer}.
	 */
	private Map<BeanType, Producer> producers = new HashMap<BeanType, Producer>();
	
	/* (non-Javadoc)
	 * @see api.Registry#addBinding(api.Binding)
	 */
	public void addBinding(Binding binding) throws NanoContainerException {
		BeanType type = binding.getType();		
		if(this.hasBinding(type)) {
			throw new NanoContainerException("The type " + type + " is already registred.");
		}
		this.bindings.put(type, binding);		
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#addBindings(java.util.List)
	 */
	public void addBindings(List<Binding> bindings) throws NanoContainerException {
		for(Binding binding : bindings) {
			this.addBinding(binding);
		}
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#addInterceptor(api.InterceptorBinding)
	 */
	public void addInterceptor(InterceptorBinding interceptor) throws NanoContainerException {
		BeanType type = interceptor.getType();
		if(this.hasInterceptor(type)) {
			throw new NanoContainerException("The type " + type + " is already registred.");
		}
		this.interceptors.put(type, interceptor);
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#addInterceptors(java.util.List)
	 */
	public void addInterceptors(List<InterceptorBinding> interceptors) throws NanoContainerException {
		for(InterceptorBinding interceptor : interceptors) {
			this.addInterceptor(interceptor);
		}
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#addProducer(api.Producer)
	 */
	public void addProducer(Producer producer) throws NanoContainerException {
		BeanType type = producer.getType();
		if(this.hasProducer(type)) {
			throw new NanoContainerException("The type " + type + " is already registred.");
		}
		this.producers.put(type, producer);
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#addProducers(java.util.List)
	 */
	public void addProducers(List<Producer> producers) throws NanoContainerException {
		for(Producer producer : producers) {
			this.addProducer(producer);
		}
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#bindingsSize()
	 */
	public int bindingsSize() {
		return this.bindings.size();
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#close()
	 */
	public void close() {
		this.bindings.clear();
		this.producers.clear();
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#getBinding(api.BeanType)
	 */
	public Binding getBinding(BeanType type) {
		return this.bindings.get(type);
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#getBindings()
	 */
	public Map<BeanType, Binding> getBindings() {
		return bindings;
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#getDescription()
	 */
	public String getDescription() {
		String description = "Registry : " + super.toString();
		description += description += System.getProperty("line.separator") + "Bindins : ";
		for(Binding binding : this.bindings.values()) {
			description += System.getProperty("line.separator") + "Type : " + binding.getType() + ", implementation : " + binding.getImplementation().getSimpleName();
		}
		description += description += System.getProperty("line.separator") + "Producers : ";
		for(Producer producer : this.producers.values()) {
			description += System.getProperty("line.separator") + "Type : " + producer.getType() + ", method : " + producer.getMethod().getName();
		}
		description += description += System.getProperty("line.separator") + "Interceptor : ";
		for(InterceptorBinding interceptor : this.interceptors.values()) {
			description += System.getProperty("line.separator") + "Type : " + interceptor.getType() + ", method : " + interceptor.getMethod().getName() + ", interceptor bindings : " + interceptor.getQualifiers();
		}
		return description;
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#getInterceptorBindings(java.util.Set)
	 */
	public List<InterceptorBinding> getInterceptorBindings(Set<Annotation> qualifiers) {
		List<InterceptorBinding> result = new ArrayList<InterceptorBinding>();
		for(Entry<BeanType, InterceptorBinding> interceptor : this.interceptors.entrySet()) {
			if(qualifiers.containsAll(interceptor.getValue().getQualifiers())) {
				result.add(interceptor.getValue());
			}
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#getInterceptors()
	 */
	public Map<BeanType, InterceptorBinding> getInterceptors() {
		return interceptors;
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#getProducer(api.BeanType)
	 */
	public Producer getProducer(BeanType type) {
		return this.producers.get(type);
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#getProducers()
	 */
	public Map<BeanType, Producer> getProducers() {
		return producers;
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#hasBinding(api.BeanType)
	 */
	public boolean hasBinding(BeanType type) {
		return this.bindings.containsKey(type);
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#hasInterceptor(api.BeanType)
	 */
	public boolean hasInterceptor(BeanType type) {
		return this.interceptors.containsKey(type);
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#hasProducer(api.BeanType)
	 */
	public boolean hasProducer(BeanType type) {
		return this.producers.containsKey(type);
	}
	
	/* (non-Javadoc)
	 * @see api.Registry#interceptorsSize()
	 */
	public int interceptorsSize() {
		return this.interceptors.size();
	}

	/* (non-Javadoc)
	 * @see api.Registry#isInjectable(api.BeanType)
	 */
	public boolean isInjectable(BeanType type) {
		return (this.bindings.containsKey(type)) || (this.producers.containsKey(type));
	}

	/* (non-Javadoc)
	 * @see api.Registry#producersSize()
	 */
	public int producersSize() {
		return this.producers.size();
	}

	/* (non-Javadoc)
	 * @see api.Registry#size()
	 */
	public int size() {
		return this.bindingsSize() + this.producersSize() + this.interceptorsSize();
	}

}
