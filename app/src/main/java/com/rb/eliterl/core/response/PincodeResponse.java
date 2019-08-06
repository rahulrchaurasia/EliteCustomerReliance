package com.rb.eliterl.core.response;

import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.model.PincodeEntity;

import java.util.List;

/**
 * Created by Rajeev Ranjan on 03/02/2018.
 */

public class PincodeResponse extends APIResponse {

    private List<PincodeEntity> Data;

    public List<PincodeEntity> getData() {
        return Data;
    }

    public void setData(List<PincodeEntity> Data) {
        this.Data = Data;
    }
}
