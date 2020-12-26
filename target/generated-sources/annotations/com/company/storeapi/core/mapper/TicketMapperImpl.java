package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.Ticket;
import com.company.storeapi.model.payload.response.finance.CreditCapital;
import com.company.storeapi.model.payload.response.ticket.ResponseTicketDTO;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-24T18:15:26-0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.7 (Oracle Corporation)"
)
@Component
public class TicketMapperImpl extends TicketMapper {

    @Override
    public ResponseTicketDTO toTicketDto(Ticket ticket) {
        if ( ticket == null ) {
            return null;
        }

        ResponseTicketDTO responseTicketDTO = new ResponseTicketDTO();

        Set<CreditCapital> set = ticket.getCreditCapitals();
        if ( set != null ) {
            responseTicketDTO.setCreditCapitals( new HashSet<CreditCapital>( set ) );
        }
        responseTicketDTO.setCustomer( ticket.getCustomer() );
        responseTicketDTO.setOrder( ticket.getOrder() );
        responseTicketDTO.setId( ticket.getId() );
        if ( ticket.getCreateAt() != null ) {
            responseTicketDTO.setCreateAt( new SimpleDateFormat().format( ticket.getCreateAt() ) );
        }
        responseTicketDTO.setPaymentType( ticket.getPaymentType() );
        responseTicketDTO.setTicketStatus( ticket.getTicketStatus() );
        responseTicketDTO.setTicketCost( ticket.getTicketCost() );
        responseTicketDTO.setTicketCostWithoutIVA( ticket.getTicketCostWithoutIVA() );
        responseTicketDTO.setOutstandingBalance( ticket.getOutstandingBalance() );
        responseTicketDTO.setCashPayment( ticket.getCashPayment() );
        responseTicketDTO.setTransactionPayment( ticket.getTransactionPayment() );
        responseTicketDTO.setCreditPayment( ticket.getCreditPayment() );
        responseTicketDTO.setCashRegister( ticket.isCashRegister() );

        return responseTicketDTO;
    }
}
