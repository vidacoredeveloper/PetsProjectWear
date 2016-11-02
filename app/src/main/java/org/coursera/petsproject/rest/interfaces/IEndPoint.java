package org.coursera.petsproject.rest.interfaces;

import org.coursera.petsproject.rest.Constant;
import org.coursera.petsproject.rest.model.LikeResponse;
import org.coursera.petsproject.rest.model.PetResponse;
import org.coursera.petsproject.rest.model.PetUserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Victor Daniel Cortés Restrepo on 17/10/16.
 */

public interface IEndPoint {

    @GET(Constant.FIND_MEDIA_USER)
    public Call<PetResponse> getRecentMediaUser(@Path("user-id") String id);

    @GET(Constant.FIND_USER)
    public Call<PetUserResponse> getPetUSer(@Query("q") String name);

    @POST(Constant.KEY_UPDATE_MEDIA_LIKE)
    public Call<LikeResponse> setLikes(@Field("media-id") String id);
}
