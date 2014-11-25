package thething.kodurestoranid.dataobjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ThingRelation {

	private String type;
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
	
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("ThingRelation:").append(type);
		sb.append("{");
		for(Entry<String, Object> prop: properties.entrySet()){
			sb.append(prop.getKey()).append(": ").append(prop.getValue()).append(", ");
		}
		sb.append("}");
		return sb.toString();
		
	}
	
}
