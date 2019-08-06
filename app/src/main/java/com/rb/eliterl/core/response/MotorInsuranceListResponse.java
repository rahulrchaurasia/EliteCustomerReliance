package com.rb.eliterl.core.response;

import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.model.InsuranceCompanyEntity;

import java.util.List;

/**
 * Created by Nilesh Birhade on 14-12-2018.
 */

public class MotorInsuranceListResponse extends APIResponse {


    private List<InsuranceCompanyEntity> Data;

    public List<InsuranceCompanyEntity> getData() {
        return Data;
    }

    public void setData(List<InsuranceCompanyEntity> Data) {
        this.Data = Data;
    }


}
