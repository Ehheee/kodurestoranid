package thething.kodurestoranid.db.utils;

import java.util.List;

public class Tools {

	public static String stringFromLabels(List<String> labels){
		StringBuilder b = new StringBuilder("");
		for(String s: labels){
			b.append(":").append(s);
		}
		return b.toString();
	}
}
