package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanTest {
    private static final Long EXISTS_LOAN_ID = 1L;
    private static final Long NOT_EXISTS_LOAN_ID = 0L;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    void getExistsLoanIdShouldReturnLoan() {

        Loan loan = mock(Loan.class);

        when(loan.getId()).thenReturn(EXISTS_LOAN_ID);
        when(loanRepository.findById(EXISTS_LOAN_ID)).thenReturn(Optional.of(loan));

        Loan loanResponse = loanService.get(EXISTS_LOAN_ID);

        assertNotNull(loanResponse);
        assertEquals(EXISTS_LOAN_ID, loanResponse.getId());
    }

    @Test
    void getNotExistsLoanIdShouldReturnNull() {

        when(loanRepository.findById(NOT_EXISTS_LOAN_ID)).thenReturn(Optional.empty());

        Loan loan = loanService.get(NOT_EXISTS_LOAN_ID);

        assertNull(loan);
    }



    /* TESTS VALIDACIONES: PENDIENTE DE IMPLEMENTAR VALIDACIONES

    @Test
    void saveWithEndDateBeforeStartDateShouldThrowException() {
        LoanDto loanDto = new LoanDto();

        loanDto.setStartDate(LocalDate.of(2026, 11, 10));
        loanDto.setEndDate(LocalDate.of(2026, 11, 5));

        //SUSTITUIR CON LA EXCEPCIÓN ADECUADA MÁS TARDE:
        assertThrows(RuntimeException.class, () -> loanService.save(loanDto));
    }

    @Test
    void saveLoanLongerThanFourteenDaysShouldThrowException() {
        LoanDto loanDto = new LoanDto();

        loanDto.setStartDate(LocalDate.of(2026, 11, 1));
        loanDto.setEndDate(LocalDate.of(2026, 11, 20));

        //SUSTITUIR CON LA EXCEPCIÓN ADECUADA MÁS TARDE:
        assertThrows(RuntimeException.class, () -> loanService.save(loanDto));
    }

    @Test
    void saveWithOverlappingGameLoanShouldThrowException() {

        Game game = new Game();
        game.setId(1L);

        Loan existingLoan = new Loan();
        existingLoan.setGame(game);
        existingLoan.setStartDate(LocalDate.of(2026, 11, 1));
        existingLoan.setEndDate(LocalDate.of(2026, 11, 10));

        when(loanRepository.findAll(any(Specification.class))).thenReturn(List.of(existingLoan));

        LoanDto loanDto = new LoanDto();
        loanDto.setGame(new GameDto());
        loanDto.getGame().setId(1L);

        loanDto.setStartDate(LocalDate.of(2026, 11, 5));
        loanDto.setEndDate(LocalDate.of(2026, 11, 15));

        //SUSTITUIR CON LA EXCEPCIÓN ADECUADA MÁS TARDE:
        assertThrows(RuntimeException.class, () -> loanService.save(loanDto));
    }

    @Test
    void saveThirdSimultaneousLoanShouldThrowException() {

        Customer customer = new Customer();
        customer.setId(1L);

        Loan loan1 = new Loan();
        loan1.setCustomer(customer);
        loan1.setStartDate(LocalDate.of(2026, 11, 1));
        loan1.setEndDate(LocalDate.of(2026, 11, 10));

        Loan loan2 = new Loan();
        loan2.setCustomer(customer);
        loan2.setStartDate(LocalDate.of(2026, 11, 1));
        loan2.setEndDate(LocalDate.of(2026, 11, 10));

        when(loanRepository.findAll(any(Specification.class))).thenReturn(List.of(loan1, loan2));

        LoanDto loanDto = new LoanDto();

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);

        loanDto.setCustomer(customerDto);

        loanDto.setStartDate(LocalDate.of(2026, 11, 5));
        loanDto.setEndDate(LocalDate.of(2026, 11, 8));

        //SUSTITUIR CON LA EXCEPCIÓN ADECUADA MÁS TARDE:
        assertThrows(RuntimeException.class, () -> loanService.save(loanDto));
    }

    */
}
