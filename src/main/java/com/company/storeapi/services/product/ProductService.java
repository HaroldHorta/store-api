package com.company.storeapi.services.product;

import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.payload.request.product.RequestAddProductDTO;
import com.company.storeapi.model.payload.request.product.RequestUpdateProductDTO;
import com.company.storeapi.model.payload.request.product.RequestUpdateUnitDTO;
import com.company.storeapi.model.payload.response.product.ResponseListProductPaginationDto;
import com.company.storeapi.model.payload.response.product.ResponseOrderProductItemsDTO;
import com.company.storeapi.model.payload.response.product.ResponseProductDTO;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ResponseProductDTO> getAllProduct ();

    ResponseListProductPaginationDto getAllProductsFilters ();

    ResponseListProductPaginationDto getAllProductsFilters (Pageable pageable);

    ResponseProductDTO saveProduct(RequestAddProductDTO requestAddProductDTO) throws IOException;

    ResponseProductDTO updateProduct(String id, RequestUpdateProductDTO requestUpdateCustomerDTO);

    ResponseProductDTO validateAndGetProductById(String id);

    ResponseOrderProductItemsDTO getItemsTotal(String id, int unit);

    ResponseProductDTO addUnitProduct(RequestUpdateUnitDTO requestUpdateUnitDTO);

    ResponseProductDTO updateStatus(String id, Status status);

    List<ResponseProductDTO> findProductByCategory (String id);


}
