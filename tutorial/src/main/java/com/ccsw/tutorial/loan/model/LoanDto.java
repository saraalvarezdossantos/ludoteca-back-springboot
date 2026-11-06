package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.customer.model.CustomerDto;
import com.ccsw.tutorial.game.model.GameDto;

import java.time.LocalDate;

/**
 * @author saraalvarezdossantos
 *
 */
public class LoanDto {

    private Long id;
    private GameDto game;
    private CustomerDto customer;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * @return id
     */
    public Long getId() {

        return this.id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {

        this.id = id;
    }

    public GameDto getGame() {
        return game;
    }

    /**
     * @param game new value of {@link #game}.
     */
    public void setGame(GameDto game) {
        this.game = game;
    }

    /**
     * @return customer
     */
    public CustomerDto getCustomer() {
        return customer;
    }

    /**
     * @param customer new value of {@link #customer}.
     */
    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    /**
     * @return startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate new value of {@link #startDate}.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate new value of {@link #endDate}.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
