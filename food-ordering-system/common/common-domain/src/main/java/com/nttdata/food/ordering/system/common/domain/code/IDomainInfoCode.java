package com.nttdata.food.ordering.system.common.domain.code;

import java.io.Serializable;

public interface IDomainInfoCode extends Serializable {

    String name();

    String getCode();

    String getMessage();
}
