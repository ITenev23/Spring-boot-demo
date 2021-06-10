package bg.paysafe.springboot.api.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    private String email;

    private String username;

    private String password;

    private Integer age;

    private Date bornOn;

    private Country country;

    private City city;

    private Gender gender;

    private UserStatus status;

    private String message;

    private String phoneNumber;

    private Boolean isDeleted;

    private Set<Conversation> conversations;

    private Set<Role> roles;

    private Set<Gender> gendersSearch;

    private Set<Upload> uploads;

    private Upload profileImg;

    private Set<User> friends;

    private Set<User> favorites;

    private Date registeredOn;

    public User() {
        this.conversations = new HashSet<>();
        this.roles = new HashSet<>();
        this.gendersSearch = new HashSet<>();
        this.uploads = new HashSet<>();
        this.friends = new HashSet<>();
        this.favorites = new HashSet<>();
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    public Set<Role> getRoles() {
        return roles;
    }

    @OneToMany(mappedBy = "user")
    public Set<Upload> getUploads() {
        return uploads;
    }

    public void setUploads(Set<Upload> uploads) {
        this.uploads = uploads;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    @Column(unique = true)
    public String getUsername() {
        return this.username;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    public Date getBornOn() {
        return bornOn;
    }

    public void setBornOn(Date bornOn) {
        this.bornOn = bornOn;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @ManyToOne
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Gender> getGendersSearch() {
        return gendersSearch;
    }

    public void setGendersSearch(Set<Gender> gendersSearch) {
        this.gendersSearch = gendersSearch;
    }

    @ManyToOne
    public UserStatus getStatus() {
        return this.status;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Conversation> getConversations() {
        return this.conversations;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.addUser(this);
    }

    public String getMessage() {
        return this.message;
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

    public Boolean isDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @OneToOne
    public Upload getProfileImg() {
        return this.profileImg;
    }

    public void setProfileImg(Upload profileImg) {
        this.profileImg = profileImg;
    }

    @ManyToMany
    public Set<User> getFriends() {
        return this.friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @ManyToMany
    public Set<User> getFavorites() {
        return this.favorites;
    }

    public void setFavorites(Set<User> favorites) {
        this.favorites = favorites;
    }

    public Date getRegisteredOn() {
        return this.registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    @Transient
    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    @Transient
    public void removeFriend(User friend) {
        this.friends.remove(friend);
    }

    @Transient
    public void addFavorites(User friend) {
        this.favorites.add(friend);
    }

    @Transient
    public void removeFavorites(User friend) {
        this.favorites.remove(friend);
    }

    @Transient
    public Boolean findFavorite(User favorite) {
        return this.favorites.contains(favorite);
    }

    @Transient
    public Boolean addConversation(Conversation conversation) {
        return this.conversations.add(conversation);
    }

}
