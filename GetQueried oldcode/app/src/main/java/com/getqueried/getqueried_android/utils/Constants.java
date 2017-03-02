package com.getqueried.getqueried_android.utils;

/**
 * Created by Gaurav on 9/5/2016.
 */
public interface Constants {

    long ONE_SECOND = 1000;
    long ONE_MINUTE = 60 * 1000;
    String CHARSET_UTF_8 = "utf-8";

    String LOGIN = "login";
    String REFRESH = "refresh";
    String REGISTER = "register";

    int MAX_OPTIONS = 4;
    int IMG_WIDTH = 250;
    int IMG_HEIGHT = 250;

    interface User {
        String NAME = "name";
        String DESCRIPTION = "description";
        String IMAGEPATH = "imagePath";
        String SMALLIMAGEPATH = "image_path";
        String FB_ID = "fb_id";
        String FB_TOKEN = "fb_token";
        String EMAIL = "email";
        String PASSWORD = "password";
        String FB_ACCESS_TOKEN = "access";
        String USER_NAME = "name";
        String USER_TYPE = "type";
        String USER_ID = "userID";
        String EULA = "eula";
        String VERIFICATION_CODE = "code";
        String ACCESS_TOKEN = "access_token";
        String TOKEN_TYPE = "token_type";
        String REFRESH_TOKEN = "refresh_token";
        String SCOPE = "scope";
        String BIRTH = "birth";
        String GENDER = "gender";
        String EDUCATIONLEVEL = "educationLevel";
        String COUNTRYOFRESIDENCE = "countryOfResidence";
        String UPDATED = "updated";
        String VERIFIED_EMAIL = "verified_email";
        String VERIFIED_FB_EMAIL = "verified_fb_email";

    }

    interface TokenPayload {
        String EXP = "exp";
        String IAT = "iat";
        String ISS = "iss";
        String KID = "kid";
        String UUID = "uuid";
    }

    interface UserStats {
        String NUMBERFOLLOWERS = "numberFollowers";
        String NUMBERFOLLOWING = "numberFollowing";
        String POINTSCURRENT = "pointsCurrent";
        String POINTSEVER = "pointsEver";
        String TOKENS = "tokens";
        String ANSWERS = "answers";
        String QUERIES = "queries";
    }

    interface Follow {
        String USERSNOTFOLLOWING = "usersNotFollowing";
        String FOLLOWERS = "followers";
        String FOLLOWING = "following";
    }

    String USER_1_ID = "\"fb214b04-e4cf-499d-a963-97c7bd7d51e8\"";
    String USER_2_ID = "\"fd286baf-2358-41f1-a9d0-40d5bfbde0a7\"";
    String USER_3_ID = "\"51023469-6c73-4ad7-9bfb-8db1e03dcb45\"";
    String USER_4_ID = "43cae170-c725-44d2-9c97-10bdb52f7e1a";

    interface Query {
        String ID = "queryID";
        String TIME_FROM = "timeFrom";
        String TIME_TO = "timeTo";
        String QUESTION_TEXT = "questionText";
        String ANONYMOUS = "anonymous";
        String TYPE = "type";
        String ACTION = "action";
    }

    interface Error {
        String ERROR = "error";
        String ERROR_DESCRIPTION = "error_description";
    }

    interface CreateQuery {
        String QUERY_TITLE = "queryTitle";
        String ANONYMOUS = "anonymous";
        String TARGET = "target";
        String QUESTION_LIST = "questionList";
        String QUESTION_IMAGE_PATH = "questionImagePath";
        String TYPE = "type";
        String QUESTION_TEXT = "questionText";
        String QUESTION_IMAGE = "questionImage";
        String HAS_IMAGE = "questionImage";

        String IS_IMAGE_DELETED = "isImageDeleted";
        String IMAGE_DESCRIPTION = "imageDescription";
        String IS_IMAGE_ROTATED = "isImageRotated";
        String IMAGE_PATH = "imagePath";

        String SLIDER_TYPE = "sliderType";
        String CHOICE_MIN = "choiceMin";
        String ANSWER_OPTION_LIST = "answerOptionList";
        String CHOICE_MAX = "choiceMax";
        String ANSWER_OPTION_IMAGE = "answerOptionImage";
        String ANSWER_OPTION_IMAGE_0 = "answerOptionImage0";
        String ANSWER_OPTION_IMAGE_1 = "answerOptionImage1";
        String ANSWER_OPTION_IMAGE_2 = "answerOptionImage2";
        String ANSWER_OPTION_IMAGE_3 = "answerOptionImage3";
        String ANSWER_OPTION_TEXT = "answerOptionText";
        String OPTIONS = "options";
        String SPECIFICUSERS = "specificUsers";
        String TXTINP = "txtinp";
        String TXTOPT = "txtopt";
        String IMGOPT = "imgopt";
        String NUMIMP = "numimp";
    }

