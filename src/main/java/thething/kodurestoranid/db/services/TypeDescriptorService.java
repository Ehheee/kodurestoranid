package thething.kodurestoranid.db.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import thething.kodurestoranid.dataobjects.FieldDescriptor;
import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.dataobjects.ThingRelation;
import thething.kodurestoranid.dataobjects.TypeDescriptor;
import thething.kodurestoranid.db.dataaccess.ThingDao;

public class TypeDescriptorService {

	private Map<String, TypeDescriptor> descriptions;
	private Map<String, String> labelsByIdPrefix;
	
	
	
	public TypeDescriptor getDescriptor(String type){
		if(descriptions.get(type) == null){
			Thing thing = thingDao.getByLabel(type);
			
		}
		return descriptions.get(type);
	}
	
	public String getLabelFromId(String id){
		return labelsByIdPrefix.get(id.substring(0, 2));
		
	}
	
	
	
	
	public TypeDescriptor descriptorFromThing(Thing thing){
		TypeDescriptor typeDescriptor = new TypeDescriptor();
		
		for(String label:thing.getLabels()){
			if(label != "ThingType"){
				typeDescriptor.setType(label);
			}
		}
		
		
		List<ThingRelation> fieldRelations = thing.getRelations().get("hasProperty");
		for(ThingRelation fieldRelation: fieldRelations){
			FieldDescriptor field = new FieldDescriptor();
			Thing fieldThing = fieldRelation.getTo();
			String fieldName = (String)fieldThing.getProperty("name");
			
			field.setDisplayName((String) fieldThing.getProperty("displayName"));
			field.setName(fieldName);
			field.setRequired((Boolean)fieldThing.getProperty("required"));
			
			typeDescriptor.addField(field);
		}
		return typeDescriptor;
	}
	

	
	
	@Autowired
	private ThingDao thingDao;
	public ThingDao getThingDao() {
		return thingDao;
	}
	public void setThingDao(ThingDao thingDao) {
		this.thingDao = thingDao;
	}
	
}
