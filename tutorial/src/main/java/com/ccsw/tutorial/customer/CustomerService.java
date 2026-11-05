package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;

import java.util.List;

public interface CustomerService {

    /**
     * Recupera un {@link Customer} a partir de su ID
     *
     * @param id PK de la entidad
     * @return {@link Customer}
     */
    Customer get(Long id);

    /**
     * Método para recuperar todas los Customers
     *
     * @return {@link List} de {@link Customer}
     */
    List<Customer> findAll();

    /**
     * Método para crear o actualizar un Customer
     * Verifica que no se pueda insertar un Customer con nombre que ya exista en la BDD
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, CustomerDto dto) throws Exception;

    /**
     * Método para borrar un Customer
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;
}