    interface Options {
        String LOCATION = "location";
        String GENDER = "gender";
        String LOCATIONLEVEL = "locationLevel";
    }

    interface Answer {
        String USER_ID = "userID";
        String ANSWER_TEXT = "answerText";
        String ANSWER_TIME = "answerTime";
        String QUERYOBJ = "queryObject";
        String QUERYID = "queryID";
        String QUESTION = "question";
        String QUESTIONTEXT = "questionText";
        String ANONYMOUS = "anonymous";
        String ANSWER_LIST = "answerList";
        String OPTION_1 = "1";
        String OPTION_2 = "2";
        String OPTION_3 = "3";
        String OPTION_4 = "4";
        String ANSWEROPTIONNUMBER = "answerOptionNum";
        String ANSWEROPTIONTEXT = "answerOptionText";
    }

    // keys to share info with fragments
    interface Data {
        String ANSWERS_PROVIDED = "isAnswersHidden";
        String IS_FOR_SHOWING_RESULT = "isForShowingResult";
        String OPTION_LIST = "optionList";
        String ARRAY_OF_QUERIES = "query_object";
        String QUERY_POSITION = "position";
        String FEEDS = "feeds";
    }

    interface Feed {
        String TYPE = "action";
        String QUERY_ID = "queryID";
        String SOURCE_ID = "source";
        String TARGET_ID = "target";
        String TIMESTAMP = "timestamp";
    }

    String COMMAND_FACADE_URL = "https://dev.getqueried.com:8000/v1.1";
    String QUERY_FACADE_URL = "https://dev.getqueried.com:8001/v1.1";

    interface URL {

        String registerPassword = "https://dev.getqueried.com:4430/register/password";
        String registerFb = "https://dev.getqueried.com:4430/register/fb";

        String activation = "https://dev.getqueried.com:4430/activation";

        String emaillogin = "https://dev.getqueried.com:4430/token?grant_type=password&client_id=gq_iosapp_v0.1" +
                "&scope=user.q_r+user.q_w";
        String fbLogin = "https://dev.getqueried.com:4430/token?grant_type=facebook&client_id=gq_iosapp_v0.1" +
                "&scope=user.q_r+user.q_w";

        String refreshToken = "https://dev.getqueried.com:4430//token?grant_type=refresh_token&scope=";

        String updateProfileImage = COMMAND_FACADE_URL + "/user/profile/image/update";
        String getUserProfileDemography = QUERY_FACADE_URL + "/user/profile/demography/pull";
        String updateUserProfileDemography = COMMAND_FACADE_URL + "/user/profile/demography/update";
        String userStats = QUERY_FACADE_URL + "/user/profile/public/stats/pull";
        String userMetadata = QUERY_FACADE_URL + "/user/profile/public/metadata/pull";

        String updateUserName = COMMAND_FACADE_URL + "/user/profile/name/update";
        String updateUserDescription = COMMAND_FACADE_URL + "/user/profile/description/update";

        String usersNotFollowing = QUERY_FACADE_URL + "/user/profile/public/notfollowing/pull";
        String userFollowers = QUERY_FACADE_URL + "/user/profile/public/followers/pull";
        String userFollowing = QUERY_FACADE_URL + "/user/profile/public/following/pull";

        String followUser = COMMAND_FACADE_URL + "/user/follow";
        String unfollowUser = COMMAND_FACADE_URL + "/user/unfollow";

        String userFeed = QUERY_FACADE_URL + "/user/feed/pull";
        String createQuery = COMMAND_FACADE_URL + "/query/create";

        String getQueryIdList = QUERY_FACADE_URL + "/query/list/pending/pull";
        String answerQuery = COMMAND_FACADE_URL + "/query/answer";
        String rejectQuery = COMMAND_FACADE_URL + "/query/reject";
        String likeQuery = COMMAND_FACADE_URL + "/query/like";
        String shareQuery = COMMAND_FACADE_URL + "/query/share";

        String createdQueriesId = QUERY_FACADE_URL + "/query/list/created/pull";

        String getQueriesStructure = QUERY_FACADE_URL + "/query/questions/pull";

        String answeredQueries = QUERY_FACADE_URL + "/query/list/answered/pull";
        String getQueryResults = QUERY_FACADE_URL + "/query/results/pull";

        String removeQuery = COMMAND_FACADE_URL + "/query/answered/hide";

        String getQueryMetadata = QUERY_FACADE_URL + "/query/list/metadata/pull";
        String getQueryStats = QUERY_FACADE_URL + "/query/list/stats/pull";

        // @"https://s3-eu-west-1.amazonaws.com/gq-queryimg/",\
        // @"https://s3-eu-west-1.amazonaws.com/gq-profileimg/"

        String getProfileImage = "https://s3-eu-west-1.amazonaws.com/gq-dev-profileimg/";
        String getQueryImage = "https://s3-eu-west-1.amazonaws.com/gq-dev-queryimg/";
    }
}
