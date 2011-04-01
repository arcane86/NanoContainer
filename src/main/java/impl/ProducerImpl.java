package impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.inject.Qualifier;

import api.BeanType;
import api.Producer;

/**
 * Implementation of the bindings used by Nanocontainer to link a 
 * {@link BeanType} (Interface or Class and {@link Qualifier} annotations) with
 *  a producer method ({@link Produces} annotated method).
 * @author Matthieu Clochard
 */
public class ProducerImpl implements Producer {
	
	/**
	 * The {@link Produces} annotated method that produce the bean.
	 */
	private Method method;	
	/**
	 * The {@link BeanType} of the bean.
	 */
	private BeanType type;
	
	/**
	 * Construct a new binding between a {@link BeanType} and a producer method.
	 * @param type the {@link BeanType} of the bean.
	 * @param method the producer method that produce the bean.
	 */
	public ProducerImpl(BeanType type, Method method) {
		this.type = type;
		this.method = method;
	}
	
	/**
	 * Construct a new binding between a type (Interface or Class) and a
	 * producer method.
	 * @param type the type (Interface or Class) of the bean.
	 * @param method the producer method that produce the bean.
	 */
	public ProducerImpl(Class<?> type, Method method) {
		this.type = new BeanTypeImpl(type);
		this.method = method;
	}

	/**
	 * Construct a new binding between a type (Interface or Class) with a set
	 *  of {@link Qualifier} annotations and a producer method.
	 * @param type the type (Interface or Class) of the bean.
	 * @param qualifiers the bean {@link Qualifier} annotations as a Set.
	 * @param method the producer method that produce the bean.
	 */
	public ProducerImpl(Class<?> type, Set<Annotation> qualifiers, 
			Method method) {
		this.type = new BeanTypeImpl(type, qualifiers);
		this.method = method;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ProducerImpl)) {
			return false;
		}
		ProducerImpl other = (ProducerImpl) obj;
		if (method == null) {
			if (other.method != null) {
				return false;
			}
		} else if (!method.equals(other.method)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see api.Producer#getDeclaringClass()
	 */
	public Class<?> getDeclaringClass() {
		return this.method.getDeclaringClass();
	}
	
	/* (non-Javadoc)
	 * @see api.Producer#getMethod()
	 */
	public Method getMethod() {
		return this.method;
	}

	/* (non-Javadoc)
	 * @see api.Producer#getType()
	 */
	public BeanType getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

}
