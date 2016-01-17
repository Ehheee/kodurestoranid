package thething.one.dataobjects;

import java.util.HashMap;
import java.util.Map;

public class ThingType {

	private String typeName;
	private String id;
	private Map<String, FieldDescriptor> fields;						//Field name used as key
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public ThingType(){
		fields = new HashMap<String, FieldDescriptor>();
	}
	
	public void addField(FieldDescriptor field){
		fields.put(field.getName(), field);
	}   
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Map<String, FieldDescriptor> getFields() {
		return fields;
	}
	public void setFields(Map<String, FieldDescriptor> fields) {
		this.fields = fields;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TypeDescriptor:").append(typeName).append("[ ");
		String comma = "";
		for (FieldDescriptor field:fields.values()) {
			sb.append("{ ");
			sb.append(comma).append("id: ").append(id).append(" name: ").append(field.getName()).append(" type: ").append(field.getType()).append(" displayName: ").append(field.getDisplayName()).append(" required: ").append(field.getRequired());
			comma = ", ";
			sb.append(" }");
		}
		sb.append(" ]");
		return sb.toString();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((typeName == null) ? 0 : typeName.hashCode());
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
		ThingType other = (ThingType) obj;
		if (fields == null) {
			if (other.fields != null) {
				return false;
			}
		} else if (!fields.equals(other.fields)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (typeName == null) {
			if (other.typeName != null) {
				return false;
			}
		} else if (!typeName.equals(other.typeName)) {
			return false;
		}
		return true;
	}

	
	
	
	
}
