package impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import util.ClassWithInterceptor;
import api.BeanType;
import api.InterceptorBinding;


public class InterceptorBindingImplTest {
	
	@Test
	public void testHashCode() {
		Method method = null;
		try {
			method = ClassWithInterceptor.class.getMethod("intercept");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		InterceptorBinding interceptorBinding1 = new InterceptorBindingImpl(new BeanTypeImpl(ClassWithInterceptor.class), method, new HashSet<Annotation>());
		InterceptorBinding interceptorBinding2 = interceptorBinding1;
		Assert.assertEquals(interceptorBinding1.hashCode(), interceptorBinding2.hashCode());
	}

	@Test
	public void testEqualsObject() {
		Method method = null;
		try {
			method = ClassWithInterceptor.class.getMethod("intercept");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		InterceptorBinding interceptorBinding1 = new InterceptorBindingImpl(new BeanTypeImpl(ClassWithInterceptor.class), method, new HashSet<Annotation>());
		InterceptorBinding interceptorBinding2 = interceptorBinding1;
		Assert.assertEquals(interceptorBinding1, interceptorBinding2);
	}

	@Test
	public void testGetType() {
		Method method = null;
		try {
			method = ClassWithInterceptor.class.getMethod("intercept");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		InterceptorBinding interceptorBinding1 = new InterceptorBindingImpl(new BeanTypeImpl(ClassWithInterceptor.class), method, new HashSet<Annotation>());
		InterceptorBinding interceptorBinding2 = interceptorBinding1;
		BeanType type = interceptorBinding1.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(new BeanTypeImpl(ClassWithInterceptor.class), type);
		type = interceptorBinding2.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(new BeanTypeImpl(ClassWithInterceptor.class), type);
	}

	@Test
	public void testGetMethod() {
		Method method = null;
		try {
			method = ClassWithInterceptor.class.getMethod("intercept");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		InterceptorBinding interceptorBinding1 = new InterceptorBindingImpl(new BeanTypeImpl(ClassWithInterceptor.class), method, new HashSet<Annotation>());
		InterceptorBinding interceptorBinding2 = interceptorBinding1;
		Method methodStored = interceptorBinding1.getMethod();
		Assert.assertNotNull(methodStored);
		Assert.assertEquals(method, methodStored);
		methodStored = interceptorBinding2.getMethod();
		Assert.assertNotNull(methodStored);
		Assert.assertEquals(method, methodStored);
	}
	
	@Test
	public void testGetQualifiers() {
		Method method = null;
		try {
			method = ClassWithInterceptor.class.getMethod("intercept");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		InterceptorBinding interceptorBinding1 = new InterceptorBindingImpl(new BeanTypeImpl(ClassWithInterceptor.class), method, new HashSet<Annotation>());
		InterceptorBinding interceptorBinding2 = interceptorBinding1;
		Set<Annotation> qualifiers = interceptorBinding1.getQualifiers();
		Assert.assertNotNull(qualifiers);
		Assert.assertEquals(new HashSet<Annotation>(), qualifiers);
		qualifiers = interceptorBinding2.getQualifiers();
		Assert.assertNotNull(qualifiers);
		Assert.assertEquals(new HashSet<Annotation>(), qualifiers);
	}
	
}
