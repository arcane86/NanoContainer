package api;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.inject.Qualifier;

/**
 * Representation of the type of beans used by Nanocontainer.
 * Each bean has several bean types.
 * @author Matthieu Clochard
 */
public interface BeanType {

	/**
	 * A bean type can describe itself.
	 * @return the bean type description.
	 */
	String getDescription();
	/**
	 * The {@link Qualifier} annotations of the bean type.
	 * @return all the Qualifier annotations annotating the bean.
	 */
	Set<Annotation> getQualifiers();
	/**
	 * The type of the bean type.
	 * @return the type (Interface, class or superclass) of the bean.
	 */
	Class<?> getType();

}
