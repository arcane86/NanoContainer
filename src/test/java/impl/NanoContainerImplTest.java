package impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
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
import util.ClassIntercepted;
import util.ClassIntercepting;
import util.ClassIntercepting2;
import util.ClassProduced;
import util.ClassProducing;
import util.ClassWithNoInterface;
import util.ClassWithProducers;
import util.Interface1;
import util.Interface2;
import util.InterfaceAutoProduce;
import util.InterfaceIntercepted;
import util.InterfaceProducing;
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
		nanoContainer.register(Class1Inheritence.class);
		Assert.assertEquals(10, nanoContainer.getRegistry().size());
		nanoContainer.register(ClassWithProducers.class);
		Assert.assertEquals(14, nanoContainer.getRegistry().size());
	}
	
	@Test(expected=NanoContainerRuntimeException.class)
	public void testRegisterClassWithNoInterface() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(Class1.class);
		Assert.assertEquals(2, nanoContainer.getRegistry().size());
		nanoContainer.register(ClassWithNoInterface.class);
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
		Assert.assertEquals(2, nanoContainer.getRegistry().size());
		nanoContainer.register(ClassWithProducers.class);
		Assert.assertEquals(6, nanoContainer.getRegistry().size());
	}

	@Test
	public void testGetInstance() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(Class1.class);
		Interface1 interface1 = nanoContainer.getInstance(Interface1.class);
		Assert.assertNotNull(interface1);
		nanoContainer.register(Class1b.class);
		nanoContainer.register(Class2.class);
		Interface2 interface2 = nanoContainer.getInstance(Interface2.class);
		Assert.assertNotNull(interface2);
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
	public void testGetInstanceFromClass() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(Class1.class);
		Interface1 interface1 = nanoContainer.getInstance(Class1.class);
		Assert.assertNotNull(interface1);
		nanoContainer.register(Class1b.class);
		nanoContainer.register(Class2.class);
		Interface2 interface2 = nanoContainer.getInstance(Class2.class);
		Assert.assertNotNull(interface2);
	}
	
	@Test
	public void testGetClassInstance() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(Class1.class);
		Class1 class1 = (Class1)((NanoContainerInvocationHandlerImpl)Proxy.getInvocationHandler(nanoContainer.getInstance(Interface1.class))).getInstance();
		Assert.assertNotNull(class1);
		nanoContainer.register(Class1b.class);
		nanoContainer.register(Class2.class);
		Class2 class2 = (Class2)((NanoContainerInvocationHandlerImpl)Proxy.getInvocationHandler(nanoContainer.getInstance(Interface2.class))).getInstance();
		Assert.assertNotNull(class2);
	}
	
	@Test
	public void testGetClassInstanceFromClass() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(Class1.class);
		Class1 class1 = nanoContainer.getInstance(Class1.class);
		Assert.assertNotNull(class1);
		nanoContainer.register(Class1b.class);
		nanoContainer.register(Class2.class);
		Class2 class2 = nanoContainer.getInstance(Class2.class);
		Assert.assertNotNull(class2);
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
		InterfaceProducing classProducing = nanoContainer.getInstance(InterfaceProducing.class);
		int i24 = classProducing.getInteger24();
		Assert.assertNotNull(i24);
		Assert.assertEquals(24,i24);
		int i42 = classProducing.getInteger42();
		Assert.assertNotNull(i42);
		Assert.assertEquals(42,i42);
		ClassProduced classProduced = classProducing.getClassProduced();
		Assert.assertNotNull(classProduced);		
		Assert.assertEquals(ClassProduced.class,classProduced.getClass());
		Assert.assertEquals(24,classProduced.getValue());
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
	public void testInterception() {
		NanoContainer nanoContainer = new NanoContainerImpl();
		nanoContainer.register(ClassIntercepted.class);
		nanoContainer.register(ClassIntercepting.class);
		nanoContainer.register(ClassIntercepting2.class);
		InterfaceIntercepted classIntercepted = nanoContainer.getInstance(InterfaceIntercepted.class);
		classIntercepted.method("Hello");
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
