package api;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface BeanType {

	Set<Annotation> getQualifiers();
	Class<?> getType();
	String getDescription();

}
