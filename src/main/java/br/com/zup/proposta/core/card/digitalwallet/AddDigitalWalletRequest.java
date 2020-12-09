package br.com.zup.proposta.core.card.digitalwallet;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddDigitalWalletRequest {

    @NotBlank @Email
    private String email;

    @NotNull
    private WalletName walletName;

    public String getEmail() {
        return email;
    }

    public WalletName getWalletName() {
        return walletName;
    }

    public DigitalWallet toModel(String walletIdAtExternalSystem) {
        return new DigitalWallet(this.email, this.walletName, walletIdAtExternalSystem);
    }
}
