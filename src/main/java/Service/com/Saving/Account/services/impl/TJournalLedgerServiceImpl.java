package Service.com.Saving.Account.services.impl;


import Service.com.Saving.Account.dto.TJournalLedgerRequest;
import Service.com.Saving.Account.dto.TJournalLedgerResponse;
import Service.com.Saving.Account.entity.MCoa;
import Service.com.Saving.Account.entity.TJournalLedger;
import Service.com.Saving.Account.entity.TransactionType;
import Service.com.Saving.Account.enums.CoaCode;
import Service.com.Saving.Account.enums.Mutation;
import Service.com.Saving.Account.enums.TransactionTypeEnum;
import Service.com.Saving.Account.exception.BusinesException;
import Service.com.Saving.Account.repository.*;
import Service.com.Saving.Account.services.TJournalLedgerService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;


@Service
@Slf4j
@Transactional
public class TJournalLedgerServiceImpl implements TJournalLedgerService {
    @Autowired
    MCoaRepository mCoaRepository;

    @Autowired
    TransactionTypeRepository transactionTypeRepository;

    @Autowired
    MSavingTransactionTypeRepository mSavingTransactionTypeRepository;

    @Autowired
    MSavingTxTypeCoaRepository mSavingTxTypeCoaRepository;

    @Autowired
    TJournalLedgerRepository journalLedgerRepository;





    @SneakyThrows
    @Override
    public TJournalLedgerResponse createe(TJournalLedgerRequest tJournalLedgerRequest) {
        TJournalLedger tJournalLedger = TJournalLedger.builder()
//                .txCode(Mutation.TRANSFER.getDescription())
//                .coaCode(Mutation.TRANSFER.getDescription())
                .mutation(Mutation.CREDIT.getDescription())
                .nominal(tJournalLedgerRequest.getNominal())
                .description(tJournalLedgerRequest.getDescription())
                .createdAt(Timestamp.from(Instant.now()))
                .build();
        journalLedgerRepository.save(tJournalLedger);
        log.info("Create tJournalLedger Success ");
        return buildResponseTJurnal(tJournalLedger);
    }

    private  TJournalLedgerResponse buildResponseTJurnal(TJournalLedger tJournalLedger){
        TJournalLedgerResponse response = TJournalLedgerResponse.builder()
                .id(tJournalLedger.getId())
                .coaCode(tJournalLedger.getCoaCode())
                .txCode(tJournalLedger.getTxCode())
                .nominal(tJournalLedger.getNominal())
                .description(tJournalLedger.getDescription())
                .createdAt(tJournalLedger.getCreatedAt())
                .build();
        return response;
    }
}
