package br.com.zup.proposta.core.card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardRetrieveResponse {

    @JsonProperty("id")
    private String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }
}
