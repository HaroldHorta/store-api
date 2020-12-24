package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.finance.CashBase;
import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import com.company.storeapi.model.payload.response.finance.ResponseCashBase;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import java.text.SimpleDateFormat;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-24T15:28:47-0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class CashRegisterMapperImpl extends CashRegisterMapper {

    @Override
    public ResponseCashRegisterDTO DtoChasRegisterDocument(CashRegisterDaily cashRegister) {
        if ( cashRegister == null ) {
            return null;
        }

        ResponseCashRegisterDTO responseCashRegisterDTO = new ResponseCashRegisterDTO();

        responseCashRegisterDTO.setDailyCashBase( cashRegister.getDailyCashBase() );
        responseCashRegisterDTO.setDailyCashSales( cashRegister.getDailyCashSales() );
        responseCashRegisterDTO.setDailyTransactionsSales( cashRegister.getDailyTransactionsSales() );
        responseCashRegisterDTO.setDailyCreditSales( cashRegister.getDailyCreditSales() );
        responseCashRegisterDTO.setTotalSales( cashRegister.getTotalSales() );
        responseCashRegisterDTO.setMoneyOut( cashRegister.getMoneyOut() );
        responseCashRegisterDTO.setCashCreditCapital( cashRegister.getCashCreditCapital() );
        responseCashRegisterDTO.setTransactionCreditCapital( cashRegister.getTransactionCreditCapital() );
        if ( cashRegister.getCreateAt() != null ) {
            responseCashRegisterDTO.setCreateAt( new SimpleDateFormat().format( cashRegister.getCreateAt() ) );
        }

        return responseCashRegisterDTO;
    }

    @Override
    public ResponseCashBase getResponseCashBase(CashBase cashBase) {
        if ( cashBase == null ) {
            return null;
        }

        ResponseCashBase responseCashBase = new ResponseCashBase();

        responseCashBase.setDailyCashBase( cashBase.getDailyCashBase() );
        if ( cashBase.getCreateAt() != null ) {
            responseCashBase.setCreateAt( new SimpleDateFormat().format( cashBase.getCreateAt() ) );
        }

        return responseCashBase;
    }
}
