package util;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class ClassWithProducers {
	
	@Produces
	public int intProducer24() {
		return 24;
	}
	
	@Produces @Named
	public int intProducer42() {
		return 42;
	}
	
	@Produces
	public ClassProduced classProducer() {
		return new ClassProduced(24);
	}

}
