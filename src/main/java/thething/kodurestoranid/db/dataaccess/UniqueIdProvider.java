package thething.kodurestoranid.db.dataaccess;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

/**
 * Class for providing unique ids to neo4j nodes. Ids provided are in string form and also contains the type string for the possible case when
 * it is neccessary to find out the type of node before querying the database.
 * @author Kaur
 *
 */
@Component
public class UniqueIdProvider {

	private Log logger = LogFactory.getLog(getClass());
	
	private static String query = "MERGE (id:UniqueId {name: {1}, str: {2} }) "
								+"ON CREATE SET id.count = 1 "
								+"ON MATCH SET id.count = id.count + 1 "
								+"RETURN id.str + id.count AS uid ";
	
	
	
	public String getId(){
		SqlRowSet rs =jdbcTemplate.queryForRowSet(query, "System", "s");
		return extractId(rs);
	}
	
	public String getId(String type){
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("name", type);
		paramSource.addValue("str", type.substring(0, 2).toLowerCase());
		
		logger.info(jdbcTemplate == null);
		logger.info(type == null + " type");
		SqlRowSet rs = jdbcTemplate.queryForRowSet(query, type, type.substring(0, 2));
		return extractId(rs);
	}
	
	
	private String extractId(SqlRowSet rs){
		while(rs.next()){
			Object o = rs.getObject("uid");
			if(o instanceof String){
				return (String)o;
			}
			
		}
		return null;
	}
	
	
	
	
	
	private BasicDataSource dataSource;
	protected JdbcTemplate jdbcTemplate;
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
	    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
