package impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import api.BeanType;
import api.Interceptor;
import api.NanoContainer;
import api.NanoContainerInvocationHandler;

/**
 * Implementation of the invocation handler used by Nanocontainer to manage
 * interceptions and decorations of proxied beans.
 * @author Matthieu Clochard
 */
public class NanoContainerInvocationHandlerImpl implements NanoContainerInvocationHandler {
	
	/**
	 * The refering {@link NanoContainer}.
	 */
	private NanoContainer container;
	/**
	 * The bean instance managed.
	 */
	private Object instance;
	/**
	 * The {@link BeanType} of the bean instance managed.
	 */
	private BeanType type;

	/**
	 * Construct a new invocation handler for a bean instance on a container.
	 * @param container the refering {@link NanoContainer}.
	 * @param instance the bean instance managed.
	 * @param type the {@link BeanType} of the bean instance managed.
	 */
	public NanoContainerInvocationHandlerImpl(NanoContainer container,
			Object instance, BeanType type) {
		this.container = container;
		this.instance = instance;
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see api.NanoContainerInvocationHandler#getContainer()
	 */
	public NanoContainer getContainer() {
		return container;
	}

	/* (non-Javadoc)
	 * @see api.NanoContainerInvocationHandler#getInstance()
	 */
	public Object getInstance() {
		return instance;
	}

	/* (non-Javadoc)
	 * @see api.NanoContainerInvocationHandler#getType()
	 */
	public BeanType getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] parameters)
			throws Throwable {
		Interceptor[] interceptors = this.container.getInterceptors(this.type, method);
		InvocationContext invocationContext = new NanoContainerInvocationContextImpl(method, parameters, this.instance, interceptors);
		return invocationContext.proceed();
	}


}
