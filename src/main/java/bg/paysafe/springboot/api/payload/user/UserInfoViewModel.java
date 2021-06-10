package bg.paysafe.springboot.api.payload.user;

import bg.paysafe.springboot.api.entity.Gender;
import bg.paysafe.springboot.api.entity.Role;
import bg.paysafe.springboot.api.entity.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoViewModel {

    private Long id;

    private String email;

    private String username;

    private String country;

    private String city;

    private String gender;

    private String status;

    private String phoneNumber;

    private String message;

    private Integer age;

    private Date bornOn;

    private List<UserImagesViewModel> images;

    private UserImagesViewModel profileImg;

    private List<String> roles;

    private List<String> genderSearch;

    private List<UserRequestInfoViewModel> friends;

    public static UserInfoViewModel fromUser(User u) {
        var model = new UserInfoViewModel();

        model.setId(u.getId());
        model.setEmail(u.getEmail());
        model.setUsername(u.getUsername());
        model.setCountry(u.getCountry().getName());
        model.setCity(u.getCity().getName());
        model.setGender(u.getGender().getName());
        model.setStatus(u.getStatus().getName());
        model.setMessage(u.getMessage());
        model.setPhoneNumber(u.getPhoneNumber());
        model.setAge(u.getAge());
        model.setBornOn(u.getBornOn());
        model.setImages(u.getUploads().stream().map(UserImagesViewModel::ofUserImages).collect(Collectors.toList()));
        model.setRoles(u.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        model.setGenderSearch(u.getGendersSearch().stream().map(Gender::getName).collect(Collectors.toList()));
        model.setFriends(u.getFriends().stream().map(UserRequestInfoViewModel::fromUser).collect(Collectors.toList()));

        if (u.getProfileImg() != null)
            model.setProfileImg(UserImagesViewModel.ofUserImages(u.getProfileImg()));

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public List<UserImagesViewModel> getImages() {
        return this.images;
    }

    public void setImages(List<UserImagesViewModel> images) {
        this.images = images;
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
