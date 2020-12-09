package br.com.zup.proposta.core.card.digitalwallet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class InformDigitalWalletRequest {

    private String email;

    @JsonProperty("carteira")
    private WalletName walletName;

    public InformDigitalWalletRequest(String email, WalletName walletName) {
        this.email = email;
        this.walletName = walletName;
    }

    public InformDigitalWalletRequest(AddDigitalWalletRequest request) {
        this.email = request.getEmail();
        this.walletName = request.getWalletName();
    }
}
