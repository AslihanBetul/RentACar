package com.abm.utility;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class CodeGenerator {
    public String codeGenerator(){
        String codeSource= UUID.randomUUID().toString();
        String[] splitedCodeSource = codeSource.split("-");
        StringBuilder code= new StringBuilder();
        for (String s : splitedCodeSource) {
            code.append(s.charAt(0));
        }
        return code.toString();
    }
}
