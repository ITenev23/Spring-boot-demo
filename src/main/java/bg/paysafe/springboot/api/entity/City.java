package bg.paysafe.springboot.api.entity;

import javax.persistence.*;

@Entity
@Table(name = "cities")
public class City extends BaseEntity{

    private String name;

    private Country country;

    private Double lat;

    private Double lng;

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

    @ManyToOne
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
