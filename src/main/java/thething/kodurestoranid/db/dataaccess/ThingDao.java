package thething.kodurestoranid.db.dataaccess;


import org.springframework.transaction.annotation.Transactional;

import thething.exceptions.DatabaseException;
import thething.kodurestoranid.cyto.dataobjects.CytoWrapper;
import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.db.utils.NeoResultWrapper;
import thething.kodurestoranid.db.utils.ThingFilter;

public interface ThingDao {

	@Transactional
	public Thing createOrUpdateWithRelations(Thing thing) throws DatabaseException;
	public Thing getThingByFilter(ThingFilter filter) throws DatabaseException;
	public NeoResultWrapper getResultsByFilter(ThingFilter filter) throws DatabaseException;
	public CytoWrapper getCytoWrapperByFilter(ThingFilter filter) throws DatabaseException;
}
