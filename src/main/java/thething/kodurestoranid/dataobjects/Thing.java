package thething.kodurestoranid.dataobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * Class to represent a random thing.
 * It can have any kinds of properties having any kinds of values.
 * It can have any kinds of incoming and outgoing relations to other things.
 * 
 * @author Kaur
 *
 */
public class Thing {

	//Incoming and outgoing relations mapped by relation type.
	private Map<String, Set<ThingRelation>> relationsIncoming;
	private Map<String, Set<ThingRelation>> relationsOutgoing;
	//Labels are hierarchical so 0 index is the root and the rest follow in order
	private List<String> labels;
	private Map<String, Object> properties;

	
	public Thing(){
		properties = new HashMap<String, Object>();
		labels = new ArrayList<String>();
		relationsIncoming = new HashMap<String, Set<ThingRelation>>();
		relationsOutgoing = new HashMap<String, Set<ThingRelation>>();
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
	
	@JsonIgnore
	public Map<String, Set<ThingRelation>> getRelationsIncoming() {
		return relationsIncoming;
	}
	public void setRelationsIncoming(Map<String, Set<ThingRelation>> relationsIncoming) {
		this.relationsIncoming = relationsIncoming;
	}

	public Map<String, Set<ThingRelation>> getRelationsOutgoing() {
		return relationsOutgoing;
	}
	public void setRelationsOutgoing(Map<String, Set<ThingRelation>> relationsOutgoing) {
		this.relationsOutgoing = relationsOutgoing;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public void addLabel(String label){
		this.labels.add(label);
	}
	public Map<String, Object> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	public void addRelation(ThingRelation relation) {
		if (relation.getFrom().equals(this)) {
			if (!relationsOutgoing.containsKey(relation.getType())) {
				relationsOutgoing.put(relation.getType(), new HashSet<ThingRelation>());
			}
			relationsOutgoing.get(relation.getType()).add(relation);
		} else if (relation.getTo().equals(this)) {
			if (!relationsIncoming.containsKey(relation.getType())) {
				relationsIncoming.put(relation.getType(), new HashSet<ThingRelation>());
			}
			relationsIncoming.get(relation.getType()).add(relation);
		}
			
		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result
				+ ((properties == null) ? 0 : properties.hashCode());
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
		Thing other = (Thing) obj;
		if (labels == null) {
			if (other.labels != null) {
				return false;
			}
		} else if (!labels.equals(other.labels)) {
			return false;
		}
		if (properties == null) {
			if (other.properties != null) {
				return false;
			}
		} else if (!properties.equals(other.properties)) {
			return false;
		}
		return true;
	}
	
	
}
