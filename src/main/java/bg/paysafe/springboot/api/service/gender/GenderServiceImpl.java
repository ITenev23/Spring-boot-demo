package bg.paysafe.springboot.api.service.gender;

import bg.paysafe.springboot.api.payload.gender.GenderInfoViewModel;
import bg.paysafe.springboot.api.repository.GenderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenderServiceImpl implements GenderService {

    private final GenderRepository genderRepository;

    public GenderServiceImpl(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    @Override
    public List<GenderInfoViewModel> getAll() {
        return this.genderRepository.findAllAndSortById()
                .stream()
                .map(GenderInfoViewModel::ofGender)
                .collect(Collectors.toList());
    }

}
