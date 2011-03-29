package impl;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import util.AbstractClass;
import util.AmbiguousClass;
import util.Class1;
import util.Class12;
import util.Class1Inheritence;
import util.Class1b;
import util.Class2;
import util.ClassAutoProduce;
import util.ClassProduced;
import util.ClassProducing;
import util.ClassWithNoInterface;
import util.ClassWithProducers;
import util.Interface1;
import util.Interface2;
import util.InterfaceAutoProduce;
import util.NamedAnnotationLiteral;
import api.BeanType;
import api.NanoContainer;
import api.exception.NanoContainerRuntimeException;

public class NanoContainerImplTest {
	
	@Test
	public void testGetRegistry() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		Assert.assertNotNull(nanoContainer.getRegistry());
	}

	@Test
	public void testGetInstancesManager() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		Assert.assertNotNull(nanoContainer.getInstancesManager());
	}

	@Test(expected=NanoContainerRuntimeException.class)
	public void testRegisterInterface() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(Interface1.class);
	}
	
	@Test(expected=NanoContainerRuntimeException.class)
	public void testRegisterAbstractClass() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(AbstractClass.class);
	}
	
	@Test
	public void testRegister() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(Class1.class);
		Assert.assertEquals(2, nanoContainer.getRegistry().size());
		nanoContainer.register(Class2.class);
		Assert.assertEquals(4, nanoContainer.getRegistry().size());
		nanoContainer.register(Class12.class);
		Assert.assertEquals(7, nanoContainer.getRegistry().size());
		nanoContainer.register(Class1b.class);
		Assert.assertEquals(9, nanoContainer.getRegistry().size());
		nanoContainer.register(ClassWithNoInterface.class);
		Assert.assertEquals(10, nanoContainer.getRegistry().size());
		nanoContainer.register(Class1Inheritence.class);
		Assert.assertEquals(12, nanoContainer.getRegistry().size());
		nanoContainer.register(ClassWithProducers.class);
		Assert.assertEquals(16, nanoContainer.getRegistry().size());
	}
	
	@Test(expected=NanoContainerRuntimeException.class)
	public void testRegisterAmbiguousClass() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(Class1.class);
		Assert.assertEquals(2, nanoContainer.getRegistry().size());
		nanoContainer.register(AmbiguousClass.class);
		Assert.assertEquals(2, nanoContainer.getRegistry().size());
	}
	
	@Test(expected=NanoContainerRuntimeException.class)
	public void testRegisterProducedClass() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(ClassProduced.class);
		Assert.assertEquals(1, nanoContainer.getRegistry().size());
		nanoContainer.register(ClassWithProducers.class);
		Assert.assertEquals(2, nanoContainer.getRegistry().size());
	}

	@Test
	public void testGetInstance() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(Class1.class);
		Assert.assertNotNull(nanoContainer.getInstance(Interface1.class));
		nanoContainer.register(Class1b.class);
		nanoContainer.register(Class2.class);
		Assert.assertNotNull(nanoContainer.getInstance(Interface2.class));
	}
	
	@Test
	public void testStart() {
		
	}
	
	@Test
	public void testStop() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(Class1.class);
		Assert.assertEquals(2, nanoContainer.getRegistry().size());
		Assert.assertNotNull(nanoContainer.getInstance(Interface1.class));
		nanoContainer.stop();
		Assert.assertEquals(0, nanoContainer.getRegistry().size());
		Assert.assertEquals(0, nanoContainer.getInstancesManager().getInstances().size());
	}
	
	@Test(expected=NanoContainerRuntimeException.class)
	public void testGetInstanceUnregistred() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.getInstance(Interface1.class);
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testGetInstanceWithAnnotation() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(Class1.class);
		nanoContainer.register(Class1b.class);
		nanoContainer.register(Class12.class);
		BeanType typeClass1 = new BeanTypeImpl(Interface1.class);
		Set<Annotation> namedDefault = new HashSet<Annotation>();
		namedDefault.add(new NamedAnnotationLiteral() {
			public String value() {
				return "";
			}
		});
		BeanType typeClass1b = new BeanTypeImpl(Interface1.class, namedDefault);
		Set<Annotation> namedOther = new HashSet<Annotation>();
		namedOther.add(new NamedAnnotationLiteral() {
			public String value() {
				return "Other";
			}
		});
		BeanType typeClass12 = new BeanTypeImpl(Interface1.class, namedOther);
		Interface1 bean1 = nanoContainer.getInstance(typeClass1);
		Interface1 bean1b = nanoContainer.getInstance(typeClass1b);
		Interface1 bean12 = nanoContainer.getInstance(typeClass12);
		Assert.assertNotNull(bean1);
		Assert.assertNotNull(bean1b);
		Assert.assertNotNull(bean12);
		Assert.assertNotSame(bean1, bean1b);
		Assert.assertNotSame(bean1, bean12);
		Assert.assertNotSame(bean1b, bean12);
	}
	
	@Test
	public void testConstructorInjection() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		
		nanoContainer.register(Class1.class);
		nanoContainer.register(Class2.class);
		nanoContainer.register(Class1b.class);
		
		Interface2 interface2 = nanoContainer.getInstance(Interface2.class);
		Assert.assertNotNull(interface2.getClass1());
		
	}
	
	@Test
	public void testMutatorInjection() {
		
		NanoContainer nanoContainer = new NanoContainerImpl();
		
		nanoContainer.register(Class1.class);
		nanoContainer.register(Class2.class);
		nanoContainer.register(Class1b.class);
		
		Interface2 interface2 = nanoContainer.getInstance(Interface2.class);
		Assert.assertNotNull(interface2.getClass1b());
		
	}
	
	@Test
	public void testFieldInjection() {
		
		NanoContainer nanoContainer = new NanoContainerImpl();
		
		nanoContainer.register(Class1.class);
		nanoContainer.register(Class2.class);
		nanoContainer.register(Class1b.class);
		
		Interface2 interface2 = nanoContainer.getInstance(Interface2.class);
		Assert.assertNotNull(interface2.getClass1c());
		
	}
	
	@Test
	public void testAnnotatedConstructorInjection() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		
		nanoContainer.register(Class1.class);
		nanoContainer.register(Class2.class);
		nanoContainer.register(Class1b.class);
		
		Interface2 interface2 = nanoContainer.getInstance(Interface2.class);
		Assert.assertNotNull(interface2.getClass1Named());
		
	}
	
	@Test
	public void testAnnotatedMutatorInjection() {
		
		NanoContainer nanoContainer = new NanoContainerImpl();
		
		nanoContainer.register(Class1.class);
		nanoContainer.register(Class2.class);
		nanoContainer.register(Class1b.class);
		
		Interface2 interface2 = nanoContainer.getInstance(Interface2.class);
		Assert.assertNotNull(interface2.getClass1bNamed());
		
	}
	
	@Test
	public void testAnnotatedFieldInjection() {
		
		NanoContainer nanoContainer = new NanoContainerImpl();
		
		nanoContainer.register(Class1.class);
		nanoContainer.register(Class2.class);
		nanoContainer.register(Class1b.class);
		
		Interface2 interface2 = nanoContainer.getInstance(Interface2.class);
		Assert.assertNotNull(interface2.getClass1cNamed());
		
	}
	
	@Test
	public void testProducersInjection() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(ClassWithProducers.class);
		nanoContainer.register(ClassProducing.class);
		ClassProducing classProducing = nanoContainer.getInstance(ClassProducing.class);
		Assert.assertNotNull(classProducing.getInteger24());
		Assert.assertNotNull(classProducing.getInteger42());
		Assert.assertNotNull(classProducing.getClassProduced());
		Assert.assertEquals(24,classProducing.getInteger24());
		Assert.assertEquals(42,classProducing.getInteger42());
		Assert.assertEquals(ClassProduced.class,classProducing.getClassProduced().getClass());
		Assert.assertEquals(24,classProducing.getClassProduced().getValue());
	}
	
	@Test
	public void testAutoProducersInjection() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(ClassAutoProduce.class);
		nanoContainer.register(ClassWithProducers.class);
		InterfaceAutoProduce autoProduce = nanoContainer.getInstance(InterfaceAutoProduce.class);
		Assert.assertNotNull(autoProduce.getClassProduced());
		Assert.assertEquals(42,autoProduce.getClassProduced().getValue());
	}
	
	@Test
	public void testPostConstruct() {
		
		NanoContainer nanoContainer = new NanoContainerImpl();
		
		nanoContainer.register(Class1.class);
		nanoContainer.register(Class2.class);
		nanoContainer.register(Class1b.class);
		
		Interface2 interface2 = nanoContainer.getInstance(Interface2.class);
		Assert.assertTrue(interface2.getPostConstrcut());
		
	}
	
	@Test
	public void testPreDestroy() {
		
		NanoContainer nanoContainer = new NanoContainerImpl();
		
		nanoContainer.register(Class1.class);
		nanoContainer.register(Class2.class);
		nanoContainer.register(Class1b.class);
		
		Interface2 interface2 = nanoContainer.getInstance(Interface2.class);
		
		nanoContainer.stop();
		
		Assert.assertTrue(interface2.getPreDestroy());
		
	}

}
