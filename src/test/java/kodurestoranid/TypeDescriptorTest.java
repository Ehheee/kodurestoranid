package kodurestoranid;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import thething.one.dataobjects.FieldDescriptor;
import thething.one.dataobjects.Thing;
import thething.one.dataobjects.ThingType;
import thething.one.db.services.TypeDescriptorService;

public class TypeDescriptorTest {

	@Test
	public void testConversions(){
		ThingType thingType = new ThingType();
		thingType.setTypeName("food");
		FieldDescriptor field1 = new FieldDescriptor();
		field1.setDisplayName("Name");
		field1.setName("name");
		field1.setRequired(true);
		field1.setType(String.class);
		
		FieldDescriptor field2 = new FieldDescriptor();
		field2.setName("price");
		field2.setDisplayName("Price");
		field2.setRequired(true);
		field2.setType(BigDecimal.class);
		
		thingType.addField(field1);
		thingType.addField(field2);
		
		TypeDescriptorService service = new TypeDescriptorService();
		Thing thing = service.thingFromDescriptor(thingType);
		System.out.println(thing);
		ThingType typeDesc2 = service.descriptorFromThing(thing);
		System.out.println(thingType);
		System.out.println(typeDesc2);
		assertEquals(thingType, typeDesc2);
	}
}
