package bg.paysafe.springboot.api.payload.country;

import bg.paysafe.springboot.api.entity.Country;

public class CountryInfoViewModel {

    private Long id;

    private String name;

    private Double lat;

    private Double lng;

    private String code;


    private CountryInfoViewModel(Long id, String name, Double lat, Double lng, String code) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.code = code;
    }


    public static CountryInfoViewModel ofCountry(Country country) {
        return new CountryInfoViewModel(
                country.getId(),
                country.getName(),
                country.getLat(),
                country.getLng(),
                country.getCode()
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
