package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.game.model.Game;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * @author saraalvarezdossantos
 *
 */
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
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
    
    /**
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game new value of {@link #game}.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer new value of {@link #customer}.
     */
    public void setCustomer(Customer customer) {
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
