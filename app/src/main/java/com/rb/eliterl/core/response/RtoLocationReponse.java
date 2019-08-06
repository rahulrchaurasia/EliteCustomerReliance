package com.rb.eliterl.core.response;

import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.model.RTOList;

/**
 * Created by IN-RB on 07-02-2018.
 */

public class RtoLocationReponse extends APIResponse {


    private RTOList Data;

    public RTOList getData() {
        return Data;
    }

    public void setData(RTOList Data) {
        this.Data = Data;
    }





}
