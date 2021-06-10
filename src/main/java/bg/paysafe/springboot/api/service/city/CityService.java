package bg.paysafe.springboot.api.service.city;

import bg.paysafe.springboot.api.payload.city.CityInfoViewModel;

import java.util.List;

public interface CityService {

    List<CityInfoViewModel> getAllByCountryId(Long id);

}
