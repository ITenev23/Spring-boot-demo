package bg.paysafe.springboot.api.service.country;

import bg.paysafe.springboot.api.payload.country.CountryInfoViewModel;

import java.util.List;

public interface CountryService {

    List<CountryInfoViewModel> getAll();

}
