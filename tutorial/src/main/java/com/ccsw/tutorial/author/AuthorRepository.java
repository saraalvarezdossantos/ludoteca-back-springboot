package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 *
 */
public interface AuthorRepository extends CrudRepository<Author, Long> {

    /**
     * Método para recuperar un listado paginado de {@link Author}
     * Esta operación no está en CRUDRepository.
     * Spring JPA "sabe" que tiene que recuperar todos datos de la tabla Author,
     * y que deben estar paginados.
     *
     * @param pageable pageable
     * @return {@link Page} de {@link Author}
     */
    Page<Author> findAll(Pageable pageable);

}