package bg.paysafe.springboot.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity{

    private String name;

    private Double lat;

    private Double lng;

    private String code;

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }

    public Double getLng() { return lng; }
    public void setLng(Double lng) { this.lng = lng; }

    @Column(unique = true)
    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }
}
