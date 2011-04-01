package api;

import java.lang.reflect.Method;

import javax.enterprise.inject.Produces;
import javax.inject.Qualifier;

/**
 * Representation of the bindings used by Nanocontainer to link a 
 * {@link BeanType} (Interface or Class and {@link Qualifier} annotations) with
 *  a producer method ({@link Produces} annotated method).
 * @author Matthieu Clochard
 */
public interface Producer {
	
	/**
	 * The bean that declare the producer method.
	 * @return the Class of the bean that declare the producer method.
	 */
	Class<?> getDeclaringClass();
	/**
	 * The producer method of the bean.
	 * @return the method that produce the bean.
	 */
	Method getMethod();
	/**
	 * The bean type of the bean.
	 * @return the {@link BeanType} of the bean.
	 */
	BeanType getType();

}
