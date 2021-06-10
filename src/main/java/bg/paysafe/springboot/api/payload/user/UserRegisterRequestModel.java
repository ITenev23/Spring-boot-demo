package bg.paysafe.springboot.api.payload.user;

import bg.paysafe.springboot.api.constant.ConfigurationConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

public class UserRegisterRequestModel {

    @Email(message = ConfigurationConstants.User.ERROR_EMAIL_VALIDATION)
    private String email;

    @Pattern(regexp = ".*", message = ConfigurationConstants.User.ERROR_PASSWORD_VALIDATION)
    private String password;

    private String username;

    private String confirm;

    private Date bornOn;

    private Long countryId;

    private Long cityId;

    private Long genderId;

    private List<Long> genderSearch;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public Long getCountryId() {
        return this.countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getCityId() {
        return this.cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getGenderId() {
        return this.genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public List<Long> getGenderSearch() {
        return genderSearch;
    }

    public void setGenderSearch(List<Long> genderSearch) {
        this.genderSearch = genderSearch;
    }

    public Date getBornOn() {
        return this.bornOn;
    }

    public void setBornOn(Date bornOn) {
        this.bornOn = bornOn;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
