package tests;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import thething.kodurestoranid.dataobjects.FieldDescriptor;
import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.dataobjects.ThingRelation;
import thething.kodurestoranid.db.dataaccess.ThingDaoImpl;
import thething.kodurestoranid.db.dataaccess.UniqueIdProvider;
import thething.kodurestoranid.db.mapping.NeoResultSetExtractor;
import thething.kodurestoranid.db.services.TypeDescriptorService;
import thething.kodurestoranid.db.utils.ThingFilter;

public class A1 {

	private static String query1 = "MATCH (a:System) WHERE a.id IN {1} RETURN a";
	private static String query2 = "MATCH (a:System) WHERE a.id IN &ids RETURN a";
	private static String query3 = "CREATE (n:System:Test &props)";
	
	
	private static NeoResultSetExtractor extractor = new NeoResultSetExtractor();
	public static void main(String[] args){
		
		BasicDataSource ds2 = new BasicDataSource();
		ds2.setDriverClassName("org.neo4j.jdbc.Driver");
		ds2.setUrl("jdbc:neo4j://localhost:7474/?debug=true");
		ds2.setMaxActive(10);
		ds2.setMaxIdle(5);
		ds2.setInitialSize(5);
		
		UniqueIdProvider idProvider = new UniqueIdProvider();
		idProvider.setDataSource(ds2);
		
		TypeDescriptorService typeDescriptorService = new TypeDescriptorService();
		
		
		
		ThingFilter filter = new ThingFilter();
		filter.setLabel("Domain");
		filter.setProperty("id", "do1");
		/*
		Thing type = new Thing();
		type.setLabels(Arrays.asList(new String[] {"ThingType", "Chef"}));
		
		Thing field1 = new Thing();
		field1.setProperty("name", "FullName");
		field1.setProperty("displayName", "Koka nimi");
		field1.setProperty("required", true);
		
		Thing field2 = new Thing();
		field2.setProperty("name", "Location");
		field2.setProperty("displayName", "Koka Asukoht");
		field2.setProperty("required", true);
		
		List<ThingRelation> fieldsRelations = new ArrayList<ThingRelation>();
		ThingRelation rel1 = new ThingRelation();
		ThingRelation rel2 = new ThingRelation();
		rel1.setName("hasProperty");
		rel2.setName("hasProperty");
		rel1.setFrom(type);
		rel2.setFrom(type);
		rel1.setTo(field1);
		rel2.setTo(field2);
		fieldsRelations.add(rel1);
		fieldsRelations.add(rel2);
		Map<String, List<ThingRelation>>  relations = new HashMap<String, List<ThingRelation>>();
		relations.put("hasProperty", fieldsRelations);
		type.setRelations(relations);
		
		TypeDescriptorService service = new TypeDescriptorService();
		System.out.println(service.descriptorFromThing(type));
		
		*/
		
		
		
		JdbcTemplate template = new JdbcTemplate(ds2);
		NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(ds2);
		idProvider.setDataSource(ds2);
		String newId = idProvider.getId();
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("id", newId);
		props.put("name", "name to test some shit 1");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", "do1");
		namedTemplate.query(filter.getQuery(), paramSource, extractor);
		//template.query(query1, extractor, Arrays.asList(new String[] {"s1", "s2"}));
		//template.query(query1, extractor);
		//template.query("MATCH (a:System {id: 's1'})-[rr*1..]->(bb) WITH distinct(bb) as b MATCH (b)<-[r]-(c) RETURN c, {relationType: type(r), data: r} as rel, b", extractor);
		
		
		
		
		
	}
	
}
