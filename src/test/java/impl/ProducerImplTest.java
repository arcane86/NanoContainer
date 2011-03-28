package impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Test;

import util.ClassWithProducers;
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
		Assert.assertNotNull(producer1.getType());
		Assert.assertNotNull(producer2.getType());
		Assert.assertNotNull(producer3.getType());
		Assert.assertNotNull(producer4.getType());
	}

	@Test
	public void testGetImplementation() {
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
		Assert.assertNotNull(producer1.getMethod());
		Assert.assertNotNull(producer2.getMethod());
		Assert.assertNotNull(producer3.getMethod());
		Assert.assertNotNull(producer4.getMethod());
	}

}
