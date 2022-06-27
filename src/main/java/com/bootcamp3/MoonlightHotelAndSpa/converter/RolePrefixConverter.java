package com.bootcamp3.MoonlightHotelAndSpa.converter;

import java.util.Set;

import static com.bootcamp3.MoonlightHotelAndSpa.constant.UserConstant.ROLE_PREFIX;

public class RolePrefixConverter {

    public static Set<String> removePrefix(Set<String> role) {

        String roleString = role.iterator().next();
        return Set.of(roleString.substring(5).toLowerCase());
    }

    public static String addPrefix(String role) {

        return ROLE_PREFIX + role.toUpperCase();
    }
}
