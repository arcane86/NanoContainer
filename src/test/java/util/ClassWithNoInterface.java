package util;

import javax.inject.Inject;

public class ClassWithNoInterface {

	private Interface1 class1;
	
	@Inject
	public ClassWithNoInterface(Interface1 class1) {
		this.class1 = class1;
	}
	
	public Interface1 getClass1() {
		return this.class1;
	}
	
}
