package api;

import java.lang.reflect.Method;

public interface Producer {
	
	Class<?> getDeclaringClass();
	Method getMethod();
	BeanType getType();

}
