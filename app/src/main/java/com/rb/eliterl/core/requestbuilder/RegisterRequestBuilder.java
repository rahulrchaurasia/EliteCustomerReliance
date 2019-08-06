package com.rb.eliterl.core.requestbuilder;

import com.rb.eliterl.core.RetroRequestBuilder;
import com.rb.eliterl.core.requestmodel.AddUserRequestEntity;
import com.rb.eliterl.core.requestmodel.RateRequestEntity;
import com.rb.eliterl.core.requestmodel.RegisterRequest;
import com.rb.eliterl.core.requestmodel.UpdateUserRequestEntity;
import com.rb.eliterl.core.response.AddUserResponse;
import com.rb.eliterl.core.response.CarMasterResponse;
import com.rb.eliterl.core.response.CityMainResponse;
import com.rb.eliterl.core.response.CommonResponse;
import com.rb.eliterl.core.response.DBVersionRespone;
import com.rb.eliterl.core.response.DisplayFeedbackResponse;
import com.rb.eliterl.core.response.DisplayRateResponse;
import com.rb.eliterl.core.response.FastLaneDataResponse;
import com.rb.eliterl.core.response.FeedbackResponse;
import com.rb.eliterl.core.response.GetOtpResponse;
import com.rb.eliterl.core.response.LoginResponse;
import com.rb.eliterl.core.response.PincodeResponse;
import com.rb.eliterl.core.response.PolicyResponse;
import com.rb.eliterl.core.response.ProfileResponse;
import com.rb.eliterl.core.response.RateResponse;
import com.rb.eliterl.core.response.UpdateUserResponse;
import com.rb.eliterl.core.response.UserConsttantResponse;
import com.rb.eliterl.core.response.UserRegistrationResponse;
import com.rb.eliterl.core.response.VehicleMasterResponse;
import com.rb.eliterl.core.response.VerifyUserRegisterResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class RegisterRequestBuilder extends RetroRequestBuilder {

    public RegisterRequestBuilder.RegisterQuotesNetworkService getService() {

        return super.build().create(RegisterRequestBuilder.RegisterQuotesNetworkService.class);
    }

    public interface RegisterQuotesNetworkService {

        @GET("/api/get-version")
        Call<DBVersionRespone> getDBVersion();

        @POST("/api/otp-insert")
        Call<GetOtpResponse> getOtp(@Body HashMap<String, String> body);     //Not User

        @POST("/api/add-user")
        Call<AddUserResponse> addUser(@Body AddUserRequestEntity body);

        @POST("/api/login")
        Call<LoginResponse> getLogin(@Body HashMap<String, String> body);

        @POST("/api/add-by-pincode")
        Call<PincodeResponse> getCity(@Body HashMap<String, String> body);

        @POST("/api/update-user")
        Call<UpdateUserResponse> updateUser(@Body UpdateUserRequestEntity updateUserRequestEntity);


        @POST("/api/change-password")
        Call<CommonResponse> changePassword(@Body HashMap<String, String> body);

        @POST("/api/forgot-password")
        Call<CommonResponse> forgotPassword(@Body HashMap<String, String> body);

        @POST("/api/reliance-login")

        Call<PolicyResponse> getPolicyData(@Body HashMap<String, String> body);

        @POST("/api/check-user-registration")
        Call<VerifyUserRegisterResponse> verifyUserRegistration(@Body HashMap<String, String> body);

        @POST("/api/user-otp-verify")
        Call<UserRegistrationResponse> userRegistration(@Body RegisterRequest registerRequest);

        @POST("/api/update-customer-profile")
        Call<UserRegistrationResponse> userProfile(@Body RegisterRequest registerRequest);

        @POST("/api/get-user-profile")
        Call<ProfileResponse> getProfile(@Body HashMap<String, String> body);


        @POST("/api/vehicle-details")
        Call<CarMasterResponse> getCarMaster(@Body HashMap<String, String> body);

        @POST("/api/get-user-constant")
        Call<UserConsttantResponse> getUserConstant(@Body HashMap<String, String> body);   //used


        @GET("/api/get-city-rto")
        Call<CityMainResponse> getCityMainMaster();  //used

        //vehicle request

        @POST("/api/get-vehicle-data")
        Call<VehicleMasterResponse> getVehicleData();


        @GET
        Call<VehicleMasterResponse> getCarMaster(@Url String url);

        @POST("/api/save-feedback-form")
        Call<FeedbackResponse> saveFeedBack(@Body HashMap<String, String> body);

        @POST("/api/display-feedback-form")
        Call<DisplayFeedbackResponse> displayFeedBack(@Body HashMap<String, String> body);

        @POST("/api/save-rate")
        Call<RateResponse> saveRate(@Body RateRequestEntity rateRequestEntity);

        @POST("/api/display-rate")
        Call<DisplayRateResponse> dispalyRate(@Body HashMap<String, String> body);


        @POST
        @Headers("token:1234567890")
        Call<FastLaneDataResponse> getFastLaneData(@Url String strUrl,@Body HashMap<String, String> body);

    }
}