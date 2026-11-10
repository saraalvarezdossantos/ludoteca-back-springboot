package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.customer.model.CustomerDto;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestRestTemplate
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    private static final int TOTAL_LOANS = 6;
    private static final int PAGE_SIZE = 5;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LoanService loanService;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDto>>() {
    };

    //TESTS PAGINACIÓN:

    @Test
    public void findFirstPageWithFiveSizeShouldReturnFirstFiveResults() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        System.out.println(response.getBody().getTotalElements());
        System.out.println(response.getBody().getContent().size());

        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeShouldReturnLastResults() {

        int elementsCount = TOTAL_LOANS - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

    // TESTS FILTRADO:

    @Test
    public void findWithoutFiltersShouldReturnAllLoansInDB() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(TOTAL_LOANS, response.getBody().getContent().size());
    }

    @Test
    public void findWithGameFilterShouldReturnOnlyMatchingLoans() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setGameId(1L);
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getContent().size());
    }

    //NOTA IMPORTANTE: filtro FECHA BUSCA préstamos activos, no que coincidan con la fecha:

    /** Datos data.sql:
     * El 06/11/2026 están activos:
     Loan 1 (01 -> 10)
     Loan 2 (03 -> 08)
     Loan 3 (05 -> 15) */
    @Test
    public void findWithDateFilterShouldReturnThreeResults() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setDate(LocalDate.of(2026, 11, 6));
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(3, response.getBody().getTotalElements());
    }

    @Test
    public void findWithDateFilterShouldReturnZeroResults() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setDate(LocalDate.of(2026, 11, 30));
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(0, response.getBody().getTotalElements());
    }

    /** Datos data.sql:
     * CLIENTE 1 tiene activos el día 6:
     Loan 1 (01 -> 10)
     Loan 3 (05 -> 15)
     */
    @Test
    public void findWithCustomerAndDateFilterShouldReturnTwoResults() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setCustomerId(1L);
        searchDto.setDate(LocalDate.of(2026, 11, 6));
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(2, response.getBody().getTotalElements());
    }

    /** Datos data.sql:
     *
     */
    @Test
    public void findWithGameAndCustomerFilterShouldReturnOneResult() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setGameId(1L);
        searchDto.setCustomerId(1L);
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(1, response.getBody().getTotalElements());
    }

    //TESTS VALIDACIONES:

    /** Datos data.sql:
     * inicio = 10/11/2026
     * fin = 05/11/2026
     */
    @Test
    public void saveShouldFailWhenEndDateIsBeforeStartDate() {
        //creación Dto Loan:
        LoanDto dto = new LoanDto();

        GameDto game = new GameDto();
        game.setId(6L);

        CustomerDto customer = new CustomerDto();
        customer.setId(1L);

        dto.setGame(game);
        dto.setCustomer(customer);

        dto.setStartDate(LocalDate.of(2026, 11, 10));
        dto.setEndDate(LocalDate.of(2026, 11, 5));

        //Nota: restTemplate.exchange() recibe respuesta HTTP, pero no propaga excepción para aserto
        //assertThrows(Exception.class, () -> restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), String.class));

        ResponseEntity<String> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void saveShouldFailWhenLoanDurationExceeds14Days() {

        LoanDto dto = new LoanDto();

        GameDto game = new GameDto();
        game.setId(6L);

        CustomerDto customer = new CustomerDto();
        customer.setId(1L);

        dto.setGame(game);
        dto.setCustomer(customer);

        dto.setStartDate(LocalDate.of(2026, 11, 1));
        dto.setEndDate(LocalDate.of(2026, 11, 20));

        ResponseEntity<String> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /** Datos data.sql:
     * game_id = 1
     * customer_id = 1
     * start_date = 2026-11-01
     * end_date = 2026-11-10
     * -préstamo exxistente: 1-------10
     * -nuevo préstamo:        5---8
     */
    @Test
    public void saveShouldFailWhenGameIsAlreadyLoanedInSamePeriod() {
        LoanDto dto = new LoanDto();

        GameDto game = new GameDto();
        game.setId(1L);

        CustomerDto customer = new CustomerDto();
        customer.setId(2L);

        dto.setGame(game);
        dto.setCustomer(customer);

        dto.setStartDate(LocalDate.of(2026, 11, 5));
        dto.setEndDate(LocalDate.of(2026, 11, 8));

        ResponseEntity<String> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /** Datos data.sql:
     * Cliente 1 tiene:
     * Loan 1: de 2026-11-01 a 2026-11-10
     * Loan 3: de 2026-11-05 a 2026-11-15
     * Se intenta crear tercero entre 5 y 10 de nov
     */
    @Test
    public void saveShouldFailWhenCustomerHasMoreThanTwoLoansInSamePeriod() {
        LoanDto dto = new LoanDto();

        GameDto game = new GameDto();
        game.setId(4L);

        CustomerDto customer = new CustomerDto();
        customer.setId(1L);

        dto.setGame(game);
        dto.setCustomer(customer);

        dto.setStartDate(LocalDate.of(2026, 11, 6));
        dto.setEndDate(LocalDate.of(2026, 11, 7));

        ResponseEntity<String> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), String.class);

        //assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println(response.getBody());
        System.out.println(response.getStatusCode());
    }
}