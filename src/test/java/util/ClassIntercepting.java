package util;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;

@Interceptor
public class ClassIntercepting {
	
	@AroundInvoke
	public void method() {
		System.out.println("Interception");
	}

}
