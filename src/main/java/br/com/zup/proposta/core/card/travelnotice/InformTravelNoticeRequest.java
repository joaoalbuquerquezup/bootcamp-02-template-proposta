package br.com.zup.proposta.core.card.travelnotice;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class InformTravelNoticeRequest {

    @JsonProperty("destino")
    private String destiny;

    @JsonProperty("validoAte")
    private LocalDate finishDate;

    public InformTravelNoticeRequest(CardTravelNoticeRequest cardTravelNoticeRequest) {
        this.destiny = cardTravelNoticeRequest.getDestiny();
        this.finishDate = cardTravelNoticeRequest.getFinishDate();
    }
}
