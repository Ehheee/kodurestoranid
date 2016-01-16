package thething.kodurestoranid.dataobjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	public String getId() {
		Object id = properties.get("id");
		if (id == null) {
			return null;
		} else {
			return (String) id;
		}
	}
	
	public void setId(String id) {
		properties.put("id", id);
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	@JsonIgnore
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



	



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result
				+ ((properties == null) ? 0 : properties.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ThingRelation other = (ThingRelation) obj;
		if (from == null) {
			if (other.from != null) {
				return false;
			}
		} else if (!from.equals(other.from)) {
			return false;
		}
		if (properties == null) {
			if (other.properties != null) {
				return false;
			}
		} else if (!properties.equals(other.properties)) {
			return false;
		}
		if (to == null) {
			if (other.to != null) {
				return false;
			}
		} else if (!to.equals(other.to)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}



	
	
	
}
