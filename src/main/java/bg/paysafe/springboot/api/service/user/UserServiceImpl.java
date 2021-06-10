package bg.paysafe.springboot.api.service.user;

import bg.paysafe.springboot.api.constant.Constants;
import bg.paysafe.springboot.api.entity.*;
import bg.paysafe.springboot.api.exception.user.*;
import bg.paysafe.springboot.api.payload.SuccessfulRequestViewModel;
import bg.paysafe.springboot.api.payload.favorite.FavoriteDeleteRequestModel;
import bg.paysafe.springboot.api.payload.favorite.FavoritesInfoViewModel;
import bg.paysafe.springboot.api.payload.friend.FriendsAndFavouritesCountInfoViewModel;
import bg.paysafe.springboot.api.payload.friend.FriendsInfoViewModel;
import bg.paysafe.springboot.api.payload.friendship.FriendDeleteRequestModel;
import bg.paysafe.springboot.api.payload.friendship.FriendshipInfoViewModel;
import bg.paysafe.springboot.api.payload.friendship.FriendshipPageViewModel;
import bg.paysafe.springboot.api.payload.friendship.FriendshipUsersInfoViewModel;
import bg.paysafe.springboot.api.payload.user.*;
import bg.paysafe.springboot.api.payload.visit.UserVisitInfoViewModel;
import bg.paysafe.springboot.api.payload.visit.UserVisitedRequestModel;
import bg.paysafe.springboot.api.payload.visit.VisitInfoViewModel;
import bg.paysafe.springboot.api.repository.*;
import bg.paysafe.springboot.api.service.chat.ChatService;
import bg.paysafe.springboot.api.service.common.io.DefaultPictureService;
import bg.paysafe.springboot.api.service.common.io.FileService;
import bg.paysafe.springboot.api.service.common.io.PictureService;
import bg.paysafe.springboot.api.service.common.mail.MailSender;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static bg.paysafe.springboot.api.constant.Constants.*;
import static bg.paysafe.springboot.api.constant.Constants.Number.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CountryRepository countryRepository;

    private final ConversationRepository conversationRepository;

    private final MessageRepository messageRepository;

    private final UserCategoryItemRepository userCategoryItemRepository;

    private final CityRepository cityRepository;

    private final GenderRepository genderRepository;

    private final PasswordEncoder passwordEncoder;

    private final String staticUrl;

    private final String staticFileDestination;

    private final FileService fileService;

    private final RoleRepository roleRepository;

    private final UploadRepository uploadRepository;

    private final PictureService pictureService;

    private final UserStatusRepository userStatusRepository;

    private final FriendshipStatusRepository friendshipStatusRepository;

    private final UploadTypeRepository uploadTypeRepository;

    private final FriendshipRepository friendshipRepository;

    private final UserVisitRepository visitRepository;

    private final ChatService chatService;

    private final UserActivityRepository userActivityRepository;

    private final MailSender mailSender;

    public UserServiceImpl(
            UserRepository userRepository,
            CountryRepository countryRepository,
            ConversationRepository conversationRepository, MessageRepository messageRepository, UserCategoryItemRepository userCategoryItemRepository, CityRepository cityRepository,
            GenderRepository genderRepository,
            PasswordEncoder passwordEncoder,
            @Value("${static.url}") String staticUrl,
            @Value("${static.fileDestination}") String staticFileDestination,
            FileService fileService,
            RoleRepository roleRepository,
            UploadRepository uploadRepository,
            PictureService pictureService,
            UserStatusRepository userStatusRepository,
            FriendshipStatusRepository friendshipStatusRepository,
            UploadTypeRepository uploadTypeRepository,
            FriendshipRepository friendshipRepository,
            UserVisitRepository visitRepository, ChatService chatService, UserActivityRepository userActivityRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.userCategoryItemRepository = userCategoryItemRepository;
        this.cityRepository = cityRepository;
        this.genderRepository = genderRepository;
        this.passwordEncoder = passwordEncoder;
        this.staticUrl = staticUrl;
        this.staticFileDestination = staticFileDestination;
        this.fileService = fileService;
        this.roleRepository = roleRepository;
        this.uploadRepository = uploadRepository;
        this.pictureService = pictureService;
        this.userStatusRepository = userStatusRepository;
        this.friendshipStatusRepository = friendshipStatusRepository;
        this.uploadTypeRepository = uploadTypeRepository;
        this.friendshipRepository = friendshipRepository;
        this.visitRepository = visitRepository;
        this.chatService = chatService;
        this.userActivityRepository = userActivityRepository;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findFirstByEmailOrUsername(s, s)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public UserInfoViewModel register(UserRegisterRequestModel request) throws PasswordMismatchException, DuplicateEmailException, DuplicateUsernameException, UserAgeException {
        if (!request.getPassword().equals(request.getConfirm())) {
            throw new PasswordMismatchException();
        }

        if (this.userRepository.findFirstByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException();
        }

        if (this.userRepository.findFirstByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateUsernameException();
        }

        Date today = java.sql.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        final int age = this.getAge(request.getBornOn(), today);

        if (age < 18 || age > 80) throw new UserAgeException();

        var u = new User();
        u.setDeleted(false);
        u.setAge(age);
        u.setEmail(request.getEmail());
        u.setBornOn(request.getBornOn());
        u.setUsername(request.getUsername());
        u.setPassword(this.passwordEncoder.encode(request.getPassword()));
        u.setCountry(this.countryRepository.findById(request.getCountryId()).get());
        u.setCity(this.cityRepository.findById(request.getCityId()).get());

        final var gender = this.genderRepository.findById(request.getGenderId()).get();
        u.setGender(gender);

        u.setProfileImg(gender.getId().equals(ONE) ?
                this.uploadRepository.findById(TWO).get() :
                this.uploadRepository.findById(ONE).get()
        );

        u.setStatus(this.userStatusRepository.findById(TWO).get());
        u.addRole(this.roleRepository.findById(TWO).get());
        u.setGendersSearch(this.genderRepository.findAllById(request.getGenderSearch()));
        u.setRegisteredOn(today);

        final var user = this.userRepository.saveAndFlush(u);

        this.chatService.createBurnMeSupportChat(user);

        return UserInfoViewModel.fromUser(user);
    }

    @Override
    public UsersSearchInfoViewModel search(Long userId, String username, Long genderId, Long genderSearchId, Integer ageMin, Integer ageMax, Long cityId, Long countryId, String sort, Integer page, Integer size) {
        final var pageable = PageRequest.of(page, size, Sort.by(
                sort.equals(DEFAULT_SORT) ?
                        Sort.Direction.ASC :
                        Sort.Direction.DESC,
                Constants.DEFAULT_SORT_PROPERTIES_AGE
                )
        );
        this.userRepository.findAllBySearchCritеria(
                ONE,
                userId,
                username,
                genderId,
                genderSearchId,
                ageMin,
                ageMax,
                cityId,
                countryId,
                pageable
        ).map(UsersSearchInfoViewModel::ofUsers);

        return UsersSearchInfoViewModel.ofUsersSearch(
                this.userRepository.findAllBySearchCritеria(
                        ONE,
                        userId,
                        username,
                        genderId,
                        genderSearchId,
                        ageMin,
                        ageMax,
                        cityId,
                        countryId,
                        pageable
                )
        );
    }

    @Override
    public UsersSearchInfoViewModel findAll(Long statusId, String sort, Integer page, Integer size) {
        final var pageable = PageRequest.of(page, size, Sort.by(sort));

        if (statusId == null)
            return UsersSearchInfoViewModel.ofUsersSearch(this.userRepository.findAll(pageable));

        return UsersSearchInfoViewModel.ofUsersSearch(this.userRepository.findAllByStatusId(statusId, pageable));
    }

    @Override
    public UserProfileViewModel getProfile(Long id) throws UserNotFound {
        return UserProfileViewModel.ofUserProfile(this.userRepository.findById(id).orElseThrow(UserNotFound::new));
    }

    @Override
    public FriendsInfoViewModel findAllUserFriends(Long id, String sort, Integer page, Integer size) {
        if (this.userRepository.findById(id).get().getFriends().isEmpty())
            return new FriendsInfoViewModel();

        return FriendsInfoViewModel.ofFriends(this.userRepository.findAllFriendsByUserId(id, PageRequest.of(page, size, Sort.by(sort))));
    }

    @Override
    public FavoritesInfoViewModel findAllUserFavorites(Long id, String sort, Integer page, Integer size) {
        if (this.userRepository.findById(id).get().getFavorites().isEmpty())
            return new FavoritesInfoViewModel();

        return FavoritesInfoViewModel.fromFavorite(this.userRepository.findAllFavoritesByUserId(id, PageRequest.of(page, size, Sort.by(sort))));
    }

    @Override
    public FriendshipInfoViewModel createFriendshipRequest(UserFriendRequestModel request, Long userId) throws UserNotFound, UserCannotFriendHimselfException {
        if (request.getFriendId().equals(userId))
            throw new UserCannotFriendHimselfException();

        this.userRepository.findById(request.getFriendId()).orElseThrow(UserNotFound::new);

        var friendship = this.friendshipRepository.findByIds(userId, request.getFriendId());

        if (friendship == null) {
            friendship = new Friendship();
            friendship.setSender(userId);
            friendship.setRecipient(request.getFriendId());
        }

        friendship.setStatus(this.friendshipStatusRepository.findById(request.getStatusId()).get());

        return FriendshipInfoViewModel.ofFriendship(
                this.friendshipRepository.saveAndFlush(friendship)
        );
    }

    @Override
    public UserFavoritesViewModel createFavoriteUser(Long id, UserFavoriteRequestModel request) throws UserNotFound {
        final var user = this.userRepository.findById(id).get();

        user.addFavorites(this.userRepository.findById(request.getUserId()).orElseThrow(UserNotFound::new));

        return new UserFavoritesViewModel(id, this.userRepository.saveAndFlush(user).getId());
    }

    @Override
    public FriendshipInfoViewModel editFriendshipStatus(UserFriendRequestModel request, Long userId) throws UserNotFound {
        var friendship = this.friendshipRepository.findByIds(userId, request.getFriendId());

        friendship.setStatus(this.friendshipStatusRepository.findById(request.getStatusId()).get());

        if (friendship.getStatus().getName().equals(STATUS_TYPE_ACCEPTED)) {
            var user = this.userRepository.findById(userId).get();
            final var friend = this.userRepository.findById(request.getFriendId())
                    .orElseThrow(UserNotFound::new);
            user.addFriend(friend);
            friend.addFriend(user);

            this.userRepository.saveAndFlush(user);
        }

        return FriendshipInfoViewModel.ofFriendship(this.friendshipRepository.saveAndFlush(friendship));
    }

    @Override
    public UserInfoViewModel getAllFriendsByUserId(Long id) throws UserNotFound {
        return UserInfoViewModel.fromUser(this.userRepository.findById(id).orElseThrow(UserNotFound::new));
    }

    @Override
    public FriendshipPageViewModel findAllFriendships(Long userId, Long type, String sort, Integer page, Integer size) {
        final var pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<Friendship> friendshipPage;
        if (type == 1)
            friendshipPage = this.friendshipRepository.findAllByRecipientAndStatusId(userId, ONE, pageable);
        else if (type == 2)
            friendshipPage = this.friendshipRepository.findAllBySenderAndStatusId(userId, ONE, pageable);
        else
            friendshipPage = this.friendshipRepository.findAllByRecipientAndStatusId(userId, FOUR, pageable);

        final var friendships = friendshipPage.getContent();

        var ids = new HashSet<Long>();
        friendships.forEach(friendship -> {
            ids.add(friendship.getRecipient());
            ids.add(friendship.getSender());
        });

        var users = new HashMap<Long, User>();
        this.userRepository.findAllById(ids).forEach(u -> users.putIfAbsent(u.getId(), u));

        return new FriendshipPageViewModel(friendshipPage.getNumber(), friendshipPage.getSize(), friendshipPage.getTotalPages(), friendshipPage.getTotalElements(),
                friendships.stream().map(x -> FriendshipUsersInfoViewModel.ofFriendship(
                        x.getId(),
                        x.getStatus().getName(),
                        users.get(x.getSender()),
                        users.get(x.getRecipient()))
                ).collect(Collectors.toList()));
    }

    @Override
    public SuccessfulRequestViewModel deleteFriend(Long id, FriendDeleteRequestModel request) throws UserNotFound {
        var friend = this.userRepository.findById(request.getFriendId()).orElseThrow(UserNotFound::new);
        var user = this.userRepository.findById(id).get();

        final var friendship = this.friendshipRepository.findByIds(id, friend.getId());
        this.friendshipRepository.deleteById(friendship.getId());

        friend.removeFriend(user);
        user.removeFriend(friend);
        this.userRepository.saveAndFlush(user);

        return new SuccessfulRequestViewModel(true);
    }

    private void deleteAllFriends(User u, List<Long> ids) {
        for (Long friendId : ids) {
            var friend = this.userRepository.findById(friendId).get();

            final var friendship = this.friendshipRepository.findByIds(u.getId(), friend.getId());
            this.friendshipRepository.deleteById(friendship.getId());

            friend.removeFriend(u);
            u.removeFriend(friend);
            this.userRepository.saveAndFlush(friend);
        }
    }

    private void deleteFavorite(User favorite, List<Long> users) {
        for (Long id : users) {
            var user = this.userRepository.findById(id).get();
            user.removeFavorites(favorite);
            favorite.removeFavorites(user);
            this.userRepository.saveAndFlush(user);
        }
    }

    @Override
    public SuccessfulRequestViewModel deleteFavoriteUser(Long id, FavoriteDeleteRequestModel request) throws UserNotFound {
        final var favorite = this.userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFound::new);

        var user = this.userRepository.findById(id).get();
        user.removeFavorites(favorite);

        this.userRepository.saveAndFlush(user);

        return new SuccessfulRequestViewModel(true);
    }

    @Override
    public FriendsAndFavouritesCountInfoViewModel countOfAllFriendsAndFavouritesByUserI(Long id) throws UserNotFound {
        final var user = this.userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        return new FriendsAndFavouritesCountInfoViewModel(
                user.getFriends().size(),
                user.getFavorites().size()
        );
    }

    @Override
    public UserVisitInfoViewModel getAllVisitsByUserId(Long id, Boolean isVisitor, String sort, Integer page, Integer size) {
        final var pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, DEFAULT_SORT_PROPERTIES_VISITED_ON)
        );

        return UserVisitInfoViewModel.ofVisits(
                isVisitor ?
                        this.visitRepository.findAllByVisitorId(id, pageable) :
                        this.visitRepository.findAllByVisitedId(id, pageable),
                isVisitor);
    }

    @Override
    public VisitInfoViewModel createVisit(Long id, UserVisitedRequestModel request) throws UserNotFound, UserCannotVisitHimselfException {
        if (id.equals(request.getVisitedUserId())) throw new UserCannotVisitHimselfException();

        var userVisits = this.visitRepository.findByUserIdAndVisitedUserId(id, request.getVisitedUserId());

        if (userVisits == null) {
            userVisits = new UserVisit();

            userVisits.setUser(this.userRepository.findById(id).orElseThrow(UserNotFound::new));
            userVisits.setVisitedUser(this.userRepository.findById(request.getVisitedUserId()).orElseThrow(UserNotFound::new));
            userVisits.setVisitedOn(java.sql.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        }

        userVisits.incrementVisitCount();

        return new VisitInfoViewModel(this.visitRepository.saveAndFlush(userVisits));
    }

    @Override
    public UserActivityViewModel getActivityByUserId(Long userId) throws UserNotFound {
        final var activity = this.userActivityRepository.findByUserId(userId).orElseThrow(UserNotFound::new);

        return new UserActivityViewModel(activity.getLastSeen());
    }

    @Override
    public UserInfoViewModel changeUsername(Long id, UsernameRequestModel model) throws UserNotFound, DuplicateUsernameException {
        if (this.userRepository.findFirstByUsername(model.getUsername()).isPresent()) {
            throw new DuplicateUsernameException();
        }

        final var user = this.userRepository.findById(id).orElseThrow(UserNotFound::new);

        user.setUsername(model.getUsername());

        return UserInfoViewModel.fromUser(this.userRepository.saveAndFlush(user));
    }

    @Override
    public UserInfoViewModel changePassword(Long id, UserPasswordRequestModel request) throws PasswordMismatchException, UserNotFound {
        if (!request.getPassword().equals(request.getConfirm())) {
            throw new PasswordMismatchException();
        }

        var user = this.userRepository.findById(id).orElseThrow(UserNotFound::new);

        user.setPassword(this.passwordEncoder.encode(request.getPassword()));

        return UserInfoViewModel.fromUser(this.userRepository.saveAndFlush(user));
    }

    @Override
    public UsersSearchInfoViewModel getUsersByRegisterDate(Long id, Integer page, Integer size) {
        final var pageable = PageRequest.of(page, size,
                Sort.by(
                        Sort.Direction.DESC,
                        Constants.DEFAULT_SORT_PROPERTIES_REGISTERED_DATE
                )
        );

        return UsersSearchInfoViewModel.ofUsersSearch(this.userRepository.findAllByRegisteredDate(ONE, id, pageable));
    }

    @Override
    public UserInfoViewModel getPhoneNumber(Long id) {
        return UserInfoViewModel.fromUser(this.userRepository.findById(id).get());
    }

    @Override
    public UserInfoViewModel setPhoneNumber(Long id, UserPhoneNumberRequestModel model) throws UserNotFound {
        User user = this.userRepository.findById(id).orElseThrow(UserNotFound::new);
        user.setPhoneNumber(model.getPhoneNumber());

        return UserInfoViewModel.fromUser(this.userRepository.saveAndFlush(user));
    }

    private void deleteRole(User user, Role role) {
        role.getUsers().remove(user);
        user.getRoles().remove(role);
        this.roleRepository.saveAndFlush(role);
    }

    private void deleteAllConversations(List<Long> ids) {
        for (Long id : ids) {
            var conversation = this.conversationRepository.findById(id).get();
            conversation.getMessages().forEach(m -> this.messageRepository.deleteById(m.getId()));

            for (User u : conversation.getUsers()) {
                u.getConversations().remove(conversation);
            }
            conversation.setLastMessage(null);
            conversation.setUsers(null);
            conversation.setMessages(null);

            this.conversationRepository.delete(conversation);
        }
    }

    private void deleteUploads(User u , Set<Upload> uploads) {
        DefaultPictureService pictureService = new DefaultPictureService(this.staticFileDestination);
        pictureService.deleteAll(uploads);
        pictureService.deleteImg(u.getProfileImg().getUrl(), u.getProfileImg().getThumbnailUrl());

        u.setProfileImg(null);
        u.setUploads(null);
        u.setGender(null);
        u.setGendersSearch(null);

        this.uploadRepository.deleteAll(uploads);
    }

    @Override
    public SuccessfulRequestViewModel delete(Long id) {
        var user = this.userRepository.findById(id).get();
        user.getRoles().forEach(r -> this.deleteRole(user, r));
        user.setRoles(null);

        this.deleteAllConversations(user.getConversations().stream().map(Conversation::getId).collect(Collectors.toList()));

        var activity = this.userActivityRepository.findByUserId(id).get();
        this.userActivityRepository.delete(activity);

        List<UserVisit> visits = this.visitRepository.findByUserId(id);
        visits.forEach(this.visitRepository::delete);

        this.deleteAllFriends(user, user.getFriends().stream().map(User::getId).collect(Collectors.toList()));

        this.deleteFavorite(
                user,
                this.userRepository.findAll().stream()
                        .map(User::getId)
                        .collect(Collectors.toList())
        );

        this.deleteUploads(user, user.getUploads());

        List<UserCategoryItem> categoryItems = this.userCategoryItemRepository.findAllByUserId(id);
        categoryItems.forEach(this.userCategoryItemRepository::delete);

        this.userRepository.deleteById(id);
        return new SuccessfulRequestViewModel(true);
    }

    @Override
    public SuccessfulRequestViewModel verify(UserVerifyRequestModel model) throws UserNotFound, TemplateException, IOException, MessagingException {
        var user = this.userRepository.findById(model.getUserId())
                .orElseThrow(UserNotFound::new);

        if (model.getMessage() == null) {
            final var userImgs = this.uploadRepository.findAllByUserAndTypeId(
                    model.getUserId(),
                    this.uploadTypeRepository.findByName(Constants.UPLOAD_TYPE_VERIFICATION)
                            .getId()
            );
            user.setStatus(this.userStatusRepository.findByName(Constants.STATUS_TYPE_VERIFIED));
            this.pictureService.deleteAll(userImgs);
            this.uploadRepository.deleteAll(userImgs);
        } else {
            user.setMessage(model.getMessage());
        }

        this.mailSender.verifyUser(user.getEmail());

        return new SuccessfulRequestViewModel(true);
    }

    @Override
    public UserRequestInfoViewModel getProfileByUserId(Long userId, Long friendId) throws UserNotFound {
        if (userId.equals(friendId)) throw new UserNotFound();

        final var friend = this.userRepository.findById(friendId).orElseThrow(UserNotFound::new);
        final var friendship = this.friendshipRepository.findByIds(userId, friendId);
        final var isFriend = this.userRepository.findById(userId).get().findFavorite(friend);

        if (friendship != null && (friendship.getStatus().getName().equals(FRIENDSHIP_STATUS_TYPE_PENDING) ||
                friendship.getStatus().getName().equals(FRIENDSHIP_STATUS_TYPE_ACCEPTED))) {

            if (friendship.getSender().equals(userId))
                return UserRequestInfoViewModel.ofUserFriends(
                        friend,
                        false,
                        true,
                        friendship.getStatus().getName().equals(STATUS_TYPE_ACCEPTED),
                        isFriend
                );

            return UserRequestInfoViewModel.ofUserFriends(
                    friend,
                    true,
                    false,
                    friendship.getStatus().getName().equals(STATUS_TYPE_ACCEPTED),
                    isFriend
            );
        }

        return UserRequestInfoViewModel.ofUserFriends(
                friend,
                false,
                false,
                false,
                isFriend
        );
    }

    @Override
    public UserInfoViewModel editUser(Long id, AdminUserEditRequestModel model) throws UserNotFound {
        var user = this.userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        user.setUsername(model.getUsername());
        user.setDeleted(model.isDeleted());

        return UserInfoViewModel.fromUser(
                this.userRepository.saveAndFlush(user));
    }

    @Override
    public UsernameValidateInfoModel validate(String email, String username) {
        return new UsernameValidateInfoModel(
                this.userRepository.findFirstByEmail(email).isEmpty(),
                this.userRepository.findFirstByUsername(username).isEmpty()
        );
    }

    private int getAge(Date b, Date t) {
        Calendar birthdate = getCalendar(b);
        Calendar today = getCalendar(t);
        int age = today.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR);
        if (birthdate.get(Calendar.MONTH) > today.get(Calendar.MONTH) ||
                (birthdate.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                        birthdate.get(Calendar.DATE) > today.get(Calendar.DATE))
        ) {
            age--;
        }

        return age;
    }

    private Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);

        return cal;
    }

}
