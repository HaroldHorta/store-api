package com.company.storeapi.model.payload.response.order;

import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.enums.OrderStatus;
import com.company.storeapi.model.enums.PaymentType;
import lombok.Data;

import java.util.List;

@Data
public class ResponseOrderDTO {

    private String id;
    private List<ResponseOrderProductItemsDTO> products;
    private PaymentType paymentType;
    private OrderStatus orderStatus;

}
