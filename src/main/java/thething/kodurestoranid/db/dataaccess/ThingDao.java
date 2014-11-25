package thething.kodurestoranid.db.dataaccess;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import thething.kodurestoranid.dataobjects.Thing;
import thething.kodurestoranid.db.utils.ThingFilter;

public interface ThingDao {

	@Transactional
	public Thing createOrUpdateWithRelations(Thing thing);
	public Thing getByFilter(ThingFilter filter);
}
