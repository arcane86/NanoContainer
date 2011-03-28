package impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import util.Class1;
import util.Class1b;
import util.Class2;
import util.ClassProduced;
import util.Interface1;
import util.Interface2;
import util.ClassWithProducers;
import api.Binding;
import api.Registry;
import api.exception.NanoContainerException;


public class RegistryImplTest {
	
	@Test
	public void testAddBinding() {
		Registry registry = new RegistryImpl();
		boolean exceptionRaised = false;
		Assert.assertEquals(0, registry.size());
		try {
			registry.addBinding(new BindingImpl(Object.class, Object.class));
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(1, registry.size());
		try {
			registry.addBinding(new BindingImpl(Object.class, Object.class));
		} catch (NanoContainerException e) {
			exceptionRaised = true;
		}
		Assert.assertEquals(true, exceptionRaised);
		exceptionRaised = false;
		Assert.assertEquals(1, registry.size());
		try {
			registry.addBinding(new BeanTypeImpl(Interface1.class), Class1.class);
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(2, registry.size());
		try {
			registry.addBinding(new BeanTypeImpl(Interface1.class), Class1.class);
		} catch (NanoContainerException e) {
			exceptionRaised = true;
		}
		Assert.assertEquals(true, exceptionRaised);
		exceptionRaised = false;
		Assert.assertEquals(2, registry.size());
		List<Binding> bindings = new ArrayList<Binding>();
		bindings.add(new BindingImpl(Interface2.class, Class2.class));
		bindings.add(new BindingImpl(Class1b.class, Class1b.class));
		try {
			registry.addBindings(bindings);
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(4, registry.size());
		try {
			registry.addBindings(bindings);
		} catch (NanoContainerException e) {
			exceptionRaised = true;
		}
		Assert.assertEquals(true, exceptionRaised);
		exceptionRaised = false;
		Assert.assertEquals(4, registry.size());
	}
	
	@Test
	public void testAddProducer() {
		Registry registry = new RegistryImpl();
		boolean exceptionRaised = false;
		Assert.assertEquals(0, registry.size());
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
		try {
			registry.addProducer(new ProducerImpl(method.getReturnType(),method));
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(1, registry.size());
		try {
			registry.addProducer(new ProducerImpl(method.getReturnType(),method));
		} catch (NanoContainerException e) {
			exceptionRaised = true;
		}
		Assert.assertEquals(true, exceptionRaised);
		exceptionRaised = false;
		Assert.assertEquals(1, registry.size());
		try {
			method = ClassWithProducers.class.getMethod("classProducer");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		try {
			registry.addProducer(new ProducerImpl(method.getReturnType(),method));
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(2, registry.size());
		try {
			registry.addProducer(new ProducerImpl(method.getReturnType(),method));
		} catch (NanoContainerException e) {
			exceptionRaised = true;
		}
		Assert.assertEquals(true, exceptionRaised);
		exceptionRaised = false;
		Assert.assertEquals(2, registry.size());
	}
	
	@Test
	public void testAddBindingAndProducer() {
		Registry registry = new RegistryImpl();
		boolean exceptionRaised = false;
		Assert.assertEquals(0, registry.size());
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
		try {
			registry.addProducer(new ProducerImpl(method.getReturnType(),method));
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(1, registry.size());
		try {
			registry.addProducer(new ProducerImpl(method.getReturnType(),method));
		} catch (NanoContainerException e) {
			exceptionRaised = true;
		}
		Assert.assertEquals(true, exceptionRaised);
		exceptionRaised = false;
		Assert.assertEquals(1, registry.size());
		try {
			registry.addBinding(new BeanTypeImpl(Class1.class), Class1.class);
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(2, registry.size());
		try {
			registry.addBinding(new BeanTypeImpl(Class1.class), Class1.class);
		} catch (NanoContainerException e) {
			exceptionRaised = true;
		}
		Assert.assertEquals(true, exceptionRaised);
		exceptionRaised = false;
		try {
			method = ClassWithProducers.class.getMethod("classProducer");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		try {
			registry.addProducer(new ProducerImpl(method.getReturnType(),method));
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(3, registry.size());
		try {
			registry.addProducer(new ProducerImpl(method.getReturnType(),method));
		} catch (NanoContainerException e) {
			exceptionRaised = true;
		}
		Assert.assertEquals(true, exceptionRaised);
		exceptionRaised = false;
		Assert.assertEquals(3, registry.size());
	}
	
	@Test
	public void testIsRegistred() {
		Registry registry = new RegistryImpl();
		Method method = null;
		try {
			method = ClassWithProducers.class.getMethod("classProducer");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		try {
			registry.addProducer(new ProducerImpl(method.getReturnType(),method));
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		try {
			registry.addBinding(new BindingImpl(Object.class, Object.class));
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertTrue(registry.isRegistred(new BeanTypeImpl(Object.class)));
		Assert.assertTrue(registry.isRegistred(new BeanTypeImpl(ClassProduced.class)));
		Assert.assertFalse(registry.isRegistred(new BeanTypeImpl(Class1b.class)));
	}
	
	@Test
	public void testSize() {
		Registry registry = new RegistryImpl();
		Method method = null;
		try {
			method = ClassWithProducers.class.getMethod("classProducer");
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		try {
			registry.addProducer(new ProducerImpl(method.getReturnType(),method));
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		try {
			registry.addBinding(new BindingImpl(Object.class, Object.class));
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(1, registry.bindingsSize());
		Assert.assertEquals(1, registry.producersSize());
		Assert.assertEquals(2, registry.size());
	}
	
	@Test
	public void testGetBinding() {
		Registry registry = new RegistryImpl();
		try {
			registry.addBinding(new BindingImpl(Object.class, Object.class));
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		Binding binding = null;
		binding = registry.getBinding(new BeanTypeImpl(Object.class));
		Assert.assertEquals(binding, new BindingImpl(Object.class, Object.class));
		binding = registry.getBinding(new BeanTypeImpl(Interface1.class));
		Assert.assertNull(binding);
	}
	
	@Test
	public void testGetBindings() {
		Registry registry = new RegistryImpl();
		Assert.assertNotNull(registry.getBindings());
		Assert.assertEquals(registry.getBindings().size(), registry.size());
		Assert.assertEquals(registry.getBindings().size(), 0);
		try {
			registry.addBinding(new BindingImpl(Object.class, Object.class));
		} catch (NanoContainerException e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertNotNull(registry.getBindings());
		Assert.assertEquals(registry.getBindings().size(), registry.size());
		Assert.assertEquals(registry.getBindings().size(), 1);
	}

}
