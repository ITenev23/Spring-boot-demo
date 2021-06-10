package bg.paysafe.springboot.api.service.gender;

import bg.paysafe.springboot.api.payload.gender.GenderInfoViewModel;

import java.util.List;

public interface GenderService {

    List<GenderInfoViewModel> getAll();

}
