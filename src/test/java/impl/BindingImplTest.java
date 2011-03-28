package impl;

import java.lang.annotation.Annotation;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Test;

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
		Assert.assertNotNull(binding1.getType());
		Assert.assertNotNull(binding2.getType());
		Assert.assertNotNull(binding3.getType());
		Assert.assertNotNull(binding4.getType());
	}

	@Test
	public void testGetImplementation() {
		Binding binding1 = new BindingImpl(Object.class, Object.class);
		Binding binding2 = new BindingImpl(Object.class, new HashSet<Annotation>(), Object.class);
		Binding binding3 = new BindingImpl(new BeanTypeImpl(Object.class), Object.class);
		Binding binding4 = binding1;
		Assert.assertNotNull(binding1.getImplementation());
		Assert.assertNotNull(binding2.getImplementation());
		Assert.assertNotNull(binding3.getImplementation());
		Assert.assertNotNull(binding4.getImplementation());
	}

}
