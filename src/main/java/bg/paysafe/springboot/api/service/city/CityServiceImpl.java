package bg.paysafe.springboot.api.service.city;

import bg.paysafe.springboot.api.payload.city.CityInfoViewModel;
import bg.paysafe.springboot.api.repository.CityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<CityInfoViewModel> getAllByCountryId(Long id) {
        return cityRepository.findAllByCountryId(id)
                .stream()
                .map(CityInfoViewModel::ofCity)
                .collect(Collectors.toList());
    }

}
