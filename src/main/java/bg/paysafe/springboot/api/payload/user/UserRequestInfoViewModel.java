package bg.paysafe.springboot.api.payload.user;

import bg.paysafe.springboot.api.entity.Gender;
import bg.paysafe.springboot.api.entity.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserRequestInfoViewModel {

    private Long id;

    private String email;

    private String username;

    private String country;

    private String city;

    private String gender;

    private Integer age;

    private Date bornOn;

    private String message;

    private String phoneNumber;

    private Date registeredOn;

    private String status;

    private Boolean isSender;

    private Boolean isRecipient;

    private Boolean isFriend;

    private Boolean isFavorite;

    private UserImagesViewModel profileImg;

    private List<UserImagesViewModel> uploads;

    private List<String> genderSearch;

    private Long visitCount;

    public static UserRequestInfoViewModel fromUser(User user) {
        return setModel(user);
    }

    public static UserRequestInfoViewModel ofUserFriends(User user, Boolean isSender, Boolean isRecipient, Boolean isFriend, Boolean isFavorite) {
        var model = setModel(user);
        model.setSender(isSender);
        model.setRecipient(isRecipient);
        model.setFriend(isFriend);
        model.setFavorite(isFavorite);

        return model;
    }

    public static UserRequestInfoViewModel ofUserVisits(User user, Long visitCount) {
        var model = setModel(user);
        model.setVisitCount(visitCount);

        return model;
    }

    private static UserRequestInfoViewModel setModel(User u) {
        var model = new UserRequestInfoViewModel();
        model.setId(u.getId());
        model.setEmail(u.getEmail());
        model.setUsername(u.getUsername());
        model.setCountry(u.getCountry().getName());
        model.setCity(u.getCity().getName());
        model.setGender(u.getGender().getName());
        model.setAge(u.getAge());
        model.setBornOn(u.getBornOn());
        model.setMessage(u.getMessage());
        model.setPhoneNumber(u.getPhoneNumber());
        model.setRegisteredOn(u.getRegisteredOn());
        model.setStatus(u.getStatus().getName());
        model.setGenderSearch(u.getGendersSearch().stream().map(Gender::getName).collect(Collectors.toList()));
        model.setUploads(u.getUploads().stream().map(UserImagesViewModel::ofUserImages).collect(Collectors.toList()));

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

    public List<String> getGenderSearch() {
        return this.genderSearch;
    }

    public void setGenderSearch(List<String> genderSearch) {
        this.genderSearch = genderSearch;
    }

    public Boolean isSender() {
        return this.isSender;
    }

    public void setSender(Boolean sender) {
        isSender = sender;
    }

    public Boolean isRecipient() {
        return this.isRecipient;
    }

    public void setRecipient(Boolean recipient) {
        isRecipient = recipient;
    }

    public Boolean isFriend() {
        return this.isFriend;
    }

    public void setFriend(Boolean friend) {
        isFriend = friend;
    }

    public Boolean isFavorite() {
        return this.isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Long getVisitCount() {
        return this.visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    public List<UserImagesViewModel> getUploads() {
        return uploads;
    }

    public void setUploads(List<UserImagesViewModel> uploads) {
        this.uploads = uploads;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
