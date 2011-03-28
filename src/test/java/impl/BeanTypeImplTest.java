package impl;

import java.lang.annotation.Annotation;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Test;

import api.BeanType;

public class BeanTypeImplTest {

	@Test
	public void testHashCode() {		
		BeanType beanType1 = new BeanTypeImpl(Object.class);
		BeanType beanType2 = new BeanTypeImpl(Object.class, new HashSet<Annotation>());
		BeanType beanType3 = beanType1;
		Assert.assertEquals(beanType1.hashCode(), beanType2.hashCode());
		Assert.assertEquals(beanType2.hashCode(), beanType3.hashCode());
		Assert.assertEquals(beanType3.hashCode(), beanType1.hashCode());
	}

	@Test
	public void testEqualsObject() {
		BeanType beanType1 = new BeanTypeImpl(Object.class);
		BeanType beanType2 = new BeanTypeImpl(Object.class, new HashSet<Annotation>());
		BeanType beanType3 = beanType1;
		Assert.assertEquals(beanType1, beanType2);
		Assert.assertEquals(beanType2, beanType3);
		Assert.assertEquals(beanType3, beanType1);
	}

	@Test
	public void testGetType() {
		BeanType beanType1 = new BeanTypeImpl(Object.class);
		BeanType beanType2 = new BeanTypeImpl(Object.class, new HashSet<Annotation>());
		BeanType beanType3 = beanType1;
		Assert.assertNotNull(beanType1.getType());
		Assert.assertNotNull(beanType2.getType());
		Assert.assertNotNull(beanType3.getType());
	}

	@Test
	public void testGetQualifiers() {
		BeanType beanType1 = new BeanTypeImpl(Object.class);
		BeanType beanType2 = new BeanTypeImpl(Object.class, new HashSet<Annotation>());
		BeanType beanType3 = beanType1;
		Assert.assertNotNull(beanType1.getQualifiers());
		Assert.assertNotNull(beanType2.getQualifiers());
		Assert.assertNotNull(beanType3.getQualifiers());
	}

}
