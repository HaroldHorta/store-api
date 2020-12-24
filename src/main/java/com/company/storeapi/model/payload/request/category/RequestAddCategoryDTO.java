package com.company.storeapi.model.payload.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAddCategoryDTO {

    @Schema(example = "Muebles")
    private String description;



}
