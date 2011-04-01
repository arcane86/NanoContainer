package util;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;

@Interceptor
public class ClassWithInterceptor {
	
	@AroundInvoke
	public void intercept() {
		
	}
	
}
