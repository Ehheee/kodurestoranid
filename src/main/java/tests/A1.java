package tests;

import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import thething.kodurestoranid.db.dataaccess.UniqueIdProvider;
import thething.kodurestoranid.db.mapping.NeoResultSetExtractor;

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
		JdbcTemplate template = new JdbcTemplate(ds2);
		NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(ds2);
		UniqueIdProvider idProvider = new UniqueIdProvider();
		idProvider.setJdbcTemplate(template);
		idProvider.setNamedParameterJdbcTemplate(namedTemplate);
		String newId = idProvider.getId();
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("id", newId);
		props.put("name", "name to test some shit 1");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("props", props);
		namedTemplate.update(query3, paramSource);
		//paramSource.addValue("ids", Arrays.asList(new String[] {"s1", "s2", "s3"}), Types.ARRAY);
		//namedTemplate.query(query2, paramSource, extractor);
		//template.query(query1, extractor, Arrays.asList(new String[] {"s1", "s2"}));
		//template.query(query1, extractor);
		//template.query("MATCH (a:System {id: 's1'})-[rr*1..]->(bb) WITH distinct(bb) as b MATCH (b)<-[r]-(c) RETURN c, {relationType: type(r), data: r} as rel, b", extractor);
		
		
		
		
		
	}
	
}
