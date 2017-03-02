package com.getqueried.getqueried_android.model;

import java.util.Date;

/**
 * Created by just_me on 09.11.16.
 */
public class FeedItem {

    /**
     * - FeedItemTypeLiked = 1,
     - FeedItemTypeFollowed = 2,
     - FeedItemTypeSharedFacebook = 3,
     - FeedItemTypeSharedTwitter = 4
     */
    public enum FeedItemType {
        LIKED, FOLLOWED, SHARED_ON_FACEBOOK, SHARED_ON_TWITTER, UNDEFINED;

        public static FeedItemType intToEnum(int i) {
            switch (i) {
                case 1: return LIKED;
                case 2: return FOLLOWED;
                case 3: return SHARED_ON_FACEBOOK;
                case 4: return SHARED_ON_TWITTER;
                default: return UNDEFINED;
            }
        }

        public static int enumToFeed(FeedItemType type) {
            switch (type) {
                case LIKED: return 1;
                case FOLLOWED: return 2;
                case SHARED_ON_FACEBOOK: return 3;
                case SHARED_ON_TWITTER: return 4;
                default: return -1;
            }
        }
    }

    public FeedItemType type;
    public String sourceID;
    public String targetID;
    public String queryID;
    public Date time;

    public FeedItem(FeedItemType type, String sourceID, String targetID, String queryID, Date time) {
        this.type = type;
        this.sourceID = sourceID;
        this.targetID = targetID;
        this.queryID = queryID;
        this.time = time;
    }
}
