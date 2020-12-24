package com.company.storeapi.model.payload.response.finance;

import lombok.Data;

@Data
public class ResponseCashRegisterDTO {

    private double dailyCashBase;
    private double dailyCashSales;
    private double dailyTransactionsSales;
    private double dailyCreditSales;
    private double totalSales;
    private double moneyOut;
    //private double discounts;
    private double cashCreditCapital;
    private double transactionCreditCapital;
    private String createAt;
}
