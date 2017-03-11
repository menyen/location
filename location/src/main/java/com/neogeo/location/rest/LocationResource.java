package com.neogeo.location.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neogeo.location.model.LocationEntity;
import com.neogeo.location.service.LocationService;

/**
 * @author ng
 *
 * Controller class, this class will take care of the request calls.
 * Operations that aren't implemented here are already implemented by Spring boot.
 * Methods that are different from the conventional are also implemented here. 
 * Ex: DELETE doesn't actually delete, it only disables db entry
 */

@RestController
public class LocationResource {
	
	@Autowired
	private LocationService service;

	/**
	 * SEARCH operation by a near point
	 * 
	 * @param latitude
	 * @param longitude
	 * @param distance
	 * @return list o locations near a point
	 */
	@RequestMapping(value = "locations/search/findByNameAndLocationNear", method = RequestMethod.GET)
	public List<LocationEntity> getLocationsByProximity(
			@RequestParam("name") String name,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,
			@RequestParam("distance") Double distance) {

		return this.service.getLocationsNearAPoint(name, Double.valueOf(longitude), Double.valueOf(latitude), distance);
	}
	
	/**
	 * DELETE operation
	 * 
	 * @param id
	 */
	@RequestMapping(value = "locations/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLocation(@PathVariable("id") String id){
		this.service.deleteLocationByID(id);
	}
	
	/**
	 * READ operation
	 * 
	 * @param id
	 * @return location entity that corresponds to the given id
	 */
	@RequestMapping(value = "locations/{id}", method = RequestMethod.GET)
	public LocationEntity getLocation(@PathVariable("id") String id){
		return service.findLocationByID(id);
	}
	
	/**
	 * REPLACE operation
	 * 
	 * @param id
	 * @param newLoc
	 */
	@RequestMapping(value = "locations/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void setLocation(@PathVariable("id") String id, @RequestBody LocationEntity newLoc){
		this.service.replaceLocationByID(id, newLoc);
	}
	
	/**
	 * UPDATE operation
	 * 
	 * @param id
	 * @param newLoc
	 */
	@RequestMapping(value = "locations/{id}", method = RequestMethod.PATCH)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void setLocationAttribute(@PathVariable("id") String id, @RequestBody LocationEntity newLoc){
		this.service.updateLocationByID(id, newLoc);
	}
	
}