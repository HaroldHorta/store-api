package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.Category;
import com.company.storeapi.model.payload.request.category.RequestAddCategoryDTO;
import com.company.storeapi.model.payload.request.category.RequestUpdateCategoryDTO;
import com.company.storeapi.model.payload.response.category.ResponseCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class CategoryMapper {

   public abstract Category toCategory(RequestAddCategoryDTO requestAddCategoryDTO);

    public abstract Category toCategory(ResponseCategoryDTO responseCategoryDTO);

    public abstract ResponseCategoryDTO toCategoryDto(Category category);

    public abstract void updateCategoryFromDto(RequestUpdateCategoryDTO requestUpdateCategoryDTO, @MappingTarget Category category);

}
