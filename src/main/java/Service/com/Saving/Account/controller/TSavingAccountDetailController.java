package Service.com.Saving.Account.controller;

import Service.com.Saving.Account.dto.TJournalLedgerRequest;
import Service.com.Saving.Account.dto.TSavingAccountDetailRequest;
import Service.com.Saving.Account.dto.TSavingAccountDetailResponse;
import Service.com.Saving.Account.entity.TJournalLedger;
import Service.com.Saving.Account.services.TJournalLedgerService;
import Service.com.Saving.Account.services.TSavingAccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tabungan")
public class TSavingAccountDetailController {

    @Autowired
    TSavingAccountDetailService tSavingAccountDetailService;

    @Autowired
    TJournalLedgerService tJournalLedgerService;


    @GetMapping
    public List<TSavingAccountDetailResponse> readsTsavingAccountDetail(@RequestParam(value = "savingAccountId", required = false) String tSavingAccountDetail) {
        return tSavingAccountDetailService.read(tSavingAccountDetail);
    }


    @GetMapping("/list")
    public String showTabunganList(Model model) {
        List<TSavingAccountDetailResponse> tabunganList = tSavingAccountDetailService.read(null);
        model.addAttribute("tabunganList", tabunganList);
        return "tabungan-list"; // File HTML untuk menampilkan daftar transaksi
    }


    @GetMapping("/add")
    public String showAddTabunganPage() {
        return "tabungan-add"; // File HTML untuk menambah transaksi
    }


    @PostMapping
    public TSavingAccountDetailResponse creatTabungan(@RequestBody TSavingAccountDetailRequest tSavingAccountDetailRequest, TJournalLedger tJournalLedger, TJournalLedgerRequest tJournalLedgerRequest) {
        return tSavingAccountDetailService.tabungan(tSavingAccountDetailRequest, tJournalLedger, tJournalLedgerRequest);
    }

    @PostMapping("/savingAccountDetail")
    public Optional<TSavingAccountDetailResponse> refund(@PathVariable String savingAccountDetail) {
        return tSavingAccountDetailService.refundTransaction(savingAccountDetail);
    }
}
