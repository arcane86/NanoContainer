package util;

import javax.inject.Inject;
import javax.inject.Named;

public class ClassProducing implements InterfaceProducing {
	
	@Inject
	int integer24;
	
	@Inject @Named
	int integer42;

	@Inject
	ClassProduced classProduced;
	
	public int getInteger24() {
		return integer24;
	}
	
	public int getInteger42() {
		return integer42;
	}

	public ClassProduced getClassProduced() {
		return classProduced;
	}

}
