package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author saraalvarezdossantos
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {
/*
        Specification<Loan> specification = Specification.where((Specification<Loan>) null);

        if (dto.getGameId() != null) {
            specification = specification.and(new LoanSpecification(new SearchCriteria("game.id", ":", dto.getGameId())));
        }

        if (dto.getCustomerId() != null) {
            specification = specification.and(new LoanSpecification(new SearchCriteria("customer.id", ":", dto.getCustomerId())));
        }

        if (dto.getDate() != null) {

            specification = specification.and(new LoanSpecification(new SearchCriteria("startDate", "<=", dto.getDate())));

            specification = specification.and(new LoanSpecification(new SearchCriteria("endDate", ">=", dto.getDate())));
        }

        return this.loanRepository.findAll(specification, dto.getPageable().getPageable());
    */

        Specification<Loan> specification = (root, query, builder) -> builder.conjunction();

        if (dto.getGameId() != null) {
            specification = specification.and(new LoanSpecification(new SearchCriteria("game.id", ":", dto.getGameId())));
        }

        if (dto.getCustomerId() != null) {
            specification = specification.and(new LoanSpecification(new SearchCriteria("customer.id", ":", dto.getCustomerId())));
        }

        if (dto.getDate() != null) {
            specification = specification.and(new LoanSpecification(new SearchCriteria("startDate", "<=", dto.getDate())));

            specification = specification.and(new LoanSpecification(new SearchCriteria("endDate", ">=", dto.getDate())));
        }

        return this.loanRepository.findAll(specification, dto.getPageable().getPageable());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto dto) throws Exception {
        Loan loan;
        if (id == null) {
            loan = new Loan();
        } else {
            loan = this.loanRepository.findById(id).orElse(null);
        }

        if (loan == null) {
            throw new Exception("Loan does not exist");
        }
        //BeanUtils: Para copiar más de un dato de una clase a otra
        BeanUtils.copyProperties(dto, loan, "id");

        this.loanRepository.save(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {
        if (this.loanRepository.findById(id).orElse(null) == null) {
            throw new Exception("Loan does not exist");
        }
        this.loanRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }
}
