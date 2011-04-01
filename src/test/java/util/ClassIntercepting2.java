package util;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;

@Interceptor
public class ClassIntercepting2 {

	@AroundInvoke
	public void method() {
		System.out.println("Interception again");
	}

}
