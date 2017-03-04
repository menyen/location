package com.neogeo.location.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
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
	public List<LocationEntity> getLocationsByLocationNearAndEnabled(
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,
			@RequestParam("distance") Double distance) {

		List<LocationEntity> result = this.repository.findByLocationNearAndEnabled(
				new Point(Double.valueOf(longitude), Double.valueOf(latitude)),
				new Distance(distance, Metrics.KILOMETERS), true);

		return result;
	}
	
	@RequestMapping(value = "locations/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLocation(@PathVariable("id") String id){
		LocationEntity loc = this.repository.findOne(id);
		loc.setEnabled(false);
		this.repository.save(loc);
	}
}