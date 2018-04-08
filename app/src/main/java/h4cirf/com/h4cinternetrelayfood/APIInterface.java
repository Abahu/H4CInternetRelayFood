package h4cirf.com.h4cinternetrelayfood;

import java.util.ArrayList;

import h4cirf.com.h4cinternetrelayfood.models.PostModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface APIInterface {

    /**
     * Gets the list of most recent posts
     */
    @GET("/posts")
    Call<ArrayList<PostModel>> doGetListResources(@Query("offset") int offset, @Query("limit") int limit);

    @POST("/posts")
    Call<Void> doPutPost(@Header("Authorization") String authToken, @Body PostModel postModel);
}
