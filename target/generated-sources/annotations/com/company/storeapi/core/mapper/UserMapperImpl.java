package com.company.storeapi.core.mapper;

import com.company.storeapi.model.entity.User;
import com.company.storeapi.model.payload.response.user.ResponseUserDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-24T15:28:47-0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public User toUser(ResponseUserDTO responseUserDTO) {
        if ( responseUserDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( responseUserDTO.getUsername() );
        user.setEmail( responseUserDTO.getEmail() );

        return user;
    }

    @Override
    public ResponseUserDTO toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        ResponseUserDTO responseUserDTO = new ResponseUserDTO();

        responseUserDTO.setUsername( user.getUsername() );
        responseUserDTO.setEmail( user.getEmail() );

        return responseUserDTO;
    }
}
