package com.neogeo.location.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.query.Param;
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

@RestController
public class LocationResource {
	
	@Autowired
	private LocationRepository repository;

	@RequestMapping(value = "locations/search/findByLocationNearAndEnabled", method = RequestMethod.GET)
	public List<LocationEntity> getLocationsByProximity(
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,
			@RequestParam("distance") Double distance) {

		List<LocationEntity> result = this.repository.findByLocationNearAndEnabled(
				new Point(Double.valueOf(longitude), Double.valueOf(latitude)),
				new Distance(distance, Metrics.KILOMETERS), true);

		return result;
	}
	
	@RequestMapping(value = "locations/search/findByLocationName", method = RequestMethod.GET)
	public List<LocationEntity> getLocationsByName(@RequestParam("name")String name){
		List<LocationEntity> result = null;
		/*ExampleMatcher matcher = ExampleMatcher.matching()
			    .withMatcher("names", );*/
		
		LocationEntity locationEntity = new LocationEntity();
		locationEntity.setNames(new ArrayList<String>(Arrays.asList(name)));
		Example<LocationEntity> example = Example.of(locationEntity);
		result = this.repository.findAll(example);
		return result;
	}
	
	@RequestMapping(value = "locations/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLocation(@PathVariable("id") String id){
		LocationEntity loc = this.repository.findOne(id);
		loc.setEnabled(false);
		this.repository.save(loc);
	}
	
	@RequestMapping(value = "locations/{id}", method = RequestMethod.GET)
	public LocationEntity getLocation(@PathVariable("id") String id){
		LocationEntity loc = this.repository.findOne(id);
		return loc;
	}
	
	@RequestMapping(value = "locations/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void setLocation(@PathVariable("id") String id, @RequestBody LocationEntity newLoc){
		LocationEntity oldLoc = this.repository.findOne(id);
		oldLoc.setAddressess(newLoc.getAddressess());
		oldLoc.setEnabled(newLoc.getEnabled());
		oldLoc.setLocation(newLoc.getLocation());
		oldLoc.setNames(newLoc.getNames());
		this.repository.save(oldLoc);
	}
	
	@RequestMapping(value = "locations/{id}", method = RequestMethod.PATCH)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void setLocationAttribute(@PathVariable("id") String id, @RequestBody LocationEntity newLoc){
		LocationEntity oldLoc = this.repository.findOne(id);
		oldLoc.setAddressess(newLoc.getAddressess() != null ? newLoc.getAddressess() : oldLoc.getAddressess());
		oldLoc.setEnabled(newLoc.getEnabled() != null ? newLoc.getEnabled() : oldLoc.getEnabled());
		oldLoc.setLocation(newLoc.getLocation() != null ? newLoc.getLocation() : oldLoc.getLocation());
		oldLoc.setNames(newLoc.getNames() != null ? newLoc.getNames() : oldLoc.getNames());
		this.repository.save(oldLoc);
	}
	
}