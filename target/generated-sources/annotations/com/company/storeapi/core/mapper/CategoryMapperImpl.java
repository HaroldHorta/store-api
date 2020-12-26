package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.payload.request.category.RequestAddCategoryDTO;
import com.company.storeapi.model.payload.request.category.RequestUpdateCategoryDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-24T18:15:26-0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.7 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl extends CategoryMapper {

    @Override
    public Category toCategory(RequestAddCategoryDTO requestAddCategoryDTO) {
        if ( requestAddCategoryDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setDescription( requestAddCategoryDTO.getDescription() );

        return category;
    }

    @Override
    public Category toCategory(ResponseCategoryDTO responseCategoryDTO) {
        if ( responseCategoryDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( responseCategoryDTO.getId() );
        category.setDescription( responseCategoryDTO.getDescription() );

        return category;
    }

    @Override
    public ResponseCategoryDTO toCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        ResponseCategoryDTO responseCategoryDTO = new ResponseCategoryDTO();

        responseCategoryDTO.setId( category.getId() );
        responseCategoryDTO.setDescription( category.getDescription() );

        return responseCategoryDTO;
    }

    @Override
    public void updateCategoryFromDto(RequestUpdateCategoryDTO requestUpdateCategoryDTO, Category category) {
        if ( requestUpdateCategoryDTO == null ) {
            return;
        }

        if ( requestUpdateCategoryDTO.getId() != null ) {
            category.setId( requestUpdateCategoryDTO.getId() );
        }
        if ( requestUpdateCategoryDTO.getDescription() != null ) {
            category.setDescription( requestUpdateCategoryDTO.getDescription() );
        }
    }
}
