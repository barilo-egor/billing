package tgb.cryptoexchange.billing.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tgb.cryptoexchange.billing.dto.TransactionDTO;
import tgb.cryptoexchange.billing.entity.Transaction;
import tgb.cryptoexchange.billing.mapper.TransactionMapper;
import tgb.cryptoexchange.billing.repository.TransactionRepository;
import tgb.cryptoexchange.billing.utils.PageableUtils;

import java.util.List;

@Service
@Slf4j
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public void create(TransactionDTO transactionDTO) {
        log.debug("Вызов create для transaction: {}", transactionDTO);
        if (transactionRepository.existsById(transactionDTO.getId())) {
            log.warn("Transaction с id {} уже существует!", transactionDTO.getId());
            return;
        }
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transactionRepository.save(transaction);
        log.debug("Успешно создана transaction: {}", transaction.getId());
    }

    public void save(TransactionDTO transactionDTO) {
        log.debug("Вызов save для transaction: {}", transactionDTO);
        Transaction transaction = transactionRepository.save(transactionMapper.toEntity(transactionDTO));
        log.debug("Успешно сохранена transaction: {}", transaction.getId());
    }

    @Transactional(readOnly = true)
    public Page<TransactionDTO> findTransactions(Specification<Transaction> spec, int page, int size, List<String> sorters) {
        Pageable pageable = PageableUtils.createPageable(page, size, sorters);
        return transactionRepository.findAll(spec, pageable).map(transactionMapper::entityToDTO);
    }


}
