package com.neogeo.location.repository;

import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.neogeo.location.model.LocationEntity;

/**
 * @author ng
 *
 * This interface is used to query the database and it will be implemented automatically by Spring boot.
 * Spring boot is limited to what it can implement. To create methods that Spring cannot implement, 
 * this interface must extend a custom class
 */
@RepositoryRestResource(collectionResourceRel = "locations", path = "locations")
public interface LocationRepository extends MongoRepository<LocationEntity, String> {

	List<LocationEntity> findByLocationNearAndEnabledIsTrue(Point p, Distance d);
	
	List<LocationEntity> findByNamesInAndEnabledIsTrue(List<String> names);
}
