package com.rb.eliterl.rto_fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rb.eliterl.BaseFragment;
import com.rb.eliterl.R;
import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.IResponseSubcriber;
import com.rb.eliterl.core.controller.misc_non_rto.MiscNonRTOController;
import com.rb.eliterl.core.controller.product.ProductController;
import com.rb.eliterl.core.model.CityMainEntity;
import com.rb.eliterl.core.model.ProductPriceEntity;
import com.rb.eliterl.core.model.RTOServiceEntity;
import com.rb.eliterl.core.model.UserConstatntEntity;
import com.rb.eliterl.core.model.UserEntity;
import com.rb.eliterl.core.requestmodel.ProductPriceRequestEntity;
import com.rb.eliterl.core.requestmodel.TransferOwnershipRequestEntity;
import com.rb.eliterl.core.response.ProductPriceResponse;
import com.rb.eliterl.core.response.RtoProductDisplayResponse;
import com.rb.eliterl.database.DataBaseController;
import com.rb.eliterl.payment.PaymentRazorActivity;
import com.rb.eliterl.product.ProductMainActivity;
import com.rb.eliterl.search.SearchCityActivity;
import com.rb.eliterl.splash.PrefManager;
import com.rb.eliterl.utility.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferOwnershipFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {

    // service : 4
    // region Common Declaration
    private Context mContext;
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    EditText  etCity ,  etVehicle ,etPincode ;
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    Button btnBooked;

    RTOServiceEntity serviceEntity;

    ScrollView scrollView;
    LinearLayout lyVehicle ,lvLogo, lyTAT;
    RelativeLayout rlDoc ,rlEditVehicle;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;

    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;
    int PARENT_PRODUCT_ID = 0;

    String AMOUNT = "0";
    int OrderID = 0;

    String CITY_ID;
    ProductPriceEntity productPriceEntity;
    //endregion

    EditText etNewOwner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_transfer_ownership, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mContext = view.getContext();

        initialize(view);

        setOnClickListener();

        dataBaseController = new DataBaseController(getActivity());
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();

        OrderID = 0;
        bindData();

        // region Filter Type

        if (getArguments() != null) {


            if (getArguments().getParcelable(Constants.SUB_PRODUCT_DATA) != null) {

                serviceEntity = getArguments().getParcelable(Constants.SUB_PRODUCT_DATA);
                PRODUCT_NAME = serviceEntity.getName();
                PARENT_PRODUCT_ID = serviceEntity.getId();
                PRODUCT_CODE = serviceEntity.getProductcode();


            }

            //endregion

            txtPrdName.setText("" + PRODUCT_NAME);
         //   Toast.makeText(getActivity(), "" + PRODUCT_ID + "/" + PRODUCT_CODE, Toast.LENGTH_SHORT).show();
        }


        // endregion


        showDialog();
        new ProductController(getActivity()).getRTOProductList(PARENT_PRODUCT_ID, PRODUCT_CODE, loginEntity.getUser_id(), TransferOwnershipFragment.this);


        super.onViewCreated(view, savedInstanceState);
    }

    private void initialize(View view) {

        prefManager = new PrefManager(getActivity());

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        btnBooked = (Button) view.findViewById(R.id.btnBooked);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etVehicle = (EditText) view.findViewById(R.id.etVehicle);
        etPincode  = (EditText) view.findViewById(R.id.etPincode);
        etNewOwner = (EditText) view.findViewById(R.id.etNewOwner);

        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        rlEditVehicle = (RelativeLayout) view.findViewById(R.id.rlEditVehicle);

        lyVehicle = (LinearLayout) view.findViewById(R.id.lyVehicle);
        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);

        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);


        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);

        etVehicle.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(20)});


    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);

        rlDoc.setOnClickListener(this);
        rlEditVehicle.setOnClickListener(this);
        btnBooked.setOnClickListener(this);

        etCity.setOnClickListener(this);



    }

    private void bindData() {
        Glide.with(getActivity())
                .load(userConstatntEntity.getCompanylogo())
                .into(ivClientLogo);

        txtClientName.setText(userConstatntEntity.getCompanyname());
        if(userConstatntEntity.getVehicleno().length() >0)
        {
            etVehicle.setText(userConstatntEntity.getVehicleno());
            etVehicle.setEnabled(false);
            rlEditVehicle.setVisibility(View.VISIBLE);
            lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_edit));


        }else{
            lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_dashboard));
            rlEditVehicle.setVisibility(View.GONE);
            etVehicle.setEnabled(true);

        }


    }

    private void setVehicleEdiatable() {
        etVehicle.setEnabled(true);

        etVehicle.setText("");
        lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_dashboard));

    }

    private void getTatData() {
        if (productPriceEntity != null) {
            lvLogo.setVisibility(View.VISIBLE);
            txtCharges.setText(productPriceEntity.getPrice());
            txtTAT.setText(productPriceEntity.getTAT());

        } else {
            lvLogo.setVisibility(View.GONE);
        }
    }

    private void saveData() {

        showDialog();
        TransferOwnershipRequestEntity requestEntity = new TransferOwnershipRequestEntity();
        requestEntity.setProdName(PRODUCT_NAME);
        requestEntity.setAmount(txtCharges.getText().toString());
        requestEntity.setCityid(String.valueOf(CITY_ID));
        requestEntity.setPayment_status("1");
        requestEntity.setProdid(String.valueOf(PRODUCT_ID));

        requestEntity.setRto_id(productPriceEntity.getRto_id());
        requestEntity.setTransaction_id("");
        requestEntity.setUserid(String.valueOf(loginEntity.getUser_id()));
        requestEntity.setVehicleno(etVehicle.getText().toString());

        requestEntity.setPincode(etPincode.getText().toString());
        requestEntity.setNew_owner(etNewOwner.getText().toString());


        Bundle bundle = new Bundle();
        bundle.putString(Constants.REQUEST_TYPE,"4");
        bundle.putParcelable(Constants.PRODUCT_PAYMENT_REQUEST,requestEntity);


        getActivity().startActivity(new Intent(getActivity(), PaymentRazorActivity.class)
                .putExtra(Constants.PAYMENT_REQUEST_BUNDLE,bundle)) ;


        getActivity().finish();



    }

    private void setScrollatBottom() {
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 1000);
    }

    private boolean validate() {
        if (!validateVehicle(etVehicle)) {

            return false;
        }
        else if (!isEmpty(etNewOwner)) {
            etNewOwner.requestFocus();
            etNewOwner.setError("Enter New Owner");
            return false;
        }
        else if (!validateCity(etCity)) {

            return false;
        }
        else if (!validatePinCode(etPincode)) {

            return false;
        }

        return true;
    }



    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view,mContext);
        switch (view.getId()) {


            case R.id.rlDoc:
                ((ProductMainActivity) getActivity()).getProducDoc(PRODUCT_ID);
                break;

            case R.id.rlEditVehicle:

                setVehicleEdiatable();
                break;
            case R.id.btnBooked:

                if (validate() == false) {
                    return;
                }
                else {

                    saveData();
                }
                break;

            case R.id.etCity:
                setScrollatBottom();
                startActivityForResult(new Intent(getActivity(), SearchCityActivity.class), Constants.SEARCH_CITY_CODE);

                break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SEARCH_CITY_CODE) {
            if (data != null) {

                CityMainEntity cityMainEntity = data.getParcelableExtra(Constants.SEARCH_CITY_DATA);
                CITY_ID = String.valueOf(cityMainEntity.getCity_id());
                etCity.setText(cityMainEntity.getCityname());
                etCity.setError(null);

                showDialog();

                //region call Price Controller
                ProductPriceRequestEntity entity = new ProductPriceRequestEntity();
                entity.setVehicleno(etVehicle.getText().toString());
                entity.setCityid(CITY_ID);
                entity.setProduct_id(String.valueOf(PRODUCT_ID));
                entity.setProductcode(PRODUCT_CODE);
                entity.setUserid(String.valueOf(loginEntity.getUser_id()));
                entity.setMake("");
                entity.setModel("");

                new MiscNonRTOController(mContext).getProductTAT(entity, this);

                //endregion

            }
        }

    }


    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof ProductPriceResponse) {
            if (response.getStatus_code() == 0) {

                productPriceEntity = ((ProductPriceResponse) response).getData().get(0);
                getTatData();

            }
        }else if (response instanceof RtoProductDisplayResponse) {
            if (response.getStatus_code() == 0) {

                if (((RtoProductDisplayResponse) response).getData().size() > 0) {


                    PRODUCT_ID = ((RtoProductDisplayResponse) response).getData().get(0).getProd_id();
                }
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
    }
}
