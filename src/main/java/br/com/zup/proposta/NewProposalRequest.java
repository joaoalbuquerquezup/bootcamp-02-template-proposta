package br.com.zup.proposta;

import br.com.zup.proposta.validation.CpfCnpj;
import br.com.zup.proposta.validation.Unique;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class NewProposalRequest {

    @NotBlank @CpfCnpj @Unique(clazz = Proposal.class)
    private String document;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull @Positive
    private BigDecimal wage;

    public Proposal toModel() {
        return new Proposal(this.document, this.email, this.name, this.address, this.wage);
    }
}
