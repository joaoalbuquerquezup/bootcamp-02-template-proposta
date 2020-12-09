package br.com.zup.proposta.core.card.travelnotice;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CardTravelNoticeRequest {

    @NotBlank
    private String destiny;

    @NotNull
    @Future
    private LocalDate finishDate;

    public CardTravelNotice toModel(String userAgent, String ipAddress) {
        return new CardTravelNotice(this.destiny, userAgent, ipAddress, this.finishDate);
    }
}
