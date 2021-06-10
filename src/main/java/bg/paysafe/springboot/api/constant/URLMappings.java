package bg.paysafe.springboot.api.constant;

import static bg.paysafe.springboot.api.constant.URLMappings.Common.COUNT;
import static bg.paysafe.springboot.api.constant.URLMappings.Common.DELETE;
import static bg.paysafe.springboot.api.constant.URLMappings.PathVar.*;
import static bg.paysafe.springboot.api.constant.URLMappings.User.*;

public final class URLMappings {

    public static final String ALL_MAPPINGS = "/**";
    public static final String USER_BASE = "/users";
    public static final String FRIENDS_BASE = "/friends";
    public static final String CATEGORY_BASE = "/categories";
    public static final String PROFILE_BASE = "/profile";
    public static final String ME_BASE = "/me";
    public static final String SEARCH_BASE = "/search";
    public static final String UPLOAD_BASE = "/uploads";
    public static final String TYPE = "/type";
    public static final String GENDERS_BASE = "/genders";
    public static final String FAVORITE_BASE = "/favorites";
    public static final String CONVERSATION_BASE = "/conversations";
    public static final String CITY_BASE = "/cities";
    public static final String COUNTRY_BASE = "/countries";
    public static final String FRIEND_BASE = "/friends";
    public static final String FRIENDSHIP_BASE = "/friendships";
    public static final String TEMPLATE_BASE = "/templates";
    public static final String MAIL_BASE = "/contacts";
    public static final String MESSAGE_BASE = "/messages";
    public static final String VISIT_BASE = "/visits";
    public static final String ACTIVITY_BASE = "/activities";
    public static final String PHONE = "/phone";

    public static final String USER_BY_ID = USER_BASE + ID;
    public static final String USER_ACTIVITY = ID + ACTIVITY_BASE;
    public static final String VERIFY_USER = USER_BASE + VERIFY;
    public static final String ALL_USER_FRIENDS_BY_ID = USER_BASE + ID + FRIENDS_BASE;
    public static final String SET_PROFILE_IMG = USER_BASE + ME_BASE + PICTURE;
    public static final String DELETE_MY_UPLOAD = USER_BASE + ME_BASE + PICTURE + DELETE;
    public static final String UPLOAD_FILE = USER_BASE + UPLOAD_BASE;
    public static final String CHANGE_UPLOAD_TYPE = USER_BASE + UPLOAD_BASE + TYPE;
    public static final String USER_PHONE_NUMBER = USER_BASE + PHONE;

    public static final String UPLOAD_CHAT_FILE = MESSAGE_BASE + UPLOAD_BASE;
    public static final String MESSAGE_DELETE = MESSAGE_BASE + DELETE;

    public static final String MY_FRIENDS = FRIEND_BASE + ME_BASE;
    public static final String FRIENDS_AND_FAVOURITES_COUNT = FRIEND_BASE + COUNT;
    public static final String UPDATE_FRIENDSHIP_STATUS = FRIENDS_BASE + Common.UPDATE;
    public static final String DELETE_FRIEND = FRIENDS_BASE + Common.DELETE;

    public static final String MY_CATEGORY_ITEMS_BY_ID = PROFILE_BASE + ME_BASE + CATEGORY_BASE + CATEGORY_ID;
    public static final String CONVERSATION_BY_ID = CONVERSATION_BASE + ID;
    public static final String CATEGORY_ITEMS_BY_ID = CATEGORY_BASE + ID;
    public static final String CREATE_CONVERSATION_NAME = CONVERSATION_BASE + ID + URLMappings.Common.NAME;

    public static final String MY_UPLOADS = UPLOAD_BASE + ME_BASE;

    public static final String CREATE_CATEGORY_ITEM = PROFILE_BASE + ME_BASE + CATEGORY_BASE;
    public static final String USER_CATEGORY_ITEMS_BY_ID = PROFILE_BASE + USER_ID + CATEGORY_BASE + CATEGORY_ID;

    public static final class User {
        public static final String NEW = "/new";
        public static final String PASSWORD = "/passwords";
        public static final String USERNAME = "/usernames";
        public static final String VERIFY = "/verify";
        public static final String VALIDATE = "/validate";
        public static final String PICTURE = "/picture";
    }

    public static final class Common {
        public static final String DELETE = "/delete";
        public static final String NAME = "/name";
        public static final String UPDATE = "/update";
        public static final String COUNT = "/count";
    }

    public static final class PathVar {
        public static final String ID = "/{id}";
        public static final String USER_ID = "/{userId}";
        public static final String FRIEND_ID = "/{friendId}";
        public static final String CATEGORY_ID = "/{categoryId}";
    }

}
