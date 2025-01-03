package Service.com.Saving.Account.services;

import Service.com.Saving.Account.dto.TSavingAccountDetailRequest;
import Service.com.Saving.Account.dto.TSavingAccountDetailResponse;
import Service.com.Saving.Account.entity.TSavingAccountDetail;

import java.util.List;


public interface TSavingAccountDetailService {
    TSavingAccountDetailResponse tabungan(TSavingAccountDetailRequest tSavingAccountDetailRequest);

    List<TSavingAccountDetailResponse> read (String tSavingAccountDetail);
}
