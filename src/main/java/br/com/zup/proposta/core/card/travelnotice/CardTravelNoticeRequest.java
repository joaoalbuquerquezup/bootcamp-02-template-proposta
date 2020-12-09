package br.com.zup.proposta.core.card.travelnotice;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CardTravelNoticeRequest {

    @NotBlank
    private String destiny;

    @NotNull
    @Future
    private LocalDate finishDate;

    public String getDestiny() {
        return destiny;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public CardTravelNotice toModel(String userAgent, String ipAddress) {
        return new CardTravelNotice(this.destiny, userAgent, ipAddress, this.finishDate);
    }
}
