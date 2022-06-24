package com.bootcamp3.MoonlightHotelAndSpa.service.impl;

import com.bootcamp3.MoonlightHotelAndSpa.model.Role;
import com.bootcamp3.MoonlightHotelAndSpa.repository.RoleRepository;
import com.bootcamp3.MoonlightHotelAndSpa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.ExceptionConstant.ROLE_NOT_FOUND;

@Service
public class RoleServiceImpl implements RoleService {

    public RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findRoleByAuthority(String authority) {

       return roleRepository.findByAuthority(authority)
               .orElseThrow(() -> new NoSuchElementException(String.format(ROLE_NOT_FOUND, authority)));
    }
}
