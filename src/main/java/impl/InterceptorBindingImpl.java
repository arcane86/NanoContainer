package impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;

import api.BeanType;
import api.InterceptorBinding;

/**
 * Implementation of the bindings used by Nanocontainer to link a interceptor
 * method ({@link AroundInvoke} annotated method) with its interceptor bean type
 * ({@link Interceptor} annotated class) and with the quaifiers of the 
 * interception ({@link javax.interceptor.InterceptorBinding} annotations).
 * @author Matthieu Clochard
 */
public class InterceptorBindingImpl implements InterceptorBinding {
	
	/**
	 * The interceptor method ({@link AroundInvoke} annotated method).
	 */
	private Method method;	
	/**
	 * The {@link javax.interceptor.InterceptorBinding} annotations of the
	 * interception (including interceptor bean and method annotations).
	 */
	private Set<Annotation> qualifiers;
	/**
	 * The bean type of the interceptor bean declaring the method.
	 */
	private BeanType type;

	/**
	 * Construct a new interceptor binding between a {@link BeanType} of the 
	 * interceptor bean, a {@link AroundInvoke} method of this bean and a set
	 * of {@link javax.interceptor.InterceptorBinding} annotations.
	 * @param type the {@link BeanType} of the interceptor bean.
	 * @param method the interceptor method.
	 * @param qualifiers the interceptor bindigngs of both interceptor bean and 
	 * method.
	 */
	public InterceptorBindingImpl(BeanType type, Method method,
			Set<Annotation> qualifiers) {
		this.type = type;
		this.method = method;
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
		if (!(obj instanceof InterceptorBindingImpl)) {
			return false;
		}
		InterceptorBindingImpl other = (InterceptorBindingImpl) obj;
		if (method == null) {
			if (other.method != null) {
				return false;
			}
		} else if (!method.equals(other.method)) {
			return false;
		}
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
	 * @see api.InterceptorBinding#getMethod()
	 */
	public Method getMethod() {
		return this.method;
	}

	/* (non-Javadoc)
	 * @see api.InterceptorBinding#getQualifiers()
	 */
	public Set<Annotation> getQualifiers() {
		return qualifiers;
	}

	/* (non-Javadoc)
	 * @see api.InterceptorBinding#getType()
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
		result = prime * result
				+ ((qualifiers == null) ? 0 : qualifiers.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

}
