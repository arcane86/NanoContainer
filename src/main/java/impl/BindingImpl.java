package impl;

import java.lang.annotation.Annotation;
import java.util.Set;

import api.BeanType;
import api.Binding;

public class BindingImpl implements Binding {
	
	private BeanType type;	
	private Class<?> implementation;
	
	public BindingImpl(Class<?> type, Class<?> implementation) {
		this.type = new BeanTypeImpl(type);
		this.implementation = implementation;
	}
	
	public BindingImpl(Class<?> type, Set<Annotation> qualifiers, 
						Class<?> implementation) {
		this.type = new BeanTypeImpl(type, qualifiers);
		this.implementation = implementation;
	}

	public BindingImpl(BeanType type, Class<?> implementation) {
		this.type = type;
		this.implementation = implementation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((implementation == null) ? 0 : implementation.hashCode());
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

	public BeanType getType() {
		return type;
	}

	public Class<?> getImplementation() {
		return implementation;
	}

}
