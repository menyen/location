package com.neogeo.location.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ng
 *
 * Entity that will be saved in MongoDB
 */
@Document(collection = "locations")
@CompoundIndexes({
	@CompoundIndex(name="unique_location", unique=true, def="{'name':1, 'address': 1}")
})
public class LocationEntity {
	
	private String id;
	
	private String name;
	
	private String address;
	
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint location;
	
	private Boolean enabled;
	
	public static final Map<String, String> NAMES_MAP;
	static {
        Map<String, String> namesMap = new HashMap<String, String>();
        namesMap.put("Al.", "Alameda");
        namesMap.put("Alm.", "Almirante");
        namesMap.put("Asp.", "Aspirante");
        namesMap.put("Av.", "Avenida");
        namesMap.put("Brig.", "Brigadeiro");
        namesMap.put("Cap.", "Capitão");
        namesMap.put("Com.", "Comandante");
        namesMap.put("Cel.", "Coronel");
        namesMap.put("D.", "Distrito");
        namesMap.put("D.", "Dom");
        namesMap.put("Dr.", "Doutor");
        namesMap.put("Dra.", "Doutora");
        namesMap.put("Ed.", "Edifício");
        namesMap.put("Edif.", "Edifício");
        namesMap.put("Eng.", "Engenheiro");
        namesMap.put("Esc.", "Escola");
        namesMap.put("Gal.", "Galeria");
        namesMap.put("Gen.", "General");
        namesMap.put("Gal.", "General");
        namesMap.put("Jd", "Jardim");
        namesMap.put("L.", "Largo");
        namesMap.put("Lg.", "Largo");
        namesMap.put("Maj.", "Major");
        namesMap.put("Mal.", "Marechal");
        namesMap.put("N. S.", "Nosso Senhor");
        namesMap.put("N. Sra.", "Nossa Senhora");
        namesMap.put("Pç.", "Praça");
        namesMap.put("Pça.", "Praça");
        namesMap.put("Prq.", "Parque");
        namesMap.put("Prof.", "Professor");
        namesMap.put("Profa.", "Professora");
        namesMap.put("R.", "Rua");
        namesMap.put("Rdv.", "Rodoviária");
        namesMap.put("Rod.", "Rodovia");
        namesMap.put("S.", "São");
        namesMap.put("Sarg.", "Sargento");
        namesMap.put("St.", "Santo");
        namesMap.put("Sto.", "Santo");
        namesMap.put("Sta.", "Santa");
        namesMap.put("Ten.", "Tenente");
        namesMap.put("V.", "Via");
        namesMap.put("Vd.", "Viaduto");
        NAMES_MAP = Collections.unmodifiableMap(namesMap);
    }
	
	public LocationEntity(){
		
	}
	
	public LocationEntity(final String name, final String address, final GeoJsonPoint location) {
		this.name = name;
	    this.address = address;
	    this.location = location;
	    this.enabled = true;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the names to set
	 */
	public void setName(String name) {
		for (Map.Entry<String, String> entry : NAMES_MAP.entrySet()) {
			name = name.replace(entry.getKey(), entry.getValue());
	    }
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		for (Map.Entry<String, String> entry : NAMES_MAP.entrySet()) {
			address = address.replace(entry.getKey(), entry.getValue());
	    }
		this.address = address;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || this.getClass() != o.getClass()) return false;
		final LocationEntity that = (LocationEntity) o;
		return Objects.equals(this.getAddress(), that.getAddress()) &&
				Objects.equals(this.getName(), that.getName()) && 
				Objects.equals(this.getLocation(), that.getLocation());
	}
	
	@Override
	  public int hashCode() {
	    return Objects.hash(this.getId(), this.address, this.getName());
	}
}
