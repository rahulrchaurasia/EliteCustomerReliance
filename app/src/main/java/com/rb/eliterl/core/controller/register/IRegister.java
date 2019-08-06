package com.rb.eliterl.core.controller.register;

import com.rb.eliterl.core.IResponseSubcriber;
import com.rb.eliterl.core.requestmodel.AddUserRequestEntity;
import com.rb.eliterl.core.requestmodel.RateRequestEntity;
import com.rb.eliterl.core.requestmodel.RegisterRequest;
import com.rb.eliterl.core.requestmodel.UpdateUserRequestEntity;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public interface IRegister {

    void getDbVersion(IResponseSubcriber iResponseSubcriber);

    void getOtp(String email, String mobile, String ip, IResponseSubcriber iResponseSubcriber);

    void addUser(AddUserRequestEntity addUserRequestEntity, IResponseSubcriber iResponseSubcriber);

    void getCityState(String pincode, IResponseSubcriber iResponseSubcriber);

    void updateUser(UpdateUserRequestEntity updateUserRequestEntity, IResponseSubcriber iResponseSubcriber);

    void getLogin(String mobile, String password, String token,String devId, IResponseSubcriber iResponseSubcriber);

    void changePassword(String mobile, String curr_password, String new_password, IResponseSubcriber iResponseSubcriber);

    void forgotPassword(String mobile, IResponseSubcriber iResponseSubcriber);

    void getPolicyData(String PolicyNumber, IResponseSubcriber iResponseSubcriber);

    void saveUserRegistration(RegisterRequest registerRequest, IResponseSubcriber iResponseSubcriber);

    void saveUserProfile(RegisterRequest registerRequest, IResponseSubcriber iResponseSubcriber);

    void getUserProfile( IResponseSubcriber iResponseSubcriber);

    void verifyOTPTegistration(String email, String mobile, String ip, IResponseSubcriber iResponseSubcriber);

    void getCarVehicleMaster(IResponseSubcriber iResponseSubcriber);

    void getUserConstatnt( IResponseSubcriber iResponseSubcriber);

    void getCityMainMaster( IResponseSubcriber iResponseSubcriber);

    void saveFeedBack(String reqId,String userId, String feedback,  IResponseSubcriber iResponseSubcriber);

    void displayFeedBack( int user_id,  IResponseSubcriber iResponseSubcriber);

    void saveRate(RateRequestEntity rateRequestEntity, IResponseSubcriber iResponseSubcriber);

    void displayRate( int userid,String request_id,  IResponseSubcriber iResponseSubcriber);

  // fastlane
    void getVechileDetails(String RegistrationNumber, IResponseSubcriber iResponseSubcriber);
}
