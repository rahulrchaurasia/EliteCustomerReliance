package com.rb.eliterl.rto_fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rb.eliterl.BaseFragment;
import com.rb.eliterl.R;
import com.rb.eliterl.core.APIResponse;
import com.rb.eliterl.core.IResponseSubcriber;
import com.rb.eliterl.core.controller.misc_non_rto.MiscNonRTOController;
import com.rb.eliterl.core.controller.product.ProductController;
import com.rb.eliterl.core.controller.register.RegisterController;
import com.rb.eliterl.core.model.CityMainEntity;
import com.rb.eliterl.core.model.FastLaneDataEntity;
import com.rb.eliterl.core.model.MakeEntity;
import com.rb.eliterl.core.model.ModelEntity;
import com.rb.eliterl.core.model.ProductPriceEntity;
import com.rb.eliterl.core.model.RTOServiceEntity;
import com.rb.eliterl.core.model.RtoProductDisplayMainEntity;
import com.rb.eliterl.core.model.UserConstatntEntity;
import com.rb.eliterl.core.model.UserEntity;
import com.rb.eliterl.core.requestmodel.ProductPriceRequestEntity;
import com.rb.eliterl.core.requestmodel.RCRequestEntity;
import com.rb.eliterl.core.response.FastLaneDataResponse;
import com.rb.eliterl.core.response.ProductPriceResponse;
import com.rb.eliterl.core.response.RtoProductDisplayResponse;
import com.rb.eliterl.core.response.VehicleMasterResponse;
import com.rb.eliterl.database.DataBaseController;
import com.rb.eliterl.payment.PaymentRazorActivity;
import com.rb.eliterl.product.ProductMainActivity;
import com.rb.eliterl.register.MakeAdapter;
import com.rb.eliterl.register.ModelAdapter;
import com.rb.eliterl.search.SearchCityActivity;
import com.rb.eliterl.splash.PrefManager;
import com.rb.eliterl.utility.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RenewRcFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {


    // Service : 1.0

    //region Declaration
    private Context mContext;
    PrefManager prefManager;
    UserConstatntEntity userConstatntEntity;

    // RecyclerView rvCity , rvRTO;

    TextView textCity, txtModel;
    Spinner spRTO, spCity;
    EditText etPincode, etVehicle, etCity;
    List<String> RtoList, CityList;  //ProductList,
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    Button btnBooked, btnGo;
    ScrollView scrollView;

    RTOServiceEntity serviceEntity;


    LinearLayout lyVehicle, lvLogo, llDocumentUpload, lyRTO, lyTAT;
    RelativeLayout rlDoc, rlEditMakeModel;
    LinearLayout lyMakeModel;
    ImageView ivLogo, ivClientLogo;

    TextView txtCharges, txtPrdName, txtDoc, txtClientName, txtTAT;

    AutoCompleteTextView acMake, acModel;

    String PRODUCT_NAME = "";
    String PRODUCT_CODE = "";
    int PRODUCT_ID = 0;
    int PARENT_PRODUCT_ID = 0;

    String AMOUNT = "0";
    int OrderID = 0;


    MakeAdapter makeAdapter;
    ModelAdapter modelAdapter;
    MakeEntity makeEntity;
    ModelEntity modelEntity;

    boolean IsMakeValid = false;
    boolean IsModelValid = false;

    String CITY_ID;

    ProductPriceEntity productPriceEntity;

    //endregion


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_renew_rc, container, false);

        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        mContext = view.getContext();

        initialize(view);

        setOnClickListener();

        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();

        userConstatntEntity = prefManager.getUserConstatnt();
        CityList = new ArrayList<String>();
        RtoList = new ArrayList<String>();
        OrderID = 0;


        if (prefManager.getMake() != null) {
            makeAdapter = new MakeAdapter(getActivity(), R.layout.fragment_renew_rc, R.id.lbl_name, prefManager.getMake());
            acMake.setAdapter(makeAdapter);
            bindMakeModel();

        } else {

            showDialog();
            new RegisterController(getActivity()).getCarVehicleMaster(this);

        }
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
            // Toast.makeText(getActivity(), "" + PRODUCT_ID + "/" + PRODUCT_CODE, Toast.LENGTH_SHORT).show();
            super.onViewCreated(view, savedInstanceState);
        }


        // endregion


        textCity.setText("Select City");
        showDialog();
        new ProductController(getActivity()).getRTOProductList(PARENT_PRODUCT_ID, PRODUCT_CODE, loginEntity.getUser_id(), RenewRcFragment.this);

    }

    //region  Method

    private void initialize(View view) {


        scrollView = (ScrollView) view.findViewById(R.id.scrollView);

        textCity = (TextView) view.findViewById(R.id.textCity);
        txtModel = (TextView) view.findViewById(R.id.txtModel);
        spRTO = (Spinner) view.findViewById(R.id.spRTO);
        spCity = (Spinner) view.findViewById(R.id.spCity);

        btnBooked = (Button) view.findViewById(R.id.btnBooked);
        btnGo = (Button) view.findViewById(R.id.btnGo);

        etPincode = (EditText) view.findViewById(R.id.etPincode);

        etCity = (EditText) view.findViewById(R.id.etCity);
        etVehicle = (EditText) view.findViewById(R.id.etVehicle);

        txtCharges = (TextView) view.findViewById(R.id.txtCharges);
        txtPrdName = (TextView) view.findViewById(R.id.txtPrdName);
        txtDoc = (TextView) view.findViewById(R.id.txtDoc);
        txtClientName = (TextView) view.findViewById(R.id.txtClientName);
        txtTAT = (TextView) view.findViewById(R.id.txtTAT);

        rlDoc = (RelativeLayout) view.findViewById(R.id.rlDoc);
        rlEditMakeModel = (RelativeLayout) view.findViewById(R.id.rlEditMakeModel);
        lyVehicle = (LinearLayout) view.findViewById(R.id.lyVehicle);
        lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);
        llDocumentUpload = (LinearLayout) view.findViewById(R.id.llDocumentUpload);
        lyRTO = (LinearLayout) view.findViewById(R.id.lyRTO);
        lyTAT = (LinearLayout) view.findViewById(R.id.lyTAT);

        lyMakeModel = (LinearLayout) view.findViewById(R.id.lyMakeModel);

        ivLogo = (ImageView) view.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) view.findViewById(R.id.ivClientLogo);

        acMake = (AutoCompleteTextView) view.findViewById(R.id.acMake);
        acModel = (AutoCompleteTextView) view.findViewById(R.id.acModel);

        acModel.setEnabled(false);
        acModel.setEnabled(false);

        acMake.setThreshold(2);
        acMake.setSelection(0);

        acModel.setThreshold(1);
        acModel.setSelection(0);

        acModel.setEnabled(false);
        acMake.setEnabled(false);

        etVehicle.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(20)});


    }

    private void setOnClickListener() {

        etCity.setFocusable(false);
        etCity.setClickable(true);


        rlDoc.setOnClickListener(this);
        btnBooked.setOnClickListener(this);
        btnGo.setOnClickListener(this);
        rlEditMakeModel.setOnClickListener(this);

        etCity.setOnClickListener(this);


    }

    private void setAutoComplete() {

        if (prefManager.getMake() != null) {


            // region Make Listener
            acMake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    acMake.setError(null);
                    acMake.setSelection(0);
                    IsMakeValid = true;
                    makeEntity = makeAdapter.getItem(position);
                    if (makeEntity.getModel() != null) {
                        modelAdapter = new ModelAdapter(getActivity(),
                                R.layout.activity_sign_up, R.id.lbl_name, makeEntity.getModel());
                        acModel.setAdapter(modelAdapter);
                        acModel.setEnabled(true);
                        IsModelValid = true;
                        acModel.setVisibility(View.VISIBLE);
                        txtModel.setVisibility(View.VISIBLE);

                    } else {
                        acModel.setText("");
                        acModel.setEnabled(false);
                        acModel.setVisibility(View.INVISIBLE);
                        txtModel.setVisibility(View.INVISIBLE);
                        IsModelValid = false;


                    }
                }
            });


            acMake.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.length() > 0) {
                        String str = acMake.getText().toString();

                        ListAdapter listAdapter = acMake.getAdapter();

                        if (listAdapter != null) {
                            for (int i = 0; i < listAdapter.getCount(); i++) {
                                String temp = listAdapter.getItem(i).toString().toUpperCase();
                                if (str.compareTo(temp) == 0) {
                                    acMake.setError(null);
                                    acModel.setText("");
                                    acModel.setEnabled(true);
                                    IsMakeValid = true;


                                    return;
                                }
                            }

                            acMake.setError("Invalid Make");
                            acMake.setFocusable(true);

                            acModel.setText("");
                            acModel.setEnabled(false);
                            IsMakeValid = false;
                        }

                    }

                }
            });


            //endregion

            //region  Model Listener

            acModel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    acModel.setError(null);
                    IsModelValid = true;
                    modelEntity = modelAdapter.getItem(position);
                    acMake.setSelection(0);
                    showDialog();
                    new ProductController(getActivity()).RTOProductListOnChangeVehicle(PARENT_PRODUCT_ID, PRODUCT_CODE, loginEntity.getUser_id(), acMake.getText().toString(), acModel.getText().toString(), RenewRcFragment.this);


                }
            });

            acModel.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.length() > 0) {
                        String str = acModel.getText().toString();

                        ListAdapter listAdapter = acModel.getAdapter();
                        if (listAdapter != null) {
                            for (int i = 0; i < listAdapter.getCount(); i++) {
                                String temp = listAdapter.getItem(i).toString().toUpperCase();
                                if (str.compareTo(temp) == 0) {
                                    acModel.setError(null);
                                    IsModelValid = true;
                                    return;
                                }
                            }

                            acModel.setError("Invalid Model");
                            acModel.setFocusable(true);
                            IsModelValid = false;

                        }

                    }

                }
            });


            //endregion
        }


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
            rlEditMakeModel.setVisibility(View.VISIBLE);
            lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_edit));
            btnGo.setVisibility(View.GONE);

        }else{
            rlEditMakeModel.setVisibility(View.GONE);
            etVehicle.setText("");
            etVehicle.setEnabled(true);
            lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_dashboard));

            btnGo.setVisibility(View.VISIBLE);

        }

    }

    private void bindMakeModel() {
        acMake.setText(userConstatntEntity.getMake());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                acMake.dismissDropDown();
            }
        });
        acModel.setText(userConstatntEntity.getModel());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                acModel.dismissDropDown();
            }
        });

        if (userConstatntEntity.getMake() != "") {
            IsMakeValid = true;
        }
        if (userConstatntEntity.getModel() != "") {
            IsModelValid = true;
        }
    }

    private void setScrollatBottom() {
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 1000);
    }


    private void setRtoTAT(RtoProductDisplayMainEntity rtoProd) {

//        if (rtoProd.getPrice() != null) {
//            txtCharges.setText("" + "\u20B9" + " " + rtoProd.getPrice());
//            AMOUNT = rtoProd.getPrice().trim();
//        }
//
//        if (rtoProd.getTAT() != null) {
//            lyTAT.setVisibility(View.VISIBLE);
//            txtTAT.setText("" + rtoProd.getTAT());
//        } else {
//            lyTAT.setVisibility(View.GONE);
//        }

        Glide.with(getActivity())
                .load(rtoProd.getProduct_logo())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(ivLogo);


    }

    private boolean validate() {
        if (!validateVehicle(etVehicle)) {
            return false;
        }

        if ((acMake.getText().toString().trim().length() == 0)) {
            acMake.requestFocus();
            acMake.setError("Enter Make");
            return false;
        }
        if (IsMakeValid == false) {
            acMake.requestFocus();
            acMake.setError("Enter Make");
            return false;
        }
        if (acModel.getVisibility() == View.VISIBLE) {

            if ((acModel.getText().toString().trim().length() == 0)) {
                acModel.requestFocus();
                acModel.setError("Enter Model");
                return false;
            }

            if (IsModelValid == false) {
                acModel.requestFocus();
                acModel.setError("Enter Model");
                return false;
            }
        }
        if ((etCity.getText().toString().trim().length() == 0)) {
            etCity.requestFocus();
            etCity.setError("Selct City");
            return false;
        }

        if (!validatePinCode(etPincode)) {

            return false;
        }
        if (PRODUCT_ID == 0) {

            Toast.makeText(getActivity(), getResources().getString(R.string.error_Msg), Toast.LENGTH_SHORT).show();

            return false;
        }
        if (productPriceEntity == null) {

            Toast.makeText(getActivity(), getResources().getString(R.string.error_Msg), Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;
    }

    private boolean validateCity() {
        if (!validateVehicle(etVehicle)) {
            return false;
        }

        if ((acMake.getText().toString().trim().length() == 0)) {
            acMake.requestFocus();
            acMake.setError("Enter Make");
            return false;
        }
        if (IsMakeValid == false) {
            acMake.requestFocus();
            acMake.setError("Enter Make");
            return false;
        }

        if (acModel.getVisibility() == View.VISIBLE) {

            if ((acModel.getText().toString().trim().length() == 0)) {
                acModel.requestFocus();
                acModel.setError("Enter Model");
                return false;
            }

            if (IsModelValid == false) {
                acModel.requestFocus();
                acModel.setError("Enter Model");
                return false;
            }
        }

        return true;
    }

    private void setMakeModelEdiatable() {


        btnGo.setVisibility(View.VISIBLE);
        etVehicle.setEnabled(true);
        acModel.setEnabled(false);
        acMake.setEnabled(false);
        acMake.setText("");
        acModel.setText("");
        etVehicle.setText("");
        lyMakeModel.setBackgroundColor(getResources().getColor(R.color.bg_dashboard));
        lyVehicle.setBackgroundColor(getResources().getColor(R.color.bg_dashboard));

    }

    //region common
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
        RCRequestEntity requestEntity = new RCRequestEntity();
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
        requestEntity.setMake(acMake.getText().toString());
        requestEntity.setModel(acModel.getText().toString());


        Bundle bundle = new Bundle();
        bundle.putString(Constants.REQUEST_TYPE, "1");
        bundle.putParcelable(Constants.PRODUCT_PAYMENT_REQUEST, requestEntity);


        getActivity().startActivity(new Intent(getActivity(), PaymentRazorActivity.class)
                .putExtra(Constants.PAYMENT_REQUEST_BUNDLE, bundle));


        getActivity().finish();


    }

    //endregion

    //endregion

    //region  Event

    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view, getActivity());
        switch (view.getId()) {


            case R.id.rlDoc:
                ((ProductMainActivity) getActivity()).getProducDoc(PRODUCT_ID);    //u
                break;


            case R.id.rlEditMakeModel:

                setMakeModelEdiatable();
                break;

            case R.id.btnBooked:

                if (validate() == false) {
                    return;
                } else {

                    saveData();
                }

                break;

            case R.id.btnGo:

                if (!isEmpty(etVehicle)) {
                    etVehicle.requestFocus();
                    etVehicle.setError("Enter Vehicle Number");
                    return;
                } else {

                    showDialog();
                    new RegisterController(getActivity()).getVechileDetails(etVehicle.getText().toString().trim(), this);

                }

                break;

            case R.id.etCity:

                if (validateCity()) {
                    setScrollatBottom();
                    startActivityForResult(new Intent(getActivity(), SearchCityActivity.class), Constants.SEARCH_CITY_CODE);
                }

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
                entity.setMake(acMake.getText().toString());
                entity.setModel(acMake.getText().toString());

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
        } else if (response instanceof RtoProductDisplayResponse) {
            if (response.getStatus_code() == 0) {

                if (((RtoProductDisplayResponse) response).getData().size() > 0) {


                    PRODUCT_ID = ((RtoProductDisplayResponse) response).getData().get(0).getProd_id();
                }
            }
        } else if (response instanceof VehicleMasterResponse) {
            if (response.getStatus_code() == 0) {
                List<MakeEntity> lstMake = ((VehicleMasterResponse) response).getData().getVehicleMasterResult().getMake();
                makeAdapter = new MakeAdapter(getActivity(), R.layout.activity_sign_up, R.id.lbl_name, lstMake);
                acMake.setAdapter(makeAdapter);
                bindMakeModel();

            }
        } else if (response instanceof FastLaneDataResponse) {

            // Toast.makeText(getActivity(),"Done",Toast.LENGTH_SHORT).show();
            FastLaneDataEntity fastLaneDataEntity = ((FastLaneDataResponse) response).getMasterData();

            try {
                if (fastLaneDataEntity != null) {

                    acModel.setEnabled(true);
                    acMake.setEnabled(true);

                    acMake.setOnItemClickListener(null);
                    acModel.setOnItemClickListener(null);
                    if (fastLaneDataEntity.getMake_Name() != "") {
                        acMake.setText("" + fastLaneDataEntity.getMake_Name());

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                acMake.dismissDropDown();
                            }
                        });

                    } else {
                        acMake.setText("");
                    }


                    if (fastLaneDataEntity.getModel_Name() != "") {
                        acModel.setText("" + fastLaneDataEntity.getModel_Name());
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                acModel.dismissDropDown();
                            }
                        });

                    } else {
                        acModel.setText("");
                    }


                    acModel.setError(null);
                    acMake.setError(null);

                    IsMakeValid = true;
                    IsModelValid = true;

                    setAutoComplete();

                } else {
                    resetMakeModel();

                }
            } catch (Exception ex) {
                resetMakeModel();

            }
        }


    }

    @Override
    public void OnFailure(Throwable t) {
        //  btnBooked.setEnabled(true);
        cancelDialog();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    //endregion

    private void resetMakeModel() {

        makeAdapter = new MakeAdapter(getActivity(), R.layout.activity_sign_up, R.id.lbl_name, prefManager.getMake());
        acMake.setAdapter(makeAdapter);
        setAutoComplete();

        acModel.setEnabled(true);
        acMake.setEnabled(true);

        acModel.setError(null);
        acMake.setError(null);

        acMake.setText("");
        acModel.setText("");
        IsMakeValid = false;
        IsModelValid = false;
    }

}
