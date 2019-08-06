package com.rb.eliterl.core.response;

import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.model.AllCityData;

/**
 * Created by IN-RB on 05-07-2018.
 */

public class CityResponse extends APIResponse {




    private AllCityData Data;

    public AllCityData getData() {
        return Data;
    }

    public void setData(AllCityData Data) {
        this.Data = Data;
    }


}
