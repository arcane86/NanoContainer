package impl;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import api.BeanType;

public class BeanTypeImpl implements BeanType {
	
	private Class<?> type;	
	private Set<Annotation> qualifiers;
	
	public BeanTypeImpl(Class<?> type) {
		this.type = type;
		this.qualifiers = new HashSet<Annotation>();
	}
	
	public BeanTypeImpl(Class<?> type, Set<Annotation> qualifiers) {
		this.type = type;
		this.qualifiers = qualifiers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((qualifiers == null) ? 0 : qualifiers.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

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

	public String getDescription() {
		String description = this.toString();
		description += System.getProperty("line.separator") + "Type : " + this.type.getSimpleName();
		description += System.getProperty("line.separator") + "Qualifiers : ";
		for(Annotation annotation : this.qualifiers) {
			description += annotation.annotationType().getSimpleName() + " ";
		}
		return description;
	}
	
	public Class<?> getType() {
		return type;
	}

	public Set<Annotation> getQualifiers() {
		return qualifiers;
	}
	
}
