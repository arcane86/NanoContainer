package api;

import java.lang.reflect.Method;
import java.util.Map;

import api.exception.NanoContainerException;

/**
 * @author hp
 *
 */
public interface InstancesManager {

	/**
	 * @param <T>
	 * @param binding
	 * @return
	 * @throws NanoContainerException
	 */
	<T> T createNewInstance(Binding binding) throws NanoContainerException;
	/**
	 * @param <T>
	 * @param producer
	 * @return
	 * @throws NanoContainerException
	 */
	<T> T createNewInstance(Producer producer) throws NanoContainerException;
	/**
	 * @param <T>
	 * @param binding
	 * @return
	 * @throws NanoContainerException
	 */
	<T> T createNewInterceptorInstance(Binding binding) throws NanoContainerException;
	/**
	 * @param <T>
	 * @param producer
	 * @return
	 * @throws NanoContainerException
	 */
	<T> T createNewInterceptorInstance(Producer producer) throws NanoContainerException;
	/**
	 * @param <T>
	 * @param binding
	 * @return
	 * @throws NanoContainerException
	 */
	<T> T createNewProducerInstance(Binding binding) throws NanoContainerException;
	/**
	 * @param <T>
	 * @param producer
	 * @return
	 * @throws NanoContainerException
	 */
	<T> T createNewProducerInstance(Producer producer) throws NanoContainerException;
	/**
	 * @return
	 */
	NanoContainer getContainer();
	/**
	 * @param <T>
	 * @param binding
	 * @return
	 * @throws NanoContainerException
	 */
	<T> T getInstance(Binding binding) throws NanoContainerException;
	/**
	 * @param <T>
	 * @param producer
	 * @return
	 * @throws NanoContainerException
	 */
	<T> T getInstance(Producer producer) throws NanoContainerException;
	/**
	 * @return
	 */
	Map<Binding,Object> getInstances();
	/**
	 * @param method
	 * @return
	 */
	Object[] getInstanciedParameters(Method method);
	/**
	 * @throws NanoContainerException
	 */
	void terminate() throws NanoContainerException;

}
