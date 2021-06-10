package bg.paysafe.springboot.api.service.user;

import bg.paysafe.springboot.api.exception.friendship.FriendshipNotFound;
import bg.paysafe.springboot.api.exception.user.*;
import bg.paysafe.springboot.api.payload.SuccessfulRequestViewModel;
import bg.paysafe.springboot.api.payload.favorite.FavoriteDeleteRequestModel;
import bg.paysafe.springboot.api.payload.favorite.FavoritesInfoViewModel;
import bg.paysafe.springboot.api.payload.friend.FriendsAndFavouritesCountInfoViewModel;
import bg.paysafe.springboot.api.payload.friend.FriendsInfoViewModel;
import bg.paysafe.springboot.api.payload.friendship.FriendDeleteRequestModel;
import bg.paysafe.springboot.api.payload.friendship.FriendshipInfoViewModel;
import bg.paysafe.springboot.api.payload.friendship.FriendshipPageViewModel;
import bg.paysafe.springboot.api.payload.user.*;
import bg.paysafe.springboot.api.payload.visit.UserVisitInfoViewModel;
import bg.paysafe.springboot.api.payload.visit.UserVisitedRequestModel;
import bg.paysafe.springboot.api.payload.visit.VisitInfoViewModel;
import freemarker.template.TemplateException;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import java.io.IOException;

public interface UserService extends UserDetailsService {

    UserInfoViewModel register(UserRegisterRequestModel request) throws PasswordMismatchException, DuplicateEmailException, DuplicateUsernameException, UserAgeException;

    UsersSearchInfoViewModel findAll(Long statusId, String sort, Integer page, Integer size);

    UserProfileViewModel getProfile(Long id) throws UserNotFound;

    SuccessfulRequestViewModel verify(UserVerifyRequestModel model) throws UserNotFound, TemplateException, IOException, MessagingException;

    UserRequestInfoViewModel getProfileByUserId(Long userId, Long friendId) throws UserNotFound;

    UserInfoViewModel editUser(Long id, AdminUserEditRequestModel model) throws UserNotFound;

    UsersSearchInfoViewModel search(Long userId, String username, Long genderId, Long genderSearchId, Integer ageMin, Integer ageMax, Long cityId, Long countryId, String sort, Integer page, Integer size);

    UsernameValidateInfoModel validate(String email, String username);

    FriendsInfoViewModel findAllUserFriends(Long id, String sort, Integer page, Integer size);

    FavoritesInfoViewModel findAllUserFavorites(Long id, String sort, Integer page, Integer size);

    FriendshipInfoViewModel createFriendshipRequest(UserFriendRequestModel request, Long userId) throws UserNotFound, UserCannotFriendHimselfException;

    UserFavoritesViewModel createFavoriteUser(Long id, UserFavoriteRequestModel request) throws UserNotFound;

    FriendshipInfoViewModel editFriendshipStatus(UserFriendRequestModel request, Long userId) throws FriendshipNotFound, UserNotFound;

    UserInfoViewModel getAllFriendsByUserId(Long id) throws UserNotFound;

    FriendshipPageViewModel findAllFriendships(Long userId, Long type, String sort, Integer page, Integer size);

    SuccessfulRequestViewModel deleteFriend(Long id, FriendDeleteRequestModel request) throws UserNotFound;

    SuccessfulRequestViewModel deleteFavoriteUser(Long id, FavoriteDeleteRequestModel request) throws UserNotFound;

    FriendsAndFavouritesCountInfoViewModel countOfAllFriendsAndFavouritesByUserI(Long id) throws UserNotFound;

    UserVisitInfoViewModel getAllVisitsByUserId(Long id, Boolean isVisitor, String sort, Integer page, Integer size);

    VisitInfoViewModel createVisit(Long id, UserVisitedRequestModel request) throws UserNotFound, UserCannotVisitHimselfException;

    UserActivityViewModel getActivityByUserId(Long userId) throws UserNotFound;

    UserInfoViewModel changeUsername(Long id, UsernameRequestModel model) throws UserNotFound, DuplicateUsernameException;

    UserInfoViewModel changePassword(Long id, UserPasswordRequestModel model) throws PasswordMismatchException, UserNotFound;

    UsersSearchInfoViewModel getUsersByRegisterDate(Long id, Integer page, Integer size);

    UserInfoViewModel getPhoneNumber(Long id);

    UserInfoViewModel setPhoneNumber(Long id, UserPhoneNumberRequestModel model) throws UserNotFound;

    SuccessfulRequestViewModel delete(Long id);

}
