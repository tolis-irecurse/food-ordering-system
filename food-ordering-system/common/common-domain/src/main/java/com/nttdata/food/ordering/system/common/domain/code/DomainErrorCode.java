package com.nttdata.food.ordering.system.common.domain.code;

public enum DomainErrorCode implements IDomainErrorCode {

    ORDER_NOT_FOUND("0000", "Order with id %s not found!"),
    ORDER_NOT_IN_CORRECT_STATE_FOR_PAYMENT("0010", "Order is not in correct state for payment operation!"),
    ORDER_NOT_IN_CORRECT_STATE_FOR_APPROVAL("0020", "Order is not in correct state for approval operation!"),
    ORDER_NOT_IN_CORRECT_STATE_FOR_INITCANCEL("0030", "Order is not in correct state for initCancel operation!"),
    ORDER_NOT_IN_CORRECT_STATE_FOR_CANCEL("0040", "Order is not in correct state for cancel operation!"),
    ORDER_NOT_IN_CORRECT_STATE_FOR_INIT("0050", "Order is not in correct state for initialization!"),
    ORDER_TOTAL_PRICE_NGT_ZERO("0060", "Total price must be greater than zero!"),
    ORDER_TOTAL_NE_TO_ORDER_ITEMS_TOTAL("0070", "Total price = %s is not equal to sum of order items = %s!"),
    ORDER_ITEM_PRICE_NOT_VALID_FOR_PRODUCT("0080", "Order item price = %s is not valid for product %s!"),
    ORDER_COULD_NOT_SAVE("0090", "Order with id %s could not be saved!"),
    RESTAURANT_NOT_FOUND("0500", "Restaurant with id %s not found!"),
    RESTAURANT_NOT_ACTIVE("0510", "Restaurant with id %s is currently not active!"),
    CUSTOMER_NOT_FOUND("1000", "Customer with id %s not found!");

    private final String code;
    private final String message;

    DomainErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
