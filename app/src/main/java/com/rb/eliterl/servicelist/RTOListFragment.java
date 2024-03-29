package com.rb.eliterl.servicelist;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rb.eliterl.BaseFragment;
import com.rb.eliterl.R;
import com.rb.eliterl.core.model.RTOServiceEntity;
import com.rb.eliterl.core.model.UserConstatntEntity;
import com.rb.eliterl.core.model.UserEntity;
import com.rb.eliterl.database.DataBaseController;
import com.rb.eliterl.product.ProductMainActivity;
import com.rb.eliterl.servicelist.adapter.RTOServiceAdapter;
import com.rb.eliterl.servicelist.adapter.ServicePagerAdapter;
import com.rb.eliterl.splash.PrefManager;
import com.rb.eliterl.utility.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RTOListFragment extends BaseFragment {


    List<RTOServiceEntity> mRTOList;
    RecyclerView rvProduct;
    RTOServiceAdapter mAdapter;
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    UserConstatntEntity userConstatntEntity;
    PrefManager prefManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_rtolist, container, false);

        View view = inflater.inflate(R.layout.fragment_rtolist, container, false);

        dataBaseController = new DataBaseController(getActivity());
        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();

        initialize(view);
       // setUserInfo();
        mRTOList = new ArrayList<>();

        if (getArguments().getParcelableArrayList(ServicePagerAdapter.RTO_LIST) != null) {
            mRTOList = getArguments().getParcelableArrayList(ServicePagerAdapter.RTO_LIST);
            bindData();
        }
        return view;
    }

    private void initialize(View view) {

        rvProduct = (RecyclerView) view.findViewById(R.id.rvProduct);
        rvProduct.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvProduct.setLayoutManager(layoutManager);


    }



    private void bindData() {
        mAdapter = new RTOServiceAdapter(RTOListFragment.this, mRTOList);
        rvProduct.setAdapter(mAdapter);

    }

    public void getProduct(RTOServiceEntity rtoServiceEntity) {

        if (rtoServiceEntity.getSubcategory().size() > 0) {
            Intent intent = new Intent(getActivity(), SubProductActivity.class);
            intent.putParcelableArrayListExtra(Constants.SUB_PRODUCT_LIST, (ArrayList<RTOServiceEntity>) rtoServiceEntity.getSubcategory());
            intent.putExtra(Constants.SERVICE_TYPE, Constants.SERVICE_RTO);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), ProductMainActivity.class);
            intent.putExtra(Constants.RTO_PRODUCT_DATA, rtoServiceEntity);
            intent.putExtra(Constants.SERVICE_TYPE, Constants.SERVICE_RTO);
            startActivity(intent);
        }
    }
}
