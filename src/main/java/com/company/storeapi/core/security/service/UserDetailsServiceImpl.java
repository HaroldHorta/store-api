package com.company.storeapi.core.security.service;

import com.company.storeapi.model.entity.User;
import com.company.storeapi.repositories.user.facade.UserRepositoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepositoryFacade userRepositoryFacade;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositoryFacade.findByUsername(username);
        return UserDetailsImpl.build(user);
    }
}
