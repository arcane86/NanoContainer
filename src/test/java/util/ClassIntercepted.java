package util;

public class ClassIntercepted implements InterfaceIntercepted {
	
	@Intercepted
	public String method(String param) {
		System.out.println(param);
		return param;
	}

}
