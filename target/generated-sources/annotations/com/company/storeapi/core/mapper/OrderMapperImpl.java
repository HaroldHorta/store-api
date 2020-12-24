package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.Order;
import com.company.storeapi.model.payload.request.order.RequestAddOrderDTO;
import com.company.storeapi.model.payload.request.order.RequestUpdateOrderDTO;
import com.company.storeapi.model.payload.request.product.RequestOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.order.ResponseOrderDTO;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-24T15:28:47-0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl extends OrderMapper {

    @Override
    public ResponseOrderDTO toOrderDto(Order order) {
        if ( order == null ) {
            return null;
        }

        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();

        Set<ResponseOrderProductItemsDTO> set = order.getProducts();
        if ( set != null ) {
            responseOrderDTO.setProducts( new ArrayList<ResponseOrderProductItemsDTO>( set ) );
        }
        responseOrderDTO.setId( order.getId() );
        responseOrderDTO.setOrderStatus( order.getOrderStatus() );

        return responseOrderDTO;
    }

    @Override
    public RequestAddOrderDTO toRequestAdd(RequestUpdateOrderDTO requestUpdateOrderDTO) {
        if ( requestUpdateOrderDTO == null ) {
            return null;
        }

        RequestAddOrderDTO requestAddOrderDTO = new RequestAddOrderDTO();

        List<RequestOrderProductItemsDTO> list = requestUpdateOrderDTO.getProducts();
        if ( list != null ) {
            requestAddOrderDTO.setProducts( new ArrayList<RequestOrderProductItemsDTO>( list ) );
        }

        return requestAddOrderDTO;
    }

    @Override
    public Set<ResponseOrderProductItemsDTO> responseOrderProductItemsDTO(List<RequestOrderProductItemsDTO> order) {
        if ( order == null ) {
            return null;
        }

        Set<ResponseOrderProductItemsDTO> set = new HashSet<ResponseOrderProductItemsDTO>( Math.max( (int) ( order.size() / .75f ) + 1, 16 ) );
        for ( RequestOrderProductItemsDTO requestOrderProductItemsDTO : order ) {
            set.add( requestOrderProductItemsDTOToResponseOrderProductItemsDTO( requestOrderProductItemsDTO ) );
        }

        return set;
    }

    protected ResponseOrderProductItemsDTO requestOrderProductItemsDTOToResponseOrderProductItemsDTO(RequestOrderProductItemsDTO requestOrderProductItemsDTO) {
        if ( requestOrderProductItemsDTO == null ) {
            return null;
        }

        ResponseOrderProductItemsDTO responseOrderProductItemsDTO = new ResponseOrderProductItemsDTO();

        responseOrderProductItemsDTO.setProduct( requestOrderProductItemsDTO.getProduct() );
        responseOrderProductItemsDTO.setUnit( requestOrderProductItemsDTO.getUnit() );

        return responseOrderProductItemsDTO;
    }
}
