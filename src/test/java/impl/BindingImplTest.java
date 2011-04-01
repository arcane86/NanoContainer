package impl;

import java.lang.annotation.Annotation;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Test;

import api.BeanType;
import api.Binding;

public class BindingImplTest {

	@Test
	public void testHashCode() {
		Binding binding1 = new BindingImpl(Object.class, Object.class);
		Binding binding2 = new BindingImpl(Object.class, new HashSet<Annotation>(), Object.class);
		Binding binding3 = new BindingImpl(new BeanTypeImpl(Object.class), Object.class);
		Binding binding4 = binding1;
		Assert.assertEquals(binding1.hashCode(), binding2.hashCode());
		Assert.assertEquals(binding1.hashCode(), binding3.hashCode());
		Assert.assertEquals(binding1.hashCode(), binding4.hashCode());
	}

	@Test
	public void testEqualsObject() {
		Binding binding1 = new BindingImpl(Object.class, Object.class);
		Binding binding2 = new BindingImpl(Object.class, new HashSet<Annotation>(), Object.class);
		Binding binding3 = new BindingImpl(new BeanTypeImpl(Object.class), Object.class);
		Binding binding4 = binding1;
		Assert.assertEquals(binding1, binding2);
		Assert.assertEquals(binding1, binding3);
		Assert.assertEquals(binding1, binding4);
	}

	@Test
	public void testGetType() {
		Binding binding1 = new BindingImpl(Object.class, Object.class);
		Binding binding2 = new BindingImpl(Object.class, new HashSet<Annotation>(), Object.class);
		Binding binding3 = new BindingImpl(new BeanTypeImpl(Object.class), Object.class);
		Binding binding4 = binding1;
		BeanType type = binding1.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(new BeanTypeImpl(Object.class), type);
		type = binding2.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(new BeanTypeImpl(Object.class), type);
		type = binding3.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(new BeanTypeImpl(Object.class), type);
		type = binding4.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(new BeanTypeImpl(Object.class), type);
	}

	@Test
	public void testGetImplementation() {
		Binding binding1 = new BindingImpl(Object.class, Object.class);
		Binding binding2 = new BindingImpl(Object.class, new HashSet<Annotation>(), Object.class);
		Binding binding3 = new BindingImpl(new BeanTypeImpl(Object.class), Object.class);
		Binding binding4 = binding1;
		Class<?> implementation = binding1.getImplementation();
		Assert.assertNotNull(implementation);
		Assert.assertEquals(Object.class, implementation);
		implementation = binding2.getImplementation();
		Assert.assertNotNull(implementation);
		Assert.assertEquals(Object.class, implementation);
		implementation = binding3.getImplementation();
		Assert.assertNotNull(implementation);
		Assert.assertEquals(Object.class, implementation);
		implementation = binding4.getImplementation();
		Assert.assertNotNull(implementation);
		Assert.assertEquals(Object.class, implementation);
	}

}
