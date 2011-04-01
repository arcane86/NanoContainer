package impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Test;

import util.ClassWithProducers;
import api.BeanType;
import api.Producer;


public class ProducerImplTest {
	
	@Test
	public void testHashCode() {
		Method method = null;
		try {
			method = ClassWithProducers.class.getMethod("intProducer24");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		Producer producer1 = new ProducerImpl(method.getReturnType(), method);
		Producer producer2 = new ProducerImpl(method.getReturnType(), new HashSet<Annotation>(), method);
		Producer producer3 = new ProducerImpl(new BeanTypeImpl(method.getReturnType()), method);
		Producer producer4 = producer1;
		Assert.assertEquals(producer1.hashCode(), producer2.hashCode());
		Assert.assertEquals(producer1.hashCode(), producer3.hashCode());
		Assert.assertEquals(producer1.hashCode(), producer4.hashCode());
	}

	@Test
	public void testEqualsObject() {
		Method method = null;
		try {
			method = ClassWithProducers.class.getMethod("intProducer24");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		Producer producer1 = new ProducerImpl(method.getReturnType(), method);
		Producer producer2 = new ProducerImpl(method.getReturnType(), new HashSet<Annotation>(), method);
		Producer producer3 = new ProducerImpl(new BeanTypeImpl(method.getReturnType()), method);
		Producer producer4 = producer1;
		Assert.assertEquals(producer1, producer2);
		Assert.assertEquals(producer1, producer3);
		Assert.assertEquals(producer1, producer4);
	}

	@Test
	public void testGetType() {
		Method method = null;
		try {
			method = ClassWithProducers.class.getMethod("intProducer24");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		Producer producer1 = new ProducerImpl(method.getReturnType(), method);
		Producer producer2 = new ProducerImpl(method.getReturnType(), new HashSet<Annotation>(), method);
		Producer producer3 = new ProducerImpl(new BeanTypeImpl(method.getReturnType()), method);
		Producer producer4 = producer1;
		BeanType type = producer1.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(new BeanTypeImpl(method.getReturnType()), type);
		type = producer2.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(new BeanTypeImpl(method.getReturnType()), type);
		type = producer3.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(new BeanTypeImpl(method.getReturnType()), type);
		type = producer4.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(new BeanTypeImpl(method.getReturnType()), type);
	}

	@Test
	public void testGetMethod() {
		Method method = null;
		try {
			method = ClassWithProducers.class.getMethod("intProducer24");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		Producer producer1 = new ProducerImpl(method.getReturnType(), method);
		Producer producer2 = new ProducerImpl(method.getReturnType(), new HashSet<Annotation>(), method);
		Producer producer3 = new ProducerImpl(new BeanTypeImpl(method.getReturnType()), method);
		Producer producer4 = producer1;
		Method methodStored = producer1.getMethod();
		Assert.assertNotNull(methodStored);
		Assert.assertEquals(method, methodStored);
		methodStored = producer2.getMethod();
		Assert.assertNotNull(methodStored);
		Assert.assertEquals(method, methodStored);
		methodStored = producer3.getMethod();
		Assert.assertNotNull(methodStored);
		Assert.assertEquals(method, methodStored);
		methodStored = producer4.getMethod();
		Assert.assertNotNull(methodStored);
		Assert.assertEquals(method, methodStored);
	}

}
