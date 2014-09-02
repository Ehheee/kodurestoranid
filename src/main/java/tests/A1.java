package tests;

import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import thething.kodurestoranid.dataobjects.ThingNode;
import thething.kodurestoranid.db.mapping.ThingNodeMapper;

public class A1 {
	static String query = "select o1.*, o2.*, o3.* from objects o1" 
			+" left join objectRelations or1 on o1.id=or1.objectId or o1.id=or1.relationId" 
			+" left join objects o2 on (or1.relationId=o2.id or or1.objectId=o2.id) and o2.id != o1.id" 
			+" left join objectRelations or2 on o2.id = or2.objectId or o2.id = or2.relationId"
			+" left join objects o3 on (or2.relationId=o3.id or or2.objectId=o3.id) and o3.id != o2.id and o3.id != o1.id"
			+" where o1.id=1;";
	
	public static void main(String[] args){
		BasicDataSource ds2 = new BasicDataSource();
		ds2.setDriverClassName("org.postgresql.Driver");
		ds2.setUsername("kodurestoranid");
		ds2.setPassword("nhf3484bvcr");
		ds2.setUrl("jdbc:postgresql://localhost:5432/kodurestoranid?useUnicode=true&amp;characterEncoding=utf8");
		ds2.setMaxActive(10);
		ds2.setMaxIdle(5);
		ds2.setInitialSize(5);
		ds2.setValidationQuery("SELECT 1");
		JdbcTemplate template = new JdbcTemplate(ds2);
		System.out.println(query);
		ThingNode t;
		AbstractExtractor<ThingNode> extractor = new AbstractExtractor<ThingNode>(ThingNode.class);
		template.query(query, extractor);
		
	}
}
