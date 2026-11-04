package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 * Nota: interface que extienda CrudRepository pasándole Entity y el tipo de la Primary Key:
 * Spring construye el resto y provee métodos CRUD
 *
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

}