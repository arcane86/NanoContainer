package api;

import java.util.Map;

import api.exception.NanoContainerException;

public interface InstancesManager {

	<T> T createNewInstance(Binding binding) throws NanoContainerException;
	<T> T getInstance(Binding binding) throws NanoContainerException;
	<T> T createNewProducerInstance(Binding binding) throws NanoContainerException;
	<T> T createNewInstance(Producer producer) throws NanoContainerException;
	<T> T getInstance(Producer producer) throws NanoContainerException;
	<T> T createNewProducerInstance(Producer producer) throws NanoContainerException;
	void terminate() throws NanoContainerException;
	NanoContainer getContainer();
	Map<Binding,Object> getInstances();

}
