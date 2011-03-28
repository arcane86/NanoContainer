package api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public interface NanoContainer {

	<T> T getInstance(BeanType beanType);
	<T> T getInstance(Class<?> type);
	<T> T getNewUnmanagedInstance(BeanType beanType);
	<T> T getNewUnmanagedInstance(Class<?> type);
	<T> T getNewProducerInstance(BeanType beanType);
	<T> T getNewProducerInstance(Class<?> type);
	void register(Class<?> implementation);
	InstancesManager getInstancesManager();
	Registry getRegistry();
	void stop();
	void start();
	Set<Annotation> getQualifiers(Class<?> implementation);
	Set<Annotation> getQualifiers(Method method);
	Set<Annotation> getQualifiers(Field field);
	Set<Annotation> getQualifiers(Annotation[] annotations);

}
