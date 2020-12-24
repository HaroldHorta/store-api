package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.entity.Product;
import com.company.storeapi.model.payload.request.product.RequestAddProductDTO;
import com.company.storeapi.model.payload.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import java.text.SimpleDateFormat;
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
public class ProductMapperImpl extends ProductMapper {

    @Override
    public ResponseProductDTO toProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ResponseProductDTO responseProductDTO = new ResponseProductDTO();

        responseProductDTO.setId( product.getId() );
        responseProductDTO.setName( product.getName() );
        responseProductDTO.setDescription( product.getDescription() );
        Set<ResponseCategoryDTO> set = product.getCategory();
        if ( set != null ) {
            responseProductDTO.setCategory( new HashSet<ResponseCategoryDTO>( set ) );
        }
        responseProductDTO.setStatus( product.getStatus() );
        if ( product.getCreateAt() != null ) {
            responseProductDTO.setCreateAt( new SimpleDateFormat().format( product.getCreateAt() ) );
        }
        if ( product.getUpdateAt() != null ) {
            responseProductDTO.setUpdateAt( new SimpleDateFormat().format( product.getUpdateAt() ) );
        }
        responseProductDTO.setPriceBuy( product.getPriceBuy() );
        responseProductDTO.setPriceSell( product.getPriceSell() );
        responseProductDTO.setUnit( product.getUnit() );
        responseProductDTO.setPhoto( product.getPhoto() );

        return responseProductDTO;
    }

    @Override
    public RequestAddProductDTO toProductRequestUpdate(RequestUpdateProductDTO product) {
        if ( product == null ) {
            return null;
        }

        RequestAddProductDTO requestAddProductDTO = new RequestAddProductDTO();

        requestAddProductDTO.setName( product.getName() );
        requestAddProductDTO.setDescription( product.getDescription() );
        List<Category> list = product.getCategoryId();
        if ( list != null ) {
            requestAddProductDTO.setCategoryId( new ArrayList<Category>( list ) );
        }
        requestAddProductDTO.setPriceBuy( product.getPriceBuy() );
        requestAddProductDTO.setPriceSell( product.getPriceSell() );
        if ( product.getUnit() != null ) {
            requestAddProductDTO.setUnit( product.getUnit() );
        }

        return requestAddProductDTO;
    }

    @Override
    public void updateProductFromDto(RequestUpdateProductDTO updateOrderDto, Product product) {
        if ( updateOrderDto == null ) {
            return;
        }

        if ( updateOrderDto.getName() != null ) {
            product.setName( updateOrderDto.getName() );
        }
        if ( updateOrderDto.getDescription() != null ) {
            product.setDescription( updateOrderDto.getDescription() );
        }
        product.setPriceBuy( updateOrderDto.getPriceBuy() );
        product.setPriceSell( updateOrderDto.getPriceSell() );
        if ( updateOrderDto.getUnit() != null ) {
            product.setUnit( updateOrderDto.getUnit() );
        }
    }
}
