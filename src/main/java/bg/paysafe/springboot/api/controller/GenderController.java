package bg.paysafe.springboot.api.controller;

import bg.paysafe.springboot.api.payload.gender.GenderInfoViewModel;
import bg.paysafe.springboot.api.service.gender.GenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static bg.paysafe.springboot.api.constant.URLMappings.GENDERS_BASE;

@RestController
public class GenderController {

    private final GenderService genderService;

    public GenderController(GenderService genderService) {
        this.genderService = genderService;
    }

    @GetMapping(value = GENDERS_BASE)
    public ResponseEntity<List<GenderInfoViewModel>> getAll() {
        return new ResponseEntity<>(this.genderService.getAll(), HttpStatus.OK);
    }

}
