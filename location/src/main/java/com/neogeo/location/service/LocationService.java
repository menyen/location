package com.neogeo.location.service;

import java.util.List;

import com.neogeo.location.model.LocationEntity;

public interface LocationService {

	
	/**
	 * Given a center in longitude and latitude, and a distance in kilometers from that center, 
	 * it finds locations with parameter name within that given space
	 * 
	 * @param latitude
	 * @param longitude
	 * @param distance
	 * @return list o locations near a point
	 */
	List<LocationEntity> getLocationsNearAPoint(String name, Double longitude, Double latitude, Double distance);

	
	/**
	 * DELETE from repository. This operation doesn't actually delete, as it only disables an entry in database
	 * 
	 * @param id
	 */
	void deleteLocationByID(String id);

	
	/**
	 * READ a LocationEntity found by ID
	 * 
	 * @param id
	 * @return location entity that corresponds to the given id
	 */
	LocationEntity findLocationByID(String id);

	
	/**
	 * REPLACE a LocationEntity by a new object of the class but with the same ID
	 * 
	 * @param id
	 * @param newLoc
	 */
	void replaceLocationByID(String id, LocationEntity newLoc);

	/**
	 * UPDATE an attribute of a LocationEntity found by ID
	 * 
	 * @param id
	 * @param newLoc
	 */
	void updateLocationByID(String id, LocationEntity newLoc);

}