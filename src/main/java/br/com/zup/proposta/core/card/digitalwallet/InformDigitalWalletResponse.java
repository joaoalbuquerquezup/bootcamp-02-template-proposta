package br.com.zup.proposta.core.card.digitalwallet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InformDigitalWalletResponse {

    @JsonProperty("id")
    private String walletIdAtExternalSystem;

    public String getWalletIdAtExternalSystem() {
        return walletIdAtExternalSystem;
    }
}
