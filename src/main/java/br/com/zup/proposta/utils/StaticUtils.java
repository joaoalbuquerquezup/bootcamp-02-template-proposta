package br.com.zup.proposta.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StaticUtils {

    // TODO: Trocar para um ObjectPool https://stackoverflow.com/questions/3907929/should-i-declare-jacksons-objectmapper-as-a-static-field
    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();

}
