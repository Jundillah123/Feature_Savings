package Service.com.Saving.Account.controller;

import Service.com.Saving.Account.dto.TSavingAccountDetailRequest;
import Service.com.Saving.Account.dto.TSavingAccountDetailResponse;
import Service.com.Saving.Account.entity.TSavingAccountDetail;
import Service.com.Saving.Account.services.TSavingAccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tabungan")
public class TSavingAccountDetailController {

    @Autowired
    TSavingAccountDetailService tSavingAccountDetailService;

    @GetMapping
    List<TSavingAccountDetailResponse> readsTsavingAccountDetail(@Param("savingAccountId") String tSavingAccountDetail){
        return tSavingAccountDetailService.read(tSavingAccountDetail);
    }

    @PostMapping
    public TSavingAccountDetailResponse creatTabungan (@RequestBody TSavingAccountDetailRequest tSavingAccountDetailRequest){
        return tSavingAccountDetailService.tabungan(tSavingAccountDetailRequest);
    }
}
