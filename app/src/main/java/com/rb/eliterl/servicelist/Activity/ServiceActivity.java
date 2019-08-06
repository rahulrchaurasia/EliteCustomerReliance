package com.rb.eliterl.servicelist.Activity;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.rb.eliterl.BaseActivity;
import com.rb.eliterl.R;
import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.IResponseSubcriber;
import com.rb.eliterl.core.controller.product.ProductController;
import com.rb.eliterl.core.model.ServiceMainEntity;
import com.rb.eliterl.core.model.UserConstatntEntity;
import com.rb.eliterl.core.model.UserEntity;
import com.rb.eliterl.core.response.ServiceListResponse;
import com.rb.eliterl.servicelist.NonRTOListFragment;
import com.rb.eliterl.servicelist.RTOListFragment;
import com.rb.eliterl.servicelist.adapter.ServicePagerAdapter;
import com.rb.eliterl.splash.PrefManager;
import com.rb.eliterl.utility.Constants;

public class ServiceActivity extends BaseActivity  implements IResponseSubcriber {

    private String RTO = "RTO SERVICES";
    private String NonRTO = "MISCELLANEOUS SERVICES";

    private TabLayout tabLayout;
    TextView txtName, txtVehicle;
    public ViewPager viewPager;
    ServiceMainEntity mMasterData;

    UserEntity loginEntity;
    UserConstatntEntity userConstatntEntity;
    PrefManager prefManager;
    int SERVICE_CURRENT_POSTION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();

        if(getIntent().hasExtra(Constants.SERVICE_POSTION))
        {
            SERVICE_CURRENT_POSTION = getIntent().getIntExtra(Constants.SERVICE_POSTION,0);
        }
        initialize();
        setUserInfo();
        showDialog();
        new ProductController(ServiceActivity.this).getRtoAndNonRtoList(this);


    }

    private void initialize() {
        mMasterData = new ServiceMainEntity();
        viewPager = (ViewPager) findViewById(R.id.viewpager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        txtName = findViewById(R.id.txtName);
        txtVehicle = findViewById(R.id.txtVehicle);


        // setupTabIcons();
    }

    private void setUserInfo() {

        if (loginEntity != null) {
            txtName.setText("" + loginEntity.getName());

        } else {
            txtName.setText("");

        }

        if(userConstatntEntity!=null)
        {
            txtVehicle.setText("" +userConstatntEntity.getVehicleno() );
        }else{
            txtVehicle.setText("");
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ServicePagerAdapter adapter = new ServicePagerAdapter(ServiceActivity.this.getSupportFragmentManager(), mMasterData);


        adapter.addFrag(new RTOListFragment(), RTO);
        adapter.addFrag(new NonRTOListFragment(), NonRTO);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(SERVICE_CURRENT_POSTION);


        tabLayout.setupWithViewPager(viewPager);



    }





    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof ServiceListResponse) {
            if (response.getStatus_code() == 0) {

                if  (((ServiceListResponse) response).getData() != null)    {

                    if ((((ServiceListResponse) response).getData().getRTO().size() != 0) && (((ServiceListResponse) response).getData().getNONRTO().size() != 0)) {

//                        rtoServiceEntityList = ((ServiceListResponse) response).getData().getRTO();
//
//                        nonRtoServiceEntityList = ((ServiceListResponse) response).getData().getNONRTO();
                        mMasterData = ((ServiceListResponse) response).getData();

                        setupViewPager(viewPager);

                    }
                }
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // finish();
        supportFinishAfterTransition();
        super.onBackPressed();
    }
}
