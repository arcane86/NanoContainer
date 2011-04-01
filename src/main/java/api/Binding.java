package api;

import javax.inject.Qualifier;

/**
 * Representation of the bindings used by Nanocontainer to link a 
 * {@link BeanType} (Interface or Class and {@link Qualifier} annotations) with
 *  an implementation (Class).
 * @author Matthieu Clochard
 */
public interface Binding {

	/**
	 * The implementation of the bean.
	 * @return the Class of the bean implementation.
	 */
	Class<?> getImplementation();
	/**
	 * The bean type of the bean.
	 * @return the {@link BeanType} of the bean.
	 */
	BeanType getType();
	
}
