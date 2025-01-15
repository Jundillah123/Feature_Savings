package Service.com.Saving.Account.services.impl;

import Service.com.Saving.Account.dto.TJournalLedgerRequest;
import Service.com.Saving.Account.dto.TJournalLedgerResponse;
import Service.com.Saving.Account.dto.TSavingAccountDetailRequest;
import Service.com.Saving.Account.dto.TSavingAccountDetailResponse;
import Service.com.Saving.Account.entity.TJournalLedger;
import Service.com.Saving.Account.entity.TSavingAccount;
import Service.com.Saving.Account.entity.TSavingAccountDetail;
import Service.com.Saving.Account.enums.CoaCode;
import Service.com.Saving.Account.enums.Mutation;
import Service.com.Saving.Account.enums.Status;
import Service.com.Saving.Account.enums.TransactionTypeEnum;
import Service.com.Saving.Account.exception.BusinesException;
import Service.com.Saving.Account.repository.*;
import Service.com.Saving.Account.services.TSavingAccountDetailService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
//Ketika Tabungan Credit di kurang
//Debit di tambah

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

    @Autowired
    TJournalLedgerRepository tJournalLedgerRepository;

    @Autowired
    TransactionTypeRepository transactionTypeRepository;

    @Autowired
    MCoaRepository mCoaRepository;


    @SneakyThrows
    @Transactional
    @Override
    public TSavingAccountDetailResponse tabungan(TSavingAccountDetailRequest tSavingAccountDetailRequest, TJournalLedger tJournalLedger, TJournalLedgerRequest tJournalLedgerRequest) {

        TSavingAccount sourceAccount = tSavingAccountRepository
                .findByAccountNumber(tSavingAccountDetailRequest.getSourceAccountNumber())
                .orElseThrow(() -> new BusinesException("Account Pengirim Tidak Ditemukan"));

        TSavingAccount destAccount = tSavingAccountRepository
                .findByAccountNumber(tSavingAccountDetailRequest.getDestAccountNumber())
                .orElseThrow(() -> new BusinesException("Account Penerima Tidak Ditemukan"));


        BigDecimal minimalBalance = sourceAccount.getMSaving().getMinimalBalance();
        BigDecimal currentBalance = sourceAccount.getCurrentBalance();
        BigDecimal availableBalance = currentBalance.subtract(minimalBalance);

        if (tSavingAccountDetailRequest.getNominal().compareTo(availableBalance) > 0) {
            throw new BusinesException("Saldo Tidak Cukup Untuk Di Transfer");
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


        TJournalLedger tJournalLedgerTransaction = TJournalLedger.builder()
                .txCode(transactionTypeRepository.getReferenceById(TransactionTypeEnum.TF.getDescription()))
                .coaCode(mCoaRepository.getReferenceById(CoaCode.KASTABUNGAN.getKey()))
                .nominal(endBalance)
                .mutation(Mutation.DEBIT.getDescription())
                .description(tSavingAccountDetailRequest.getDescriptionn())
                .createdAt(Timestamp.from(Instant.now()))
                .build();

        TJournalLedger tJournalLedger1 = TJournalLedger.builder()
                .txCode(transactionTypeRepository.getReferenceById(TransactionTypeEnum.TF.getDescription()))
                .coaCode(mCoaRepository.getReferenceById(CoaCode.KASTABUNGAN.getKey()))
                .nominal(balance)
                .mutation(Mutation.CREDIT.getDescription())
                .description(tSavingAccountDetailRequest.getDescriptionn())
                .createdAt(Timestamp.from(Instant.now()))
                .build();

        tJournalLedgerRepository.save(tJournalLedger1);
        log.info("Journal Ledger Credit Success");

        tJournalLedgerRepository.save(tJournalLedgerTransaction);
        log.info("Journal Ledger Debit Success");

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

        return buildResponseForTSavingAccountDetail(sourceTransaction, tJournalLedgerTransaction);
    }

    @Override
    public List<TSavingAccountDetailResponse> read(String tSavingAccountDetail) {
        List<TSavingAccountDetail> tSavingAccountDetails = tSavingAccountDetailRepository.findAll();
        List<TSavingAccountDetailResponse> tSavingAccountResponses = tSavingAccountDetails.stream()
                .map(data -> buildResponseGet(data)).collect(Collectors.toList());
        return tSavingAccountResponses;
    }

    @SneakyThrows
    @Override
    public Optional<TSavingAccountDetailResponse> refundTransaction(String savingAccountDetail) {
        TSavingAccountDetail transaksiRefund = tSavingAccountDetailRepository.findById(savingAccountDetail)
                .orElseThrow(() -> new BusinesException("Transaksi Not Found"));

        if (transaksiRefund.getStatusIdDetail().equals(Status.COMPLETED)) {
            throw new BusinesException("Transaksi Tidak Di Bisa Di Batalkan");
        }
        BigDecimal refundAmount = transaksiRefund.getNominal();
        TSavingAccount sourchAccount = transaksiRefund.getSavingAccountId();
        sourchAccount.setCurrentBalance(sourchAccount.getCurrentBalance().add(refundAmount));
        tSavingAccountRepository.save(sourchAccount);

        TSavingAccountDetailResponse tSavingAccountDetailResponse = TSavingAccountDetailResponse.builder()
                .savingAccountDetail(transaksiRefund.getSavingAccountDetail())
                .destAccountNumber(sourchAccount.getAccountNumber())
                .nominal(refundAmount)
                .statusIdDetail(rStatusRespository.getReferenceById(Status.COMPLETED.getKey()))
                .build();
        return Optional.of(tSavingAccountDetailResponse);
    }


    private TSavingAccountDetailResponse buildResponseGet(TSavingAccountDetail tSavingAccountDetail) {
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

    private TSavingAccountDetailResponse buildResponseForTSavingAccountDetail(TSavingAccountDetail tSavingAccountDetail, TJournalLedger tJournalLedger) {
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
        TJournalLedgerResponse tJournalLedgerResponse = TJournalLedgerResponse.builder()
                .id(tJournalLedger.getId())
                .txCode(tJournalLedger.getTxCode())
                .coaCode(tJournalLedger.getCoaCode())
                .mutation(tJournalLedger.getMutation())
                .nominal(tJournalLedger.getNominal())
                .description(tJournalLedger.getDescription())
                .createdAt(tJournalLedger.getCreatedAt())
                .build();

        return tSavingAccountDetailResponse;
    }
}
