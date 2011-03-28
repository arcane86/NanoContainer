package impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import api.BeanType;
import api.Producer;

public class ProducerImpl implements Producer {
	
	private BeanType type;	
	private Method method;
	
	public ProducerImpl(Class<?> type, Method method) {
		this.type = new BeanTypeImpl(type);
		this.method = method;
	}
	
	public ProducerImpl(Class<?> type, Set<Annotation> qualifiers, 
			Method method) {
		this.type = new BeanTypeImpl(type, qualifiers);
		this.method = method;
	}

	public ProducerImpl(BeanType type, Method method) {
		this.type = type;
		this.method = method;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
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
	
	public Class<?> getDeclaringClass() {
		return this.method.getDeclaringClass();
	}

	public Method getMethod() {
		return this.method;
	}

	public BeanType getType() {
		return this.type;
	}

}
