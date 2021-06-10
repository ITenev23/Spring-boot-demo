package bg.paysafe.springboot.api.payload.user;

import bg.paysafe.springboot.api.entity.Gender;
import bg.paysafe.springboot.api.entity.Role;
import bg.paysafe.springboot.api.entity.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserProfileViewModel {

    private Long id;

    private String email;

    private String username;

    private String country;

    private String city;

    private String gender;

    private String status;

    private String message;

    private Integer age;

    private Date bornOn;

    private UserImagesViewModel profileImg;

    private List<String> roles;

    private List<String> genderSearch;

    private List<UserRequestInfoViewModel> friends;

    public static UserProfileViewModel ofUserProfile(User user) {
        var model = new UserProfileViewModel();

        model.setId(user.getId());
        model.setEmail(user.getEmail());
        model.setUsername(user.getUsername());
        model.setCountry(user.getCountry().getName());
        model.setCity(user.getCity().getName());
        model.setGender(user.getGender().getName());
        model.setStatus(user.getStatus().getName());
        model.setMessage(user.getMessage());
        model.setAge(user.getAge());
        model.setBornOn(user.getBornOn());
        model.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        model.setGenderSearch(user.getGendersSearch().stream().map(Gender::getName).collect(Collectors.toList()));
        model.setFriends(user.getFriends().stream().map(UserRequestInfoViewModel::fromUser).collect(Collectors.toList()));

        if (user.getProfileImg() != null)
            model.setProfileImg(UserImagesViewModel.ofUserImages(user.getProfileImg()));

        return model;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBornOn() {
        return this.bornOn;
    }

    public void setBornOn(Date bornOn) {
        this.bornOn = bornOn;
    }

    public UserImagesViewModel getProfileImg() {
        return this.profileImg;
    }

    public void setProfileImg(UserImagesViewModel profileImg) {
        this.profileImg = profileImg;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getGenderSearch() {
        return this.genderSearch;
    }

    public void setGenderSearch(List<String> genderSearch) {
        this.genderSearch = genderSearch;
    }

    public List<UserRequestInfoViewModel> getFriends() {
        return this.friends;
    }

    public void setFriends(List<UserRequestInfoViewModel> friends) {
        this.friends = friends;
    }
}
