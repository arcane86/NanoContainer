package impl;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.inject.Qualifier;

import api.BeanType;
import api.Binding;

/**
 * Implementation of the bindings used by Nanocontainer to link a 
 * {@link BeanType} (Interface or Class and {@link Qualifier} annotations) with
 *  an implementation (Class).
 * @author Matthieu Clochard
 */
public class BindingImpl implements Binding {
	
	/**
	 * The implementation (Class) of the bean.
	 */
	private Class<?> implementation;	
	/**
	 * The {@link BeanType} of the bean.
	 */
	private BeanType type;
	
	/**
	 * Construct a new binding between a {@link BeanType} and an
	 * implementation (Class).
	 * @param type the {@link BeanType} of the bean.
	 * @param implementation the implementation (Class) of the bean.
	 */
	public BindingImpl(BeanType type, Class<?> implementation) {
		this.type = type;
		this.implementation = implementation;
	}
	
	/**
	 * Construct a new binding between a type (Interface or Class) and an
	 * implementation (Class).
	 * @param type the type (Interface or Class) of the bean.
	 * @param implementation the implementation (Class) of the bean.
	 */
	public BindingImpl(Class<?> type, Class<?> implementation) {
		this.type = new BeanTypeImpl(type);
		this.implementation = implementation;
	}

	/**
	 * Construct a new binding between a type (Interface or Class) with a set
	 *  of {@link Qualifier} annotations and an implementation (Class).
	 * @param type the type (Interface or Class) of the bean.
	 * @param qualifiers the bean {@link Qualifier} annotations as a Set.
	 * @param implementation the implementation (Class) of the bean.
	 */
	public BindingImpl(Class<?> type, Set<Annotation> qualifiers, 
						Class<?> implementation) {
		this.type = new BeanTypeImpl(type, qualifiers);
		this.implementation = implementation;
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
		if (!(obj instanceof BindingImpl)) {
			return false;
		}
		BindingImpl other = (BindingImpl) obj;
		if (implementation == null) {
			if (other.implementation != null) {
				return false;
			}
		} else if (!implementation.equals(other.implementation)) {
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
	 * @see api.Binding#getImplementation()
	 */
	public Class<?> getImplementation() {
		return implementation;
	}

	/* (non-Javadoc)
	 * @see api.Binding#getType()
	 */
	public BeanType getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((implementation == null) ? 0 : implementation.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

}
