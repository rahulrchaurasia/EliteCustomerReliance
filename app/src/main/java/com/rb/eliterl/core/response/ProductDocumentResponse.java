package com.rb.eliterl.core.response;

import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.model.DocProductEnity;

import java.util.List;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class ProductDocumentResponse extends APIResponse {


    private List<DocProductEnity> Data;

    public List<DocProductEnity> getData() {
        return Data;
    }

    public void setData(List<DocProductEnity> Data) {
        this.Data = Data;
    }


}
