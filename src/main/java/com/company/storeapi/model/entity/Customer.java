package com.company.storeapi.model.entity;

import com.company.storeapi.model.enums.Status;
import com.company.storeapi.model.enums.TypeDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.Email;

@Document(collection = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Customer {


    @Id
    private String id;
    @NonNull
    private String name;

    private TypeDocument typeDocument;
    
    private String nroDocument;

    private String email;

    private String address;

    private String phone;

    private Status status;


}
