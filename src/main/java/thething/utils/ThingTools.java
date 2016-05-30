package thething.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import thething.one.dataobjects.Thing;

public class ThingTools {
	final static Log logger = LogFactory.getLog(ThingTools.class);
	@SuppressWarnings("unchecked")
	public static Thing createThing(LinkedHashMap<String, Object> m) {
		Thing thing = new Thing();
		for (Entry<String, Object> entry: m.entrySet()) {
			if (entry.getKey() == "labels" ){
				if (entry.getValue() instanceof ArrayList) {
					thing.setLabels((List<String>) entry.getValue());
				}
			}
			
			if (entry.getKey() == "data") {
				if (entry.getValue() instanceof LinkedHashMap) {
					SortedMap<String, Object> props = new TreeMap<>((Map<String, Object>) entry.getValue());
					thing.setProperties(props);
				}
				
			}
		}
		logger.info("Mapped: " + thing);
		return thing;
	}
	@SuppressWarnings("unchecked")
	public static Thing createThingWithRelationsFromMap(LinkedHashMap<String, Object> m) {
		Thing thing = new Thing();
		for (Entry<String, Object> entry: m.entrySet()) {
			if (entry.getKey() == "labels" ){
				if (entry.getValue() instanceof ArrayList) {
					thing.setLabels((List<String>) entry.getValue());
				}
			}
			
			if (entry.getKey() == "data") {
				if (entry.getValue() instanceof LinkedHashMap) {
					SortedMap<String, Object> props = new TreeMap<>((Map<String, Object>) entry.getValue());
					thing.setProperties(props);
				}
				
			}
			if (entry.getKey() == "relationsOutgoing") {
				if (entry.getValue() instanceof LinkedHashMap) {
					Map<String, Object> relations = (LinkedHashMap<String, Object>) entry.getValue();
				}
			}
		}
		logger.info("Mapped: " + thing);
		return thing;
	}
	public static String getString(Map<String, Object> map, String key) {
		if (map.get(key) != null && map.get(key) instanceof String) {
			return (String) map.get(key);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static List<String> getStringArray(Map<String, Object> map, String key) {
		if (map.get(key) != null && map.get(key) instanceof List) {
			List<?> list = (List<?>) map.get(key);
			if (list.size() > 0 && list.get(0) instanceof String) {
				return (List<String>) list;
			}
		}
		return null;
	}
}
