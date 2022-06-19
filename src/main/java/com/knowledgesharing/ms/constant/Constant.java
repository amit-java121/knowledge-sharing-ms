package com.knowledgesharing.ms.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {

    public static final String ROLE_ADMIN = "hasRole('ROLE_ADMIN')";

    public static final String ROLE_CUSTOMER = "hasRole('ROLE_CUSTOMER')";
}
