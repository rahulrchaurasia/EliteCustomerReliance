package com.rb.eliterl.core.response;

import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.model.RtoProductDisplayMainEntity;

import java.util.List;

/**
 * Created by IN-RB on 08-08-2018.
 */

public class RtoProductDisplayResponse extends APIResponse {
    private List<RtoProductDisplayMainEntity> Data;

    public List<RtoProductDisplayMainEntity> getData() {
        return Data;
    }

    public void setData(List<RtoProductDisplayMainEntity> Data) {
        this.Data = Data;
    }








}
