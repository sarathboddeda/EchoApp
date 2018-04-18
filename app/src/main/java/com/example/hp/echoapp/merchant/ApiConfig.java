package com.example.hp.echoapp.merchant;

import com.example.hp.echoapp.ServerResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by HP on 12/7/2017.
 */

public interface ApiConfig {
    @Multipart
    //@FormUrlEncoded
    @POST("echoapp/services2/addimage.php")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part image);

    @POST("echoapp/services2/products.php?function=addproduct")
    Call<ServerResponse>addfile(@Body ServerResponse request);
}
