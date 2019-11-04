package com.ziasy.vaishnavvivah.NetworkManager;





import com.google.gson.JsonObject;
import com.ziasy.vaishnavvivah.model.ImageModel;
import com.ziasy.vaishnavvivah.model.Login_model;
import com.ziasy.vaishnavvivah.model.NewLoginModel;
import com.ziasy.vaishnavvivah.model.OtpModel;
import com.ziasy.vaishnavvivah.model.Signup_model;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


/**
 * Created by ANDROID on 08-Jan-18.
 */

public interface APIInterface {

  /*  @GET("offer")
      Call<SliderModel> getImages();

      @FormUrlEncoded
      @POST("tiffinplaceorder")
      Call<SignupModel> getTiffinOrder(@Field("client_id") String client_id, @Field("tiffinid") String tiffinid,
                                     @Field("name") String name, @Field("price") String price,
                                     @Field("quantity") String quantity, @Field("period") String period,
                                     @Field("package_id") String package_id, @Field("coupon") String coupon,
                                     @Field("start_date") String start_date, @Field("end_date") String end_date);*/


    @POST("login.php")
    Call<NewLoginModel> postLogin(
            @Body JsonObject body);

/*
    @FormUrlEncoded
    @POST("login.php")
    Call<Login_model>getLogin(@Field("user_mobile") String mobile, @Field("user_password") String password,
                              @Field("device_id") String device_id);*/

  /*  @POST("registration.php")
    Call<Signup_model> getSignUp(@Body JsonObject jsonObject);*/

    @POST("registration.php")
    Call<Signup_model> postData(
            @Body JsonObject body);
    /*

    @FormUrlEncoded
    @POST("registration.php")
    Call<Signup_model> getSignUp(@Field("name") String name, @Field("email") String email,
                                 @Field("mobile") String mobile,
                                 @Field("password") String password, @Field("imei") String imei,
                                 @Field("device_id") String device_id  );
*/

    @POST("profile.php")
    Call<Login_model> getProfile(@Body JsonObject body);


    @POST("profile-update.php")
    Call<Login_model> getUpdateProfile(@Body JsonObject jsonObject);


    @POST("resend-otp.php")
    Call<OtpModel> getResendOtp(@Body JsonObject body);


    @POST("change-password.php")
    Call<Login_model> getResetPassword(@Body JsonObject object);


    @POST("intrest-in-profile.php")
    Call<Login_model> getLike(@Body JsonObject jsonObject);


    @POST("user-profile.php")
    Call<Login_model> getProfiles(@Body JsonObject jsonObject);


    @POST("intrest-in-me.php")
    Call<Login_model> getInteresrIn(@Body JsonObject jsonObject);
//

    @POST("forgot-password.php")
    Call<OtpModel>getForgot(@Body JsonObject object);



    @POST("update-password.php")
    Call<Login_model>getUpdatePassword(@Body JsonObject object);
///

    @POST("profile-picture.php")
    Call<Login_model> getUpload(@Body JsonObject object);

    @POST("image-upload.php")
    Call<Login_model> getImageUpload(@Body JsonObject object);


    @POST("image-upload-view.php")
    Call<ImageModel> getImageUploadView(@Body JsonObject object);


    @POST("search-filter.php")
    Call<Login_model> getFilter(@Body JsonObject jsonObject);


    @POST("paymentProcesses.php")
    Call<Login_model> getPayment(@Field("user_id") String user_id,@Field("txn_id") String txn_id,
                              @Field("payment_type") String payment_type,@Field("payment_source") String payment_source,
                              @Field("payment_status") String payment_status,@Field("amount") String amount);



     @POST("search.php")
     Call<Login_model> getSearch(@Body JsonObject jsonObject);


    @POST("delete.php")
    Call<Login_model> getDelet(@Body JsonObject jsonObject);



    @Multipart
    @POST("profileUpdate.php")
    Call<Login_model>uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name,
                    @Part("education") String education, @Part("occupation") String occupation,
                    @Part("mother_occupation") String mother_occupation,@Part("father_occupation") String father_occupation,
                    @Part("address") String address,@Part("city") String city,
                    @Part("state") String state,@Part("income") String income,
                    @Part("marital_status") String marital_status
                    );


    @FormUrlEncoded
    @POST("generateChecksum.php")
    Call<Login_model> getChecksum(@Field("orderid") String ORDER_ID,
                                 @Field("customerid") String CUST_ID,
                                 @Field("amount") String TXN_AMOUNT,
                                 @Field("email") String email,@Field("mobile") String mobile);

}

