package thething.kodurestoranid.db.dataaccess;

import java.util.List;

import thething.kodurestoranid.dataobjects.ThingNode;
import thething.kodurestoranid.db.util.ThingNodeFilter;

public class ThingNodeDao extends BaseDao{

	public List<ThingNode> getThingNodes(ThingNodeFilter filter){
		String query = filter.getQuery();
		this.namedParameterJdbcTemplate.queryForList
		
		return null;
	}
}
