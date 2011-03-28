package api;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import api.exception.NanoContainerException;

public interface Registry {

	Map<BeanType, Binding> getBindings();
	int size();
	int bindingsSize();
	int producersSize();
	String getDescription();
	boolean isRegistred(BeanType type);
	Binding getBinding(BeanType type);
	Producer getProducer(BeanType type);
	void addBinding(BeanType type, Class<?> implementation) throws NanoContainerException;
	void addBinding(Binding binding) throws NanoContainerException;	
	void addBindings(List<Binding> bindings) throws NanoContainerException;
	void addProducer(Producer producer) throws NanoContainerException;
	void addProducer(BeanType type, Method method) throws NanoContainerException;
	void addProducers(List<Producer> producers) throws NanoContainerException;
	void close();

}
