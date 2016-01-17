package thething.one.db.dataaccess;


import org.springframework.transaction.annotation.Transactional;

import thething.exceptions.DatabaseException;
import thething.one.cyto.dataobjects.CytoWrapper;
import thething.one.dataobjects.Thing;
import thething.one.db.utils.NeoResultWrapper;
import thething.one.db.utils.ThingFilter;

public interface ThingDao {

	@Transactional
	public Thing createOrUpdateWithRelations(Thing thing) throws DatabaseException;
	public Thing getThingByFilter(ThingFilter filter) throws DatabaseException;
	public NeoResultWrapper getResultsByFilter(ThingFilter filter) throws DatabaseException;
	public CytoWrapper getCytoWrapperByFilter(ThingFilter filter) throws DatabaseException;
}
