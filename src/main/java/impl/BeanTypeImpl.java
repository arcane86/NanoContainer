package impl;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Qualifier;

import api.BeanType;

/**
 * Implementation of the type of beans used by Nanocontainer.
 * Each bean has several bean types.
 * @author Matthieu Clochard
 *
 */
public class BeanTypeImpl implements BeanType {
	
	/**
	 * The {@link Qualifier} annotations of the bean.
	 */
	private Set<Annotation> qualifiers;	
	/**
	 * The type of the bean.
	 */
	private Class<?> type;
	
	/**
	 * Construct a new bean type with no {@link Qualifier} annotation.
	 * @param type the type of the bean (Interface or Class).
	 */
	public BeanTypeImpl(Class<?> type) {
		this.type = type;
		this.qualifiers = new HashSet<Annotation>();
	}
	
	/**
	 * Construct a new bean type.
	 * @param type the type of the bean (Interface or Class).
	 * @param qualifiers {@link Qualifier} annotations of the bean as a Set.
	 */
	public BeanTypeImpl(Class<?> type, Set<Annotation> qualifiers) {
		this.type = type;
		this.qualifiers = qualifiers;
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
		if (!(obj instanceof BeanTypeImpl)) {
			return false;
		}
		BeanTypeImpl other = (BeanTypeImpl) obj;
		if (qualifiers == null) {
			if (other.qualifiers != null) {
				return false;
			}
		} else if (!qualifiers.equals(other.qualifiers)) {
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
	 * @see api.BeanType#getDescription()
	 */
	public String getDescription() {
		String description = this.toString();
		description += System.getProperty("line.separator") + "Type : " + this.type.getSimpleName();
		description += System.getProperty("line.separator") + "Qualifiers : ";
		for(Annotation annotation : this.qualifiers) {
			description += annotation.annotationType().getSimpleName() + " ";
		}
		description += System.getProperty("line.separator");
		return description;
	}

	/* (non-Javadoc)
	 * @see api.BeanType#getQualifiers()
	 */
	public Set<Annotation> getQualifiers() {
		return qualifiers;
	}
	
	/* (non-Javadoc)
	 * @see api.BeanType#getType()
	 */
	public Class<?> getType() {
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
				+ ((qualifiers == null) ? 0 : qualifiers.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
}
