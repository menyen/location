package com.neogeo.location.model;

import java.util.List;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "locations")
public class LocationEntity {
	
	private String id;
	
	private List<String> names;
	
	private List<String> addressess;
	
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint location;
	
	private Boolean enabled;
	
	public LocationEntity(){
		
	}
	
	public LocationEntity(final List<String> names, final List<String> addressess, final GeoJsonPoint location, final Boolean enabled) {
		this.setNames(names);
	    this.setAddressess(addressess);
	    this.location = location;
	    this.enabled = enabled;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the location
	 */
	public GeoJsonPoint getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(GeoJsonPoint location) {
		this.location = location;
	}
	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the names
	 */
	public List<String> getNames() {
		return names;
	}

	/**
	 * @param names the names to set
	 */
	public void setNames(List<String> names) {
		this.names = names;
	}

	/**
	 * @return the addressess
	 */
	public List<String> getAddressess() {
		return addressess;
	}

	/**
	 * @param addressess the addressess to set
	 */
	public void setAddressess(List<String> addressess) {
		this.addressess = addressess;
	}
}