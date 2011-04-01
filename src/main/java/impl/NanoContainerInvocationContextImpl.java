package impl;

import java.lang.reflect.Method;
import java.util.Map;

import javax.interceptor.InvocationContext;

import api.Interceptor;
import api.NanoContainerInvocationContext;

/**
 * Implementation of an invocation context used by Nanocontainer to represent 
 * the context during a interception or a decoration.
 * @author Matthieu Clochard
 */
public class NanoContainerInvocationContextImpl implements NanoContainerInvocationContext {
	
	/**
	 * The index of the current interceptor in use.
	 */
	private int currentInterceptor;
	/**
	 * The interceptors that should be called before the invoked method.
	 */
	private Interceptor[] interceptors;
	/**
	 * The last interceptor that redirect the last call on the invoked method.
	 */
	private Interceptor lastInterceptor;
	/**
	 * The invoked method of the invocation context.
	 */
	private Method method;
	/**
	 * The parameters of the invoked method.
	 */
	private Object[] parameters;
	/**
	 * The object on which the method is called.
	 */
	private Object target;

	/**
	 * Construct a new {@link InvocationContext} for a method invocation.
	 * @param method the invoked method.
	 * @param parameters the parameters of the invoked method.
	 * @param target the object on which the method is called.
	 * @param interceptors the interceptors that should be called before the 
	 * invoked method.
	 */
	public NanoContainerInvocationContextImpl(Method method, Object[] parameters,
			Object target, Interceptor[] interceptors) {
		this.method = method;
		this.parameters = parameters;
		this.target = target;
		this.interceptors = interceptors;
		this.lastInterceptor = new LastInterceptor();
		this.currentInterceptor = 0;
	}

	/* (non-Javadoc)
	 * @see javax.interceptor.InvocationContext#getContextData()
	 */
	public Map<String, Object> getContextData() {
		return null;
	}

	/* (non-Javadoc)
	 * @see api.NanoContainerInvocationContext#getInterceptors()
	 */
	public Interceptor[] getInterceptors() {
		return this.interceptors;
	}

	/* (non-Javadoc)
	 * @see javax.interceptor.InvocationContext#getMethod()
	 */
	public Method getMethod() {
		return this.method;
	}

	/* (non-Javadoc)
	 * @see javax.interceptor.InvocationContext#getParameters()
	 */
	public Object[] getParameters() {
		return this.parameters;
	}

	/* (non-Javadoc)
	 * @see javax.interceptor.InvocationContext#getTarget()
	 */
	public Object getTarget() {
		return this.target;
	}
	
	/* (non-Javadoc)
	 * @see javax.interceptor.InvocationContext#getTimer()
	 */
	public Object getTimer() {
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.interceptor.InvocationContext#proceed()
	 */
	public Object proceed() throws Exception {
		if(this.currentInterceptor < this.interceptors.length) {
			return this.interceptors[this.currentInterceptor++].intercept(this);
		}        
        this.currentInterceptor = 0;
        return this.lastInterceptor.intercept(this);
	}

	/* (non-Javadoc)
	 * @see javax.interceptor.InvocationContext#setParameters(java.lang.Object[])
	 */
	public void setParameters(Object[] arg0) {
		this.parameters = arg0;
	}

}
