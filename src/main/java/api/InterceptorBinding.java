package api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;

/**
 * Representation of the bindings used by Nanocontainer to link a interceptor
 * method ({@link AroundInvoke} annotated method) with its interceptor bean type
 * ({@link Interceptor} annotated class) and with the quaifiers of the 
 * interception ({@link javax.interceptor.InterceptorBinding} annotations).
 * @author Matthieu Clochard
 */
public interface InterceptorBinding {
	
	/**
	 * The interceptor method ({@link AroundInvoke} annotated method).
	 * @return the method of this binding.
	 */
	Method getMethod();
	/**
	 * The {@link javax.interceptor.InterceptorBinding} annotations of the
	 * interception (including interceptor bean and method annotations).
	 * @return all the InterceptorBinding annotations of the interceptor bean 
	 * and method as a Set.
	 */
	Set<Annotation> getQualifiers();
	/**
	 * The bean type of the interceptor bean declaring the method.
	 * @return the {@link BeanType} of the interceptor bean.
	 */
	BeanType getType();

}
