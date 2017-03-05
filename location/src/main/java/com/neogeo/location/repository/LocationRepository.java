package com.neogeo.location.repository;

import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.neogeo.location.model.LocationEntity;

@RepositoryRestResource(collectionResourceRel = "locations", path = "locations")
public interface LocationRepository extends MongoRepository<LocationEntity, String> {

	List<LocationEntity> findByLocationNearAndEnabledIsTrue(Point p, Distance d);
	
	List<LocationEntity> findByNamesInAndEnabledIsTrue(List<String> names);
}
