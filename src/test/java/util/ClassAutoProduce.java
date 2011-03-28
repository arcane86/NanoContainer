package util;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

public class ClassAutoProduce implements InterfaceAutoProduce {
	
	@Inject @Named
	private ClassProduced classProduced;
	
	@Inject
	private ClassWithProducers classWithProducers;

	public ClassProduced getClassProduced() {
		return this.classProduced;
	}

	@Produces @Named
	public ClassProduced produce(@Named int value) {
		return new ClassProduced(value);
	}

}
