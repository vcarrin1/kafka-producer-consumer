package com.vcarrin87.kafka_producer_consumer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionMessage {

    private Long transactionId;
    protected Event event;
    private Double amount;
    private Status transactionStatus;

    public enum Event {
        WITHDRAW, DEPOSIT
    }
    public enum Status {
        SUBMITTED, STARTED, PENDING, COMPLETED, FAILED
    }
}
