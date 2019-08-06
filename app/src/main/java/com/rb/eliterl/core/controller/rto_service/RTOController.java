package com.rb.eliterl.core.controller.rto_service;

import android.content.Context;

import com.rb.eliterl.core.IResponseSubcriber;
import com.rb.eliterl.core.requestbuilder.RtoRequestBuilder;
import com.rb.eliterl.core.requestmodel.AdditionHypothecationRequestEntity;
import com.rb.eliterl.core.requestmodel.AddressEndorsementRCRequestEntity;
import com.rb.eliterl.core.requestmodel.AssistanceObtainingRequestEntity;
import com.rb.eliterl.core.requestmodel.DriverDLVerificationRequestEntity;
import com.rb.eliterl.core.requestmodel.PaperToSmartCardRequestEntity;
import com.rb.eliterl.core.requestmodel.RCRequestEntity;
import com.rb.eliterl.core.requestmodel.TransferOwnershipRequestEntity;
import com.rb.eliterl.core.requestmodel.VehicleRegCertificateRequestEntity;
import com.rb.eliterl.core.response.ProvideClaimAssResponse;
import com.rb.eliterl.splash.PrefManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public class RTOController implements IRTO {


    RtoRequestBuilder.RtoNetworkService rtoNetworkService;
    Context mContext;
    PrefManager prefManager;
    IResponseSubcriber iResponseSubcriber;

    public RTOController(Context context) {
        rtoNetworkService = new RtoRequestBuilder().getService();
        mContext = context;
        prefManager = new PrefManager(mContext);
    }

    @Override
    public void saveVehicleRegCertificate(VehicleRegCertificateRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {
        rtoNetworkService.saveVehicleRegCertificate(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(""));
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }

            }
        });
    }

    @Override
    public void saveTransferOwnership(TransferOwnershipRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {
        rtoNetworkService.saveTransferOwnership(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(""));
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }

            }
        });
    }

    @Override
    public void savePaperSmartCard(PaperToSmartCardRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {
        rtoNetworkService.savePaperSmartCard(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(""));
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }

            }
        });
    }

    @Override
    public void saveDriverDLVerification(DriverDLVerificationRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        rtoNetworkService.saveDriverDLVerification(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(""));
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }

            }
        });
    }

    @Override
    public void saveAddressEndorsementRC(AddressEndorsementRCRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        rtoNetworkService.saveAddressEndorsementRC(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(""));
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }

            }
        });
    }

    @Override
    public void saveAdditionHypothecation(AdditionHypothecationRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        rtoNetworkService.saveAdditionHypothecation(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(""));
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }

            }
        });
    }

    @Override
    public void saveRCService1(RCRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        rtoNetworkService.saveRCservice1(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(""));
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }

            }
        });
    }

    @Override
    public void saveAssistanceObtaining(AssistanceObtainingRequestEntity entity, final IResponseSubcriber iResponseSubcriber) {

        rtoNetworkService.saveAssistObtaining(entity).enqueue(new Callback<ProvideClaimAssResponse>() {
            @Override
            public void onResponse(Call<ProvideClaimAssResponse> call, Response<ProvideClaimAssResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus_code() == 0) {
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(""));
                }
            }

            @Override
            public void onFailure(Call<ProvideClaimAssResponse> call, Throwable t) {

                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }

            }
        });

    }
}
