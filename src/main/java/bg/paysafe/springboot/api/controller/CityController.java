package bg.paysafe.springboot.api.controller;

import bg.paysafe.springboot.api.payload.city.CityInfoViewModel;
import bg.paysafe.springboot.api.service.city.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static bg.paysafe.springboot.api.constant.URLMappings.CITY_BASE;
import static bg.paysafe.springboot.api.constant.URLMappings.PathVar.ID;

@RestController
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(value = CITY_BASE + ID)
    public ResponseEntity<List<CityInfoViewModel>> getAllByCountryId(@PathVariable Long id) {
        return new ResponseEntity<>(this.cityService.getAllByCountryId(id), HttpStatus.OK);
    }

}
