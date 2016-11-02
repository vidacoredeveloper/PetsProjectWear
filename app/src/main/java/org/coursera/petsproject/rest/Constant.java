package org.coursera.petsproject.rest;

import org.coursera.petsproject.activities.MainActivity;

/**
 * Created by Victor Daniel Cortés Restrepo on 17/10/16.
 */

public final class Constant {

    public static final String VERSION = "/v1/";
    public static final String ROOT_URL = "https://api.instagram.com" + VERSION;
    public static final String KEY_GET_INFORMATION_USER = "users/search?q=jack";
    public static final String KEY_GET_MEDIA_USER = "users/{user-id}/media/recent/";
    public static final String ACCESS_TOKEN = "4049814199.d01d5e5.194a9d8704894f64bee373bd827c4813";
    public static final String KEY_ACCESS_TOKEN_USER = "&access_token=" + ACCESS_TOKEN;
    public static final String KEY_ACCESS_TOKEN_MEDIA = "?access_token=" + ACCESS_TOKEN;


    //curl -F 'access_token=ACCESS-TOKEN' \
    //https://api.instagram.com/v1/media/{media-id}/likes
    public static final String KEY_UPDATE_LIKE = "curl -F 'access_token=" + ACCESS_TOKEN + "' ";
    public static final String KEY_UPDATE = KEY_UPDATE_LIKE + ROOT_URL;


    public static final String FIND_USER = ROOT_URL + KEY_GET_INFORMATION_USER + KEY_ACCESS_TOKEN_USER;
    public static final String FIND_MEDIA_USER = ROOT_URL + KEY_GET_MEDIA_USER + KEY_ACCESS_TOKEN_MEDIA;
    public static final String KEY_UPDATE_MEDIA_LIKE = KEY_UPDATE + "media/{media-id}/likes";
}
