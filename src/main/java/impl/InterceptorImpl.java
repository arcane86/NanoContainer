package impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import api.Interceptor;

/**
 * Implementation of an interceptor used by Nanocontainer.
 * @author Matthieu Clochard
 */
public class InterceptorImpl implements Interceptor {
	
	/**
	 * The method that should be called during the interception.
	 */
	Method method;
	/**
	 * The instanciated parameters of the method.
	 */
	Object[] parameters;
	/**
	 * The instanciated object on which the method should be called.
	 */
	Object target;
	
	/**
	 * Construct a new interceptor.
	 * @param method the method that should be called during the interception.
	 * @param parameters the instanciated parameters of the method
	 * @param target the instanciated object on which the method should be 
	 * called.
	 */
	public InterceptorImpl(Method method,
			Object[] parameters, Object target) {
		this.method = method;
		this.parameters = parameters;
		this.target = target;
	}

	/* (non-Javadoc)
	 * @see api.Interceptor#intercept(javax.interceptor.InvocationContext)
	 */
	public Object intercept(InvocationContext invocationContext) {
		Method method = this.method;
		Object[] parameters = this.parameters;
		Object target = this.target;
		try {
			method.invoke(target, parameters);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		try {
			return invocationContext.proceed();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
