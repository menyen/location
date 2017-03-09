package com.neogeo.location.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neogeo.location.model.LocationEntity;
import com.neogeo.location.repository.LocationRepository;

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
	private LocationRepository repository;

	/**
	 * Given a center in longitude and latitude, and a distance in kilometers from that center, 
	 * it finds all locations within that given space
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

		for (Map.Entry<String, String> entry : LocationEntity.NAMES_MAP.entrySet()) {
			name = name.replace(entry.getKey(), entry.getValue());
	    }
		List<LocationEntity> result = this.repository.findByNameAndLocationNearAndEnabledIsTrue(
				name,
				new Point(Double.valueOf(longitude), Double.valueOf(latitude)),
				new Distance(distance, Metrics.KILOMETERS));

		return result;
	}
	
	/**
	 * DELETE operation
	 * 
	 * @param id
	 */
	@RequestMapping(value = "locations/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLocation(@PathVariable("id") String id){
		LocationEntity loc = this.repository.findOne(id);
		loc.setEnabled(false);
		this.repository.save(loc);
	}
	
	/**
	 * READ operation
	 * 
	 * @param id
	 * @return location entity that corresponds to the given id
	 */
	@RequestMapping(value = "locations/{id}", method = RequestMethod.GET)
	public LocationEntity getLocation(@PathVariable("id") String id){
		LocationEntity loc = this.repository.findOne(id);
		return loc;
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
		LocationEntity oldLoc = this.repository.findOne(id);
		oldLoc.setAddress(newLoc.getAddress());
		oldLoc.setEnabled(newLoc.getEnabled());
		oldLoc.setLocation(newLoc.getLocation());
		oldLoc.setName(newLoc.getName());
		this.repository.save(oldLoc);
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
		LocationEntity oldLoc = this.repository.findOne(id);
		oldLoc.setAddress(newLoc.getAddress() != null ? newLoc.getAddress() : oldLoc.getAddress());
		oldLoc.setEnabled(newLoc.getEnabled() != null ? newLoc.getEnabled() : oldLoc.getEnabled());
		oldLoc.setLocation(newLoc.getLocation() != null ? newLoc.getLocation() : oldLoc.getLocation());
		oldLoc.setName(newLoc.getName() != null ? newLoc.getName() : oldLoc.getName());
		this.repository.save(oldLoc);
	}
	
}