package bg.paysafe.springboot.api.service.country;

import bg.paysafe.springboot.api.payload.country.CountryInfoViewModel;
import bg.paysafe.springboot.api.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<CountryInfoViewModel> getAll() {
        return countryRepository.findAll().stream()
                .map(CountryInfoViewModel::ofCountry)
                .collect(Collectors.toList());
    }

}
