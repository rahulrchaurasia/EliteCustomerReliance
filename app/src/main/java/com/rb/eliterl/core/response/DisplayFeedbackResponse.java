package com.rb.eliterl.core.response;

import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.model.FeedBackDisplayEntity;

import java.util.List;

/**
 * Created by Rajeev Ranjan on 08/01/2019.
 */

public class DisplayFeedbackResponse extends APIResponse {


    private List<FeedBackDisplayEntity> Data;

    public List<FeedBackDisplayEntity> getData() {
        return Data;
    }

    public void setData(List<FeedBackDisplayEntity> Data) {
        this.Data = Data;
    }


}
