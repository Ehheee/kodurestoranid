package tests;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import thething.kodurestoranid.db.dataaccess.ThingDaoImpl;
import thething.kodurestoranid.db.utils.ThingFilter;

public class A2 {

	
	public static void main(String[] args) {
		BasicDataSource ds2 = new BasicDataSource();
		ds2.setDriverClassName("org.neo4j.jdbc.Driver");
		ds2.setUrl("jdbc:neo4j://localhost:7474/?debug=true");
		ds2.setMaxActive(10);
		ds2.setMaxIdle(5);
		ds2.setInitialSize(5);
		
		
		ThingDaoImpl thingDao = new ThingDaoImpl();
		thingDao.setDataSource(ds2);
		
		
		
		
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(ds2);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		ThingFilter filter = new ThingFilter();
		filter.addLabel("System").addLabel("ThingType");
		filter.setProperty("typeName", "Chef");
		filter.setProperty("id", "Sy3");
		
		System.out.println(filter.getQuery());
		SqlRowSet rs = template.queryForRowSet(filter.getQuery(), filter.getProperties());
		
		
		
		/*
		Thing thing = new Thing();
		thing.setProperty("name", "testName");
		thing.addLabel("System");
		thing.addLabel("Test");
		
		Thing thing2 = new Thing();
		thing2.setProperty("name", "secondTHing");
		thing2.addLabel("System");
		thing2.addLabel("Test2");
		
		ThingRelation relation = new ThingRelation();
		relation.setFrom(thing);
		relation.setTo(thing2);
		relation.setType("hasChild");
		
		thing.setProperty("id", "Sy1");
		System.out.println(relation.getFrom().getProperty("id"));
		ThingRelationDao relationDao = new ThingRelationDao();
		relationDao.createRelation(relation);
		*/
		
		
		/*
		Thing thing = new Thing();
		thing.setProperty("id", "Sy3");
		thing.setProperty("name", "location");
		thing.setProperty("displayName", "Koka aadress");
		thing.setProperty("required", true);
		thing.setProperty("type", "String");
		thingDao.update(thing);
		*/
	}
}
