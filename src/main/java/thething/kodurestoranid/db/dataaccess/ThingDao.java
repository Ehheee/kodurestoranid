package thething.kodurestoranid.db.dataaccess;


import org.springframework.transaction.annotation.Transactional;

import thething.kodurestoranid.cyto.dataobjects.CytoWrapper;
import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.db.utils.NeoResultWrapper;
import thething.kodurestoranid.db.utils.ThingFilter;

public interface ThingDao {

	@Transactional
	public Thing createOrUpdateWithRelations(Thing thing);
	public Thing getThingByFilter(ThingFilter filter);
	public NeoResultWrapper getResultsByFilter(ThingFilter filter);
	public CytoWrapper getCytoWrapperByFilter(ThingFilter filter);
}
