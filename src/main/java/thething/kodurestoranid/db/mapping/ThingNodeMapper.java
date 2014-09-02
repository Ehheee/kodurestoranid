package thething.kodurestoranid.db.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import thething.kodurestoranid.dataobjects.FieldDescriptor;
import thething.kodurestoranid.dataobjects.ThingNode;
import thething.kodurestoranid.dataobjects.TypeDescriptor;
import thething.kodurestoranid.db.services.TypeDescriptorService;

import org.springframework.jdbc.core.RowMapper;

public class ThingNodeMapper implements RowMapper<T>{

	
	
	
	public ThingNode mapRow(ResultSet rs, int rowNum) throws SQLException {
		ThingNode dn = new ThingNode();
		String type = rs.getString("o1.type");
		String id = rs.getString("o1.type");
		String name = rs.getString("o1.name");
		String text1 = rs.getString("o1.text1");
		String text2 = rs.getString("o1.text2");
		Date date1 = rs.getDate("o1.date1");
		Date date2 = rs.getDate("o1.date2");
		Long author = rs.getLong("o1.author");
		Boolean active = rs.getBoolean("o1.active");
		Boolean disabled = rs.getBoolean("o1.disabled");
		Boolean requiresPermission = rs.getBoolean("o1.requiresPermission");
		String parentId = rs.getString("o1.parentId");
		Integer sort = rs.getInt("o1.sort");
		String location = rs.getString("o1.location");
		String custom01 = rs.getString("o1.custom01");
		rs.getMetaData().get
		return dn;
	}
	
	private TypeDescriptorService typeDescriptorService;

}
