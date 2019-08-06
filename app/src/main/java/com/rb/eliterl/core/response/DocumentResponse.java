package com.rb.eliterl.core.response;

import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.model.DocResultEntity;

import java.util.List;

/**
 * Created by IN-RB on 04-06-2018.
 */

public class DocumentResponse extends APIResponse {
    private List<DocResultEntity> Data;

    public List<DocResultEntity> getData() {
        return Data;
    }

    public void setData(List<DocResultEntity> Data) {
        this.Data = Data;
    }




}
