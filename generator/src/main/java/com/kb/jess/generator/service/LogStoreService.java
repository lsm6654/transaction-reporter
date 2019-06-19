package com.kb.jess.generator.service;

import com.kb.jess.core.model.TransactionBase;

import java.util.List;

/**
 * 로그 저장소는 달라질 수 있으므로 추상화.
 */
public interface LogStoreService {
    void sendToRepository(List<TransactionBase> transactions);
    void close();
}
