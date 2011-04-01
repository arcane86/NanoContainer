package api;

import javax.interceptor.InvocationContext;

/**
 * Representation of an interceptor used by Nanocontainer.
 * @author Matthieu Clochard
 */
public interface Interceptor {
	/**
	 * Proceed the interception by calling a dynamically defined method.
	 * @param invocationContext the context when the interception occurs.
	 * @return the result of the method called.
	 */
	Object intercept(InvocationContext invocationContext);
}
