package thething.kodurestoranid.dataobjects;

import java.util.HashMap;
import java.util.Map;

public class ThingRelation {

	private String name;
	private Thing from;
	private Thing to;
	private Map<String, Object> properties;
	
	
	public ThingRelation(){
		properties = new HashMap<String, Object>();
	}
	
	
	
	public Object getProperty(String name){
		return properties.get(name);
	}
	public void setProperty(String key, Object value){
		properties.put(key, value);
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Thing getFrom() {
		return from;
	}
	public void setFrom(Thing from) {
		this.from = from;
	}
	public Thing getTo() {
		return to;
	}
	public void setTo(Thing to) {
		this.to = to;
	}
	public Map<String, Object> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	
}
