package bg.paysafe.springboot.api.controller;

import bg.paysafe.springboot.api.payload.country.CountryInfoViewModel;
import bg.paysafe.springboot.api.service.country.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static bg.paysafe.springboot.api.constant.URLMappings.COUNTRY_BASE;

@RestController
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(value = COUNTRY_BASE)
    public ResponseEntity<List<CountryInfoViewModel>> getAll() {
        return new ResponseEntity<>(this.countryService.getAll(), HttpStatus.OK);
    }

}
