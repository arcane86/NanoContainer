package util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

public class Class2 implements Interface2 {
	
	private Interface1 class1;
	private Interface1 class1b;	
	@Inject private Interface1 class1c;
	private boolean postConstruct = false;
	private boolean preDestroy = false;
	
	private Interface1 class1Named;
	private Interface1 class1bNamed;	
	@Inject @Named private Interface1 class1cNamed;
	
	
	@Inject
	public Class2(Interface1 class1, @Named Interface1 class1Named) {
		this.class1 = class1;
		this.class1Named = class1Named;
	}
	
	public Interface1 getClass1() {
		return this.class1;
	}
	
	public Interface1 getClass1b() {
		return this.class1b;
	}
	
	public Interface1 getClass1c() {
		return this.class1c;
	}
	
	public Interface1 getClass1Named() {
		return class1Named;
	}

	public Interface1 getClass1bNamed() {
		return class1bNamed;
	}

	public Interface1 getClass1cNamed() {
		return class1cNamed;
	}

	@Inject
	public void setClass1(Interface1 class1) {
		this.class1b = class1;
	}
	
	@Inject
	public void setClass1Named(@Named Interface1 class1) {
		this.class1bNamed = class1;
	}
	
	@PostConstruct
	public void postConstruct() {
		postConstruct = true;
	}
	
	@PreDestroy
	public void preDestroy() {
		preDestroy = true;
	}

	public boolean getPostConstrcut() {
		return postConstruct;
	}

	public boolean getPreDestroy() {
		return preDestroy;
	}

}
