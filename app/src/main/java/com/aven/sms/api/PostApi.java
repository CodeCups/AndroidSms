package com.aven.sms.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by aven on 18-2-5.
 */

public interface PostApi {
    @FormUrlEncoded
    @POST("/test")
    Call<ResponseBody> postData(@Field("phone") String name, @Field("username") String password);
}
