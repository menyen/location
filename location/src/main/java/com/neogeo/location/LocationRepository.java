package com.neogeo.location;

import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "locations", path = "locations")
public interface LocationRepository extends MongoRepository<LocationEntity, String> {

	List<LocationEntity> findByLocationNear(Point p, Distance d);
}
