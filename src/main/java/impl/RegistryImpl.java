package impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.BeanType;
import api.Binding;
import api.Producer;
import api.Registry;
import api.exception.NanoContainerException;

public class RegistryImpl implements Registry {
	
	private Map<BeanType, Binding> bindings = new HashMap<BeanType, Binding>();
	private Map<BeanType, Producer> producers = new HashMap<BeanType, Producer>();
	
	public void addBinding(Binding binding) throws NanoContainerException {
		BeanType type = binding.getType();		
		if(this.isRegistred(type)) {
			throw new NanoContainerException("The type " + type + " is already registred.");
		}
		this.bindings.put(type, binding);		
	}
	
	public void addBindings(List<Binding> bindings) throws NanoContainerException {
		for(Binding binding : bindings) {
			this.addBinding(binding);
		}
	}
	
	public void addProducer(Producer producer) throws NanoContainerException {
		BeanType type = producer.getType();
		if(this.isRegistred(type)) {
			throw new NanoContainerException("The type " + type + " is already registred.");
		}
		this.producers.put(type, producer);
	}
	
	public void addProducers(List<Producer> producers) throws NanoContainerException {
		for(Producer producer : producers) {
			this.addProducer(producer);
		}
	}
	
	public void close() {
		this.bindings.clear();
		this.producers.clear();
	}
	
	public Binding getBinding(BeanType type) {
		return this.bindings.get(type);
	}
	
	public Producer getProducer(BeanType type) {
		return this.producers.get(type);
	}
	
	public boolean isRegistred(BeanType type) {
		return (this.bindings.containsKey(type) || this.producers.containsKey(type));
	}
	
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
		return description;
	}
	
	public int size() {
		return this.bindingsSize() + this.producersSize();
	}
	
	public int bindingsSize() {
		return this.bindings.size();
	}
	
	public int producersSize() {
		return this.producers.size();
	}

	public Map<BeanType, Binding> getBindings() {
		return bindings;
	}

	public Map<BeanType, Producer> getProducers() {
		return producers;
	}

}
