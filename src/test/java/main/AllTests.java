package main;

import impl.BeanTypeImplTest;
import impl.BindingImplTest;
import impl.InstancesManagerImplTest;
import impl.InterceptorBindingImpl;
import impl.InterceptorBindingImplTest;
import impl.NanoContainerImplTest;
import impl.ProducerImplTest;
import impl.RegistryImplTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value={
BeanTypeImplTest.class,
BindingImplTest.class,
ProducerImplTest.class,
InterceptorBindingImplTest.class,
RegistryImplTest.class,
InstancesManagerImplTest.class,
NanoContainerImplTest.class,
})
public class AllTests {
}
