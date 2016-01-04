package thething.kodurestoranid.db.dataaccess;


import java.util.Collection;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import thething.kodurestoranid.db.services.TypeDescriptorService;

public  class BaseDao {

	protected Log logger = LogFactory.getLog(getClass());
	public enum ActionType {
		INSERT, DELETE
	}
	public enum ObjectType{
		THING, USER
	}
	
	
	@Autowired
	protected TypeDescriptorService typeDescriptorService;
	public TypeDescriptorService getTypeDescriptorService() {
		return typeDescriptorService;
	}
	public void setTypeDescriptorService(TypeDescriptorService typeDescriptorService) {
		this.typeDescriptorService = typeDescriptorService;
	}


	
	
	@Autowired
	UniqueIdProvider uniqueIdProvider;
	public UniqueIdProvider getUniqueIdProvider() {
		return uniqueIdProvider;
	}
	public void setUniqueIdProvider(UniqueIdProvider uniqueIdProvider) {
		this.uniqueIdProvider = uniqueIdProvider;
	}

	
	/*
	private BasicDataSource dataSource;
	protected JdbcTemplate jdbcTemplate;
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
	    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Autowired
	DataSourceTransactionManager transactionManager;
	*/
}
