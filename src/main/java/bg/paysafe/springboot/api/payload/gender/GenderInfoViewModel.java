package bg.paysafe.springboot.api.payload.gender;

import bg.paysafe.springboot.api.entity.Gender;

public class GenderInfoViewModel {

    private Long id;

    private String name;

    private GenderInfoViewModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GenderInfoViewModel ofGender(Gender gender) {
        return new GenderInfoViewModel(gender.getId(),gender.getName());
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
}
