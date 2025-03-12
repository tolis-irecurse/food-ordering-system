package com.nttdata.food.ordering.system.payment.service.domain.ports.output.repository;

import com.nttdata.food.ordering.system.common.domain.valueobject.CustomerId;
import com.nttdata.food.ordering.system.payment.service.domain.entity.CreditEntry;

import java.util.Optional;

public interface CreditEntryRepository {

    CreditEntry save(CreditEntry creditEntry);

    Optional<CreditEntry> findByCustomerId(CustomerId customerId);
}
