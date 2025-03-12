package com.nttdata.food.ordering.system.payment.service.dataaccess.creditentry.mapper;

import com.nttdata.food.ordering.system.common.domain.valueobject.CustomerId;
import com.nttdata.food.ordering.system.common.domain.valueobject.Money;
import com.nttdata.food.ordering.system.payment.service.dataaccess.creditentry.entity.CreditEntryEntity;
import com.nttdata.food.ordering.system.payment.service.domain.entity.CreditEntry;
import com.nttdata.food.ordering.system.payment.service.domain.valueobject.CreditEntryId;
import org.springframework.stereotype.Component;

@Component
public class CreditEntryDataAccessMapper {

    public CreditEntry creditEntryEntityToCreditEntry(CreditEntryEntity creditEntryEntity) {
        return CreditEntry.builder()
                .creditEntryId(new CreditEntryId(creditEntryEntity.getId()))
                .customerId(new CustomerId(creditEntryEntity.getCustomerId()))
                .totalCreditAmount(new Money(creditEntryEntity.getTotalCreditAmount()))
                .build();
    }

    public CreditEntryEntity creditEntryToCreditEntryEntity(CreditEntry creditEntry) {
        return CreditEntryEntity.builder()
                .id(creditEntry.getId().getValue())
                .customerId(creditEntry.getCustomerId().getValue())
                .totalCreditAmount(creditEntry.getTotalCreditAmount().getAmount())
                .build();
    }

}
