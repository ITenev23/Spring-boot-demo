package bg.paysafe.springboot.api.constant;

public class Constants {

    public static final String UPLOAD_TYPE_VERIFICATION = "verification";

    public static final String STATUS_TYPE_VERIFIED = "VERIFIED";

    public static final String STATUS_TYPE_ACCEPTED = "ACCEPTED";

    public static final String FRIENDSHIP_STATUS_TYPE_PENDING = "PENDING";

    public static final String FRIENDSHIP_STATUS_TYPE_ACCEPTED = "ACCEPTED";

    public static final String DEFAULT_SORT = "DESC";

    public static final String IMAGES_SEPARATOR_IN_MESSAGE = "/\\|\\\\";

    public static final String DEFAULT_SORT_PROPERTIES_AGE = "age";

    public static final String DEFAULT_SORT_PROPERTIES_REGISTERED_DATE = "registeredOn";

    public static final String DEFAULT_SORT_PROPERTIES_VISITED_ON = "visitedOn";

    public static final String DEFAULT_SORT_PROPERTIES_ID = "id";

    public static final String DEFAULT_SORT_PROPERTIES_DATE = "m.createdAt";

    public static final String DEFAULT_SORT_PROPERTIES_MESSAGE_DATE = "createdAt";

    public static final String DEFAULT_PAGE = "0";

    public static final String DEFAULT_SIZE = "20";

    public static final String ROLE_ADMIN = "ADMIN";

    public static final String ROLE_USER_FREE = "USER_FREE";

    public static final String ROLE_USER_PAID = "USER_PAID";

    public static final String[] ALL_ROLES = new String[]{ROLE_ADMIN, ROLE_USER_FREE, ROLE_USER_PAID};

    public static final String[] ROLES_VERIFIED = new String[]{ROLE_ADMIN, ROLE_USER_PAID};

    public static class Number {
        public static final Long ONE = 1L;
        public static final Long TWO = 2L;
        public static final Long THREE = 3L;
        public static final Long FOUR = 4L;
        public static final Long FIVE = 5L;
    }

}
