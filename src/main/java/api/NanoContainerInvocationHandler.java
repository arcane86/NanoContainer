package api;

import java.lang.reflect.InvocationHandler;

/**
 * Representation of the invocation handler used by Nanocontainer to manage
 * interceptions and decorations of proxied beans.
 * @author Matthieu Clochard
 */
public interface NanoContainerInvocationHandler extends InvocationHandler {
	/**
	 * The refering container of this {@link NanoContainerInvocationHandler}.
	 * @return the refering {@link NanoContainer}.
	 */
	NanoContainer getContainer();
	/**
	 * The bean instance managed by this {@link NanoContainerInvocationHandler}.
	 * @return the bean instance as an Object.
	 */
	Object getInstance();
	/**
	 * The bean type of the bean instance managed by this {@link NanoContainerInvocationHandler}.
	 * @return the {@link BeanType} of the instance.
	 */
	BeanType getType();
}
