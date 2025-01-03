package Service.com.Saving.Account.services.impl;

import Service.com.Saving.Account.dto.TSavingAccountDetailRequest;
import Service.com.Saving.Account.dto.TSavingAccountDetailResponse;
import Service.com.Saving.Account.dto.TSavingAccountResponse;
import Service.com.Saving.Account.entity.RStatus;
import Service.com.Saving.Account.entity.TSavingAccount;
import Service.com.Saving.Account.entity.TSavingAccountDetail;
import Service.com.Saving.Account.enums.Mutation;
import Service.com.Saving.Account.enums.Status;
import Service.com.Saving.Account.repository.RStatusRespository;
import Service.com.Saving.Account.repository.TSavingAccountDetailRepository;
import Service.com.Saving.Account.repository.TSavingAccountRepository;
import Service.com.Saving.Account.services.TSavingAccountDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
public class TSavingAccountDetailServiceImpl implements TSavingAccountDetailService {

    @Autowired
    TSavingAccountDetailRepository tSavingAccountDetailRepository;

    @Autowired
    TSavingAccountRepository tSavingAccountRepository;

    @Autowired
    RStatusRespository rStatusRespository;


    @Transactional
    @Override
    public TSavingAccountDetailResponse tabungan(TSavingAccountDetailRequest tSavingAccountDetailRequest) {
        TSavingAccount sourceAccount = tSavingAccountRepository
                .findByAccountNumber(tSavingAccountDetailRequest.getSourceAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account Pengirim Tidak Ditemukan"));

        TSavingAccount destAccount = tSavingAccountRepository
                .findByAccountNumber(tSavingAccountDetailRequest.getDestAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account Penerima Tidak Ditemukan"));


        BigDecimal minimalBalance = sourceAccount.getMSaving().getMinimalBalance();
        BigDecimal currentBalance = sourceAccount.getCurrentBalance();
        BigDecimal availableBalance = currentBalance.subtract(minimalBalance);

        if (tSavingAccountDetailRequest.getNominal().compareTo(availableBalance) > 0) {
            throw new RuntimeException("Saldo Tidak Cukup Untuk Di Transfer");
        }
        String referenceCode = UUID.randomUUID().toString();

        String mutation = (tSavingAccountDetailRequest.getNominal().compareTo(BigDecimal.ZERO) > 0) ? Mutation.CREDIT.getDescription() : Mutation.DEBIT.getDescription();
        BigDecimal balance = (mutation.equals(Mutation.DEBIT.getDescription())) ? currentBalance : destAccount.getCurrentBalance();
        BigDecimal endBalance = balance.subtract(tSavingAccountDetailRequest.getNominal());


        BigDecimal newSourceBalance = currentBalance.subtract(tSavingAccountDetailRequest.getNominal());
        TSavingAccountDetail sourceTransaction = TSavingAccountDetail.builder()
                .savingAccountId(sourceAccount)
                .nominal(tSavingAccountDetailRequest.getNominal())
                .mutation(Mutation.DEBIT.getDescription())
                .destAccountNumber(tSavingAccountDetailRequest.getSourceAccountNumber())
                .balance(balance)
                .endBalance(endBalance)
                .statusIdDetail(rStatusRespository.getReferenceById(Status.ACTIVE.getKey()))
                .referenceCode(referenceCode)
                .createdAt(Timestamp.from(Instant.now()))
                .build();


        BigDecimal newDestBalance = destAccount.getCurrentBalance().add(tSavingAccountDetailRequest.getNominal());
        TSavingAccountDetail destTransaction = TSavingAccountDetail.builder()
                .savingAccountId(destAccount)
                .nominal(tSavingAccountDetailRequest.getNominal())
                .mutation(Mutation.CREDIT.getDescription())
                .destAccountNumber(tSavingAccountDetailRequest.getDestAccountNumber())
                .balance(newDestBalance)
                .endBalance(newDestBalance)
                .statusIdDetail(rStatusRespository.getReferenceById(Status.ACTIVE.getKey()))
                .referenceCode(referenceCode)
                .createdAt(Timestamp.from(Instant.now()))
                .build();

        tSavingAccountDetailRepository.save(sourceTransaction);
        log.info("Transaksi Pengirim Success ");
        tSavingAccountDetailRepository.save(destTransaction);
        log.info("Transaksi Penerima Success");

        sourceAccount.setCurrentBalance(newSourceBalance);
        destAccount.setCurrentBalance(newDestBalance);
        tSavingAccountRepository.save(sourceAccount);
        log.info("Account Pengirim success");
        tSavingAccountRepository.save(destAccount);
        log.info("Account Penerima success");


        return buildResponseForTSavingAccountDetail(sourceTransaction);
    }

    @Override
    public List<TSavingAccountDetailResponse> read(String tSavingAccountDetail) {
       List<TSavingAccountDetail> tSavingAccountDetails = tSavingAccountDetailRepository.findAll();
       List<TSavingAccountDetailResponse> tSavingAccountResponses = tSavingAccountDetails.stream()
               .map(data -> buildResponseGet(data)).collect(Collectors.toList());
        return tSavingAccountResponses;
    }

    private TSavingAccountDetailResponse buildResponseGet (TSavingAccountDetail tSavingAccountDetail) {
        TSavingAccountDetailResponse response = TSavingAccountDetailResponse.builder()
                .savingAccountId(tSavingAccountDetail.getSavingAccountId())
                .savingAccountDetail(tSavingAccountDetail.getSavingAccountDetail())
                .nominal(tSavingAccountDetail.getNominal())
                .mutation(tSavingAccountDetail.getMutation())
                .destAccountNumber(tSavingAccountDetail.getDestAccountNumber())
                .endBalance(tSavingAccountDetail.getEndBalance())
                .balance(tSavingAccountDetail.getBalance())
                .referenceCode(tSavingAccountDetail.getReferenceCode())
                .createdAt(tSavingAccountDetail.getCreatedAt())
                .statusIdDetail(tSavingAccountDetail.getStatusIdDetail())
                .build();
        return response;
    }



    private TSavingAccountDetailResponse buildResponseForTSavingAccountDetail(TSavingAccountDetail tSavingAccountDetail) {
        TSavingAccountDetailResponse tSavingAccountDetailResponse = TSavingAccountDetailResponse.builder()
                .savingAccountDetail(tSavingAccountDetail.getSavingAccountDetail())
                .savingAccountId(tSavingAccountDetail.getSavingAccountId())
                .nominal(tSavingAccountDetail.getNominal())
                .destAccountNumber(tSavingAccountDetail.getDestAccountNumber())
                .endBalance(tSavingAccountDetail.getEndBalance())
                .balance(tSavingAccountDetail.getBalance())
                .createdAt(tSavingAccountDetail.getCreatedAt())
                .mutation(tSavingAccountDetail.getMutation())
                .statusIdDetail(tSavingAccountDetail.getStatusIdDetail())
                .build();

        return tSavingAccountDetailResponse;
    }
}
