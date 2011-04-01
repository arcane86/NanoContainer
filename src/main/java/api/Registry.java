package api;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

import api.exception.NanoContainerException;

/**
 * Representation of the registry used by Nanocontainer to memorize the registed
 *  {@link Binding}, {@link Producer} and {@link InterceptorBinding}.
 * @author Matthieu Clochard
 */
public interface Registry {

	/**
	 * Add a new binding in the registry.
	 * @param binding the {@link Binding} to memorize.
	 * @throws NanoContainerException if the binding is already registered.
	 */
	void addBinding(Binding binding) throws NanoContainerException;
	/**
	 * Add a list of new bindings in the registry.
	 * @param bindings the {@link Binding}s to memorize as a List.
	 * @throws NanoContainerException if a binding is already registered.
	 */
	void addBindings(List<Binding> bindings) throws NanoContainerException;
	/**
	 * Add a new interceptor in the registry.
	 * @param interceptor the {@link InterceptorBinding} to memorize.
	 * @throws NanoContainerException if the interceptor is already registered.
	 */
	void addInterceptor(InterceptorBinding interceptor) throws NanoContainerException;
	/**
	 * Add a list of interceptor in the registry.
	 * @param interceptors the {@link InterceptorBinding}s to memorize as a List.
	 * @throws NanoContainerException if a interceptor is already registered.
	 */
	void addInterceptors(List<InterceptorBinding> interceptors) throws NanoContainerException;
	/**
	 * Add a new producer in the registry.
	 * @param producer the {@link Producer} to memorize.
	 * @throws NanoContainerException if the producer is already registered.
	 */
	void addProducer(Producer producer) throws NanoContainerException;
	/**
	 * Add a list of producers in the registry.
	 * @param producers the {@link Producer}s to memorize as a List.
	 * @throws NanoContainerException if a producer is already registered.
	 */
	void addProducers(List<Producer> producers) throws NanoContainerException;
	/**
	 * The size of the bindings list.
	 * @return the number of {@link Binding} registered in the registry.
	 */
	int bindingsSize();
	/**
	 * Close the registry, emptying all its lists.
	 */
	void close();
	/**
	 * Get the binding corresponding to the bean type.
	 * @param type the {@link BeanType} of the searched binding.
	 * @return the corresponding {@link Binding} or <code>null</code>.
	 */
	Binding getBinding(BeanType type);
	/**
	 * Get the list of bindinds registered in the registry.
	 * @return all the {@link Binding} as a Map keyed by {@link BeanType}.
	 */
	Map<BeanType, Binding> getBindings();
	/**
	 * The description of the registry with all registered items.
	 * @return the description.
	 */
	String getDescription();
	/**
	 * Get the interceptors corresponding to the interceptor bindings.
	 * @param qualifiers the {@link javax.interceptor.InterceptorBinding} of the interception.
	 * @return all the corresponding {@link InterceptorBinding} as a List.
	 */
	List<InterceptorBinding> getInterceptorBindings(Set<Annotation> qualifiers);
	/**
	 * Get the list of interceptors registered in the registry.
	 * @return all the {@link InterceptorBinding} as a Map keyed by {@link BeanType}.
	 */
	Map<BeanType, InterceptorBinding> getInterceptors();
	/**
	 * Get the producer corresponding to the bean type.
	 * @param type the {@link BeanType} of the searched producer.
	 * @return the corresponding {@link Producer} or <code>null</code>.
	 */
	Producer getProducer(BeanType type);
	/**
	 * Get the list of producers registrered in the registry.
	 * @return all the {@link Producer} as a Map keyed by {@link BeanType}.
	 */
	Map<BeanType, Producer> getProducers();
	/**
	 * Is the bean type registrered as a binding
	 * @param type the {@link BeanType} searched.
	 * @return <code>true</code> if the bean type is registered, <code>false</code> otherwise.
	 */
	boolean hasBinding(BeanType type);	
	/**
	 * Is the bean type registered as a interceptor.
	 * @param type the {@link BeanType} searched.
	 * @return <code>true</code> if the bean type is registered, <code>false</code> otherwise.
	 */
	boolean hasInterceptor(BeanType type);
	/**
	 * Is the bean type registered as a producer.
	 * @param type the {@link BeanType} searched.
	 * @return <code>true</code> if the bean type is registered, <code>false</code> otherwise.
	 */
	boolean hasProducer(BeanType type);
	/**
	 * The size of the interceptors list.
	 * @return the number of {@link InterceptorBinding} registered in the registry.
	 */
	int interceptorsSize();
	/**
	 * Is the bean type a injectable registered bean.
	 * @param type the {@link BeanType} searched.
	 * @return <code>true</code> if the bean type is injectable, <code>false</code> otherwise.
	 */
	boolean isInjectable(BeanType type);
	/**
	 * The size of the producers list.
	 * @return the number of {@link Producer} registered in the registry.
	 */
	int producersSize();
	/**
	 * The size of the registry.
	 * @return the number of all items registered in the registry.
	 */
	int size();

}
