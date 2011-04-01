package impl;

import javax.interceptor.InvocationContext;

import junit.framework.Assert;

import org.junit.Test;

import util.ClassIntercepted;

import api.Interceptor;


public class InterceptorImplTest {
	
	@Test
	public void testIntercept() {
		ClassIntercepted classInterceped = new ClassIntercepted();
		try {
			Interceptor interceptor = new InterceptorImpl(ClassIntercepted.class.getMethod("method", String.class), new Object[] {"Hello"}, classInterceped);
			InvocationContext invocationContext = new NanoContainerInvocationContextImpl(this.getClass().getMethod("testIntercept"), new Object[0], classInterceped, new Interceptor[] {interceptor});
			Assert.assertEquals(classInterceped.method("Hello"), interceptor.intercept(invocationContext));
		} catch (Exception e) {
			Assert.fail(e.getClass() + " : " + e.getMessage());
		}
	}

}
