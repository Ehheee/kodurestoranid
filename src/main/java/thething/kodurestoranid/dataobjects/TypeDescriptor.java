package thething.kodurestoranid.dataobjects;

import java.util.HashMap;
import java.util.Map;

public class TypeDescriptor {

	private String type;
	private Map<String, FieldDescriptor> fields;						//Field name used as key
	
	public TypeDescriptor(){
		fields = new HashMap<String, FieldDescriptor>();
	}
	
	public void addField(FieldDescriptor field){
		fields.put(field.getName(), field);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, FieldDescriptor> getFields() {
		return fields;
	}
	public void setFields(Map<String, FieldDescriptor> fields) {
		this.fields = fields;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("TypeDescriptor:").append(type).append("{");
		for(FieldDescriptor field:fields.values()){
			sb.append(" name:").append(field.getName()).append(" displayName:").append(field.getDisplayName()).append(" required:").append(field.getRequired());
			sb.append(", ");
		}
		sb.append(" }");
		return sb.toString();
	}
	
}
