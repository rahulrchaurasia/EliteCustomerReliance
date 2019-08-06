package com.rb.eliterl.core.response;

import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.model.CityMainEntity;

import java.util.List;

/**
 * Created by IN-RB on 04-12-2018.
 */

public class CityMainResponse extends APIResponse {
    private List<CityMainEntity> Data;

    public List<CityMainEntity> getData() {
        return Data;
    }

    public void setData(List<CityMainEntity> Data) {
        this.Data = Data;
    }




}
