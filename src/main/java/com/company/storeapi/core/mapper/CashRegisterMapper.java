package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.finance.CashBase;
import com.company.storeapi.model.entity.finance.CashRegisterDaily;
import com.company.storeapi.model.payload.response.finance.ResponseCashBase;
import com.company.storeapi.model.payload.response.finance.ResponseCashRegisterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class CashRegisterMapper {
    public abstract ResponseCashRegisterDTO DtoChasRegisterDocument(CashRegisterDaily cashRegister);

    public abstract ResponseCashBase getResponseCashBase (CashBase cashBase);

}
