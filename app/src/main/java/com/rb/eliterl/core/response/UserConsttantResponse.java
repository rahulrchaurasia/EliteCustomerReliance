package com.rb.eliterl.core.response;

import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.model.UserConstatntEntity;

import java.util.List;

/**
 * Created by IN-RB on 16-11-2018.
 */

public class UserConsttantResponse extends APIResponse {


    private List<UserConstatntEntity> Data;

    public List<UserConstatntEntity> getData() {
        return Data;
    }

    public void setData(List<UserConstatntEntity> Data) {
        this.Data = Data;
    }


}
