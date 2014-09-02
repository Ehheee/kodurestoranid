package tests;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import thething.kodurestoranid.dataobjects.ThingNode;

public class AbstractExtractor<K> implements ResultSetExtractor<Map<String, K>>{

	private Log logger = LogFactory.getLog(getClass());
	private Class<K> type;
	private AbstractMapper<K> mapper;
	
	public AbstractExtractor(Class<K> type){
		logger.info(type.getClass().getTypeParameters()[0]);
		this.type = type;
		mapper = new AbstractMapper<K>(type);
	}
	
	public Map<String, K> extractData(ResultSet rs) throws SQLException, DataAccessException {
		return null;
		
	}

}
