package Service.com.Saving.Account.services;

import Service.com.Saving.Account.dto.TJournalLedgerRequest;
import Service.com.Saving.Account.dto.TSavingAccountDetailRequest;
import Service.com.Saving.Account.dto.TSavingAccountDetailResponse;
import Service.com.Saving.Account.entity.TJournalLedger;
import Service.com.Saving.Account.entity.TSavingAccountDetail;

import java.util.List;
import java.util.Optional;


public interface TSavingAccountDetailService {
    TSavingAccountDetailResponse tabungan(TSavingAccountDetailRequest tSavingAccountDetailRequest, TJournalLedger tJournalLedger, TJournalLedgerRequest tJournalLedgerRequest);

    List<TSavingAccountDetailResponse> read (String tSavingAccountDetail);

//    String deleted (String savingAccountDetailId);
    Optional<TSavingAccountDetailResponse> refundTransaction (String savingAccountDetail);
}
