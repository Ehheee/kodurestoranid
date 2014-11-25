package thething.kodurestoranid.db.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import thething.kodurestoranid.dataobjects.FieldDescriptor;
import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.dataobjects.ThingRelation;
import thething.kodurestoranid.dataobjects.ThingType;
import thething.kodurestoranid.db.dataaccess.ThingDao;
import thething.kodurestoranid.db.dataaccess.ThingDaoImpl;
import thething.kodurestoranid.db.utils.ThingFilter;
import thething.kodurestoranid.db.utils.Tools;

@Component
public class TypeDescriptorService {

	private Log logger = LogFactory.getLog(getClass());
	
	private Map<String, ThingType> descriptions;
	private Map<String, String> labelsByIdPrefix;
	
	
	public TypeDescriptorService() {
		descriptions = new HashMap<String, ThingType>();
		labelsByIdPrefix = new HashMap<String, String>();
	}
	
	public ThingType getDescriptor(String type ) {
		if (descriptions.get(type) == null) {
			ThingFilter filter = new ThingFilter();
			filter.addLabel("System").addLabel("ThingType");
			filter.setProperty("typeName", type);
			Thing thing = thingDao.getByFilter(filter);
			if (thing != null) {
				descriptions.put(type, this.descriptorFromThing(thing));
			}
		}
		return descriptions.get(type);
	}
	
	public String getLabelFromId(String id) {
		return labelsByIdPrefix.get(id.substring(0, 2));
		
	}
	
	public Thing thingFromDescriptor(ThingType thingType) {
		Thing typeThing = new Thing();
		typeThing.addLabel("System");
		typeThing.addLabel("ThingType");
		typeThing.setProperty("typeName", thingType.getTypeName());
		typeThing.setProperty("id", thingType.getId());
		
		Map<String, List<ThingRelation>> thingRelations = new HashMap<>();
		List<ThingRelation> fieldRelations = new ArrayList<>();
		for (Entry<String, FieldDescriptor> e: thingType.getFields().entrySet()) {
			FieldDescriptor field = e.getValue();
			Thing fieldThing = new Thing();
			
			fieldThing.addLabel("System");
			fieldThing.addLabel("FieldDescriptor");
			
			fieldThing.setProperty("name", field.getName());
			fieldThing.setProperty("displayName", field.getDisplayName());
			fieldThing.setProperty("required", field.getRequired());
			fieldThing.setProperty("type", field.getType().getSimpleName());
			fieldThing.setProperty("id", field.getId());
			
			ThingRelation relation = new ThingRelation();
			relation.setFrom(typeThing);
			relation.setTo(fieldThing);
			relation.setType("hasField");
			fieldRelations.add(relation);
		}
		thingRelations.put("hasField", fieldRelations);
		typeThing.setRelations(thingRelations);
		return typeThing;
		
	}
	
	
	public ThingType descriptorFromThing(Thing thing) {
		ThingType thingType = new ThingType();
		if (thing.getLabels().get(0).equals("System") && thing.getLabels().get(1).equals("ThingType")) {
			thingType.setTypeName((String) thing.getProperty("typeName"));
		} else {
			logger.warn("Tried to create descriptor from invalid Thing: " + thing);
			return null;
		}
		thingType.setId((String) thing.getProperty("id"));
		List<ThingRelation> fieldRelations = thing.getRelations().get("hasField");
		for (ThingRelation fieldRelation: fieldRelations) {
			FieldDescriptor field = new FieldDescriptor();
			Thing fieldThing = fieldRelation.getTo();
			
			Class <?> type = Tools.stringToClass((String) fieldThing.getProperty("type"));
			
			field.setType(type);
			field.setName((String) fieldThing.getProperty("name"));
			field.setDisplayName((String) fieldThing.getProperty("displayName"));
			field.setRequired((Boolean) fieldThing.getProperty("required"));
			field.setId((String) fieldThing.getProperty("id"));
			thingType.addField(field);
		}
		return thingType;
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
