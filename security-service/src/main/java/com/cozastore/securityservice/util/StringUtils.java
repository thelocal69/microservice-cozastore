package com.cozastore.securityservice.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtils {
    public String getUserNameFormDomain(String domain){
        if (domain.contains("@")){
            int index = domain.indexOf("@");
            return domain.substring(0, index);
        }
        return domain;
    }
}
