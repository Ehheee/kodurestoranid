package thething.kodurestoranid.db.dataaccess;


import java.util.Collection;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public  class BaseDao {

	protected Log logger = LogFactory.getLog(getClass());
	public enum ActionType {
		INSERT, DELETE
	}
	public enum ObjectType{
		THING, USER
	}
	
	@Autowired
	private BasicDataSource dataSource;
	protected JdbcTemplate jdbcTemplate;
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void setDataSource(DataSource dataSource) {
	    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void logCollection(Collection<?> collection){
		for(Object o: collection){
			logger.info(o);
		}
	}
	
	
}
