package com.nascodefy.emosianpkm.Fragments;

import com.nascodefy.emosianpkm.Notifications.MyResponse;
import com.nascodefy.emosianpkm.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAALagc2YI:APA91bEH4KFn4Fes__CU5XA8sjcSGNdi8xDZrESOBU5nkGN9ABCieIZCKXAclJqTXsTwulWB5RTvkxmZ_R20aBd2V2XjJrMkgHm-Gq80kpsMWgEsQQyl5WjT7yJRJ1XyKgUW8eCNZzXJ"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
