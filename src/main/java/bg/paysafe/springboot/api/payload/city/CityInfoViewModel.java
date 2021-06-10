package bg.paysafe.springboot.api.payload.city;

import bg.paysafe.springboot.api.entity.City;
import bg.paysafe.springboot.api.entity.Country;

public class CityInfoViewModel {

    private Long id;

    private String name;

    private Country country;

    private Double lat;

    private Double lng;

    private CityInfoViewModel(Long id, String name, Country country, Double lat, Double lng) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lng = lng;
    }

    public static CityInfoViewModel ofCity(City city) {
        return new CityInfoViewModel(
                city.getId(),
                city.getName(),
                city.getCountry(),
                city.getLat(),
                city.getLng()
        );
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Double getLat() {
        return this.lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return this.lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
