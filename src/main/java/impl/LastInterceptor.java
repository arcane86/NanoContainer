package impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import api.Interceptor;

public class LastInterceptor implements Interceptor {

	public Object intercept(InvocationContext invocationContext) {
		Method method = invocationContext.getMethod();
		Object[] parameters = invocationContext.getParameters();
		Object target = invocationContext.getTarget();
		try {
			return method.invoke(target, parameters);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

}
