package main;

import impl.BeanTypeImplTest;
import impl.BindingImplTest;
import impl.InstancesManageImplTest;
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
RegistryImplTest.class,
InstancesManageImplTest.class,
NanoContainerImplTest.class,
})
public class AllTests {
}
