package thething.kodurestoranid.db.utils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tools {

	private static final Map<String, Class<?>> classes = createClassMap();
	
	private static Map<String, Class<?>> createClassMap(){
		Map<String, Class<?>> result = new HashMap<>();
		result.put("String", String.class);
		result.put("Integer", Integer.class);
		result.put("BigDecimal", BigDecimal.class);
		return Collections.unmodifiableMap(result);
	}
	
	public static String stringFromLabels(List<String> labels){
		StringBuilder b = new StringBuilder("");
		for(String s: labels){
			b.append(": ").append(s);
		}
		return b.toString();
	}
	
	public static Class<?> stringToClass(String s){
		return classes.get(s);
	}
}
