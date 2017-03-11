package com.neogeo.location.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

import com.neogeo.location.model.LocationEntity;
import com.neogeo.location.repository.LocationRepository;

public class LocationServiceImpl implements LocationService {
	
	@Autowired
	private LocationRepository repository;
	
	/* (non-Javadoc)
	 * @see com.neogeo.location.service.LocationService#getLocationsNearAPoint(java.lang.String, java.lang.Double, java.lang.Double, java.lang.Double)
	 */
	@Override
	public List<LocationEntity> getLocationsNearAPoint(String name, Double longitude, Double latitude, Double distance){
		for (Map.Entry<String, String> entry : LocationEntity.NAMES_MAP.entrySet()) {
			name = name.replace(entry.getKey(), entry.getValue());
	    }
		List<LocationEntity> result = this.repository.findByNameAndLocationNearAndEnabledIsTrue(
				name,
				new Point(Double.valueOf(longitude), Double.valueOf(latitude)),
				new Distance(distance, Metrics.KILOMETERS));

		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.neogeo.location.service.LocationService#deleteLocation(java.lang.String)
	 */
	@Override
	public void deleteLocationByID(String id){
		LocationEntity loc = this.repository.findOne(id);
		loc.setEnabled(false);
		this.repository.save(loc);
	}
	
	/* (non-Javadoc)
	 * @see com.neogeo.location.service.LocationService#getLocation(java.lang.String)
	 */
	@Override
	public LocationEntity findLocationByID( String id){
		LocationEntity loc = this.repository.findOne(id);
		return loc;
	}
	
	/* (non-Javadoc)
	 * @see com.neogeo.location.service.LocationService#setLocation(java.lang.String, com.neogeo.location.model.LocationEntity)
	 */
	@Override
	public void replaceLocationByID(String id, LocationEntity newLoc){
		LocationEntity oldLoc = this.repository.findOne(id);
		oldLoc.setAddress(newLoc.getAddress());
		oldLoc.setEnabled(newLoc.getEnabled());
		oldLoc.setLocation(newLoc.getLocation());
		oldLoc.setName(newLoc.getName());
		this.repository.save(oldLoc);
	}
	
	/* (non-Javadoc)
	 * @see com.neogeo.location.service.LocationService#setLocationAttribute(java.lang.String, com.neogeo.location.model.LocationEntity)
	 */
	@Override
	public void updateLocationByID(String id, LocationEntity newLoc){
		LocationEntity oldLoc = this.repository.findOne(id);
		oldLoc.setAddress(newLoc.getAddress() != null ? newLoc.getAddress() : oldLoc.getAddress());
		oldLoc.setEnabled(newLoc.getEnabled() != null ? newLoc.getEnabled() : oldLoc.getEnabled());
		oldLoc.setLocation(newLoc.getLocation() != null ? newLoc.getLocation() : oldLoc.getLocation());
		oldLoc.setName(newLoc.getName() != null ? newLoc.getName() : oldLoc.getName());
		this.repository.save(oldLoc);
	}
}
