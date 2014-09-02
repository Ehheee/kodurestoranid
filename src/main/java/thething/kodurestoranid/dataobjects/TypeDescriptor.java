package thething.kodurestoranid.dataobjects;

import java.util.Map;

public class TypeDescriptor {

	private String type;
	private Map<String, FieldDescriptor> fields;
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
	
	
	
}
