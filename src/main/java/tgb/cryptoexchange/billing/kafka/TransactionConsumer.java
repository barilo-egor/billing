package tgb.cryptoexchange.billing.kafka;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import tgb.cryptoexchange.billing.dto.TransactionDTO;
import tgb.cryptoexchange.billing.service.TransactionService;

@Slf4j
@Service
@Profile("!kafka-disabled")
public class TransactionConsumer {

    private final TransactionService transactionService;

    public TransactionConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "${kafka.topic.billing.transaction}", groupId = "${kafka.group-id}",
            containerFactory = "transactionKafkaListenerContainerFactory")
    public void getTransaction(@Valid @Payload TransactionDTO dto, @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key) {
        log.trace("Получена transaction. Key={}, value={}", key, dto);
        try {
            transactionService.save(dto);
        } catch (Exception e) {
            log.error("Ошибка при попытке сохранения transaction {}", dto.getId());
        }
    }

}
