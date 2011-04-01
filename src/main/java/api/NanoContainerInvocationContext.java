package api;

import javax.interceptor.InvocationContext;

/**
 * Representation of an invocation context used by Nanocontainer to represent 
 * the context during a interception or a decoration.
 * @author Matthieu Clochard
 */
public interface NanoContainerInvocationContext extends InvocationContext {

	/**
	 * The interceptors used with this invocation context.
	 * @return the interceptors of the invocation context as an array.
	 */
	Interceptor[] getInterceptors();

}
