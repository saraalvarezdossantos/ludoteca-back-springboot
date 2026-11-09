package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;

import java.time.LocalDate;

/**
 * Obtener datos de paginación para Spring JPA
 * @author saraalvarezdossantos
 *
 */
public class LoanSearchDto {

    private PageableRequest pageable;

    //Campos para añadir el filtrado:
    private Long gameId;
    private Long customerId;
    private LocalDate date;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}
