package com.vaultpay.domain.ports;

import com.vaultpay.domain.event.TransactionDebitedEvent;

public interface TransactionPublisherPort {
    void publishTransactionDebited(TransactionDebitedEvent event);
}
