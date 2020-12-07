package br.com.zup.proposta.card.biometry;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CreateBiometryRequest {

    @NotBlank
    private String fingerprint;

    public Biometry toModel() {
        return new Biometry(this.fingerprint);
    }
}
