package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * @author saraalvarezdossantos
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
