package api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public interface NanoContainer {

	<T> T getInstance(BeanType beanType);
	<T> T getInstance(Class<?> type);
	InstancesManager getInstancesManager();
	Set<Annotation> getInterceptorBindings(Class<?> implementation);
	Set<Annotation> getInterceptorBindings(Method method);
	Interceptor[] getInterceptors(BeanType type, Method method);
	<T> T getNewProducerInstance(BeanType beanType);
	<T> T getNewProducerInstance(Class<?> type);
	<T> T getNewUnmanagedInstance(BeanType beanType);
	<T> T getNewUnmanagedInstance(Class<?> type);
	Set<Annotation> getQualifiers(Annotation[] annotations);
	Set<Annotation> getQualifiers(Class<?> implementation);
	Set<Annotation> getQualifiers(Field field);
	Set<Annotation> getQualifiers(Method method);
	Registry getRegistry();
	void register(Class<?> implementation);
	void start();
	void stop();

}
