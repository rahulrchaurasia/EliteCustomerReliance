package com.rb.eliterl.core.controller.rto_service;

import com.rb.eliterl.core.IResponseSubcriber;
import com.rb.eliterl.core.requestmodel.AdditionHypothecationRequestEntity;
import com.rb.eliterl.core.requestmodel.AddressEndorsementRCRequestEntity;
import com.rb.eliterl.core.requestmodel.AssistanceObtainingRequestEntity;
import com.rb.eliterl.core.requestmodel.DriverDLVerificationRequestEntity;
import com.rb.eliterl.core.requestmodel.PaperToSmartCardRequestEntity;
import com.rb.eliterl.core.requestmodel.RCRequestEntity;
import com.rb.eliterl.core.requestmodel.TransferOwnershipRequestEntity;
import com.rb.eliterl.core.requestmodel.VehicleRegCertificateRequestEntity;

/**
 * Created by Rahul  on 14-12-2018.
 */

public interface IRTO {

    void saveRCService1(RCRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveAssistanceObtaining(AssistanceObtainingRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveAdditionHypothecation(AdditionHypothecationRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveTransferOwnership(TransferOwnershipRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveDriverDLVerification(DriverDLVerificationRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveAddressEndorsementRC(AddressEndorsementRCRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void savePaperSmartCard(PaperToSmartCardRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void saveVehicleRegCertificate(VehicleRegCertificateRequestEntity entity, IResponseSubcriber iResponseSubcriber);


}
