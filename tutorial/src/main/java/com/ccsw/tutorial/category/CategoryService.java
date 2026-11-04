package com.ccsw.tutorial.category;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;

import java.util.List;

/**
 * @author ccsw
 *
 */
public interface CategoryService {

    /*
    Notas repaso tutorial:
    - Toda la lógica de negocio, operaciones y demás debe estar implementada en los servicios.
      Los controladores simplemente invocan servicios y transforman ciertos datos.
    - Es buena práctica que la capa de servicios se implemente usando el patrón fachada,
      esto quiere decir que necesitamos tener una Interface y al menos una implementación de esa Interface.
      Y siempre debemos interactuar con la Interface.
      Esto nos permitirá a futuro poder sustituir la implementación por otra diferente sin que el resto del código se vea afectado.
      Especialmente útil cuando queremos mockear comportamientos en tests.
    - La capa de servicio puede invocar a otros servicios en sus operaciones, pero nunca debe invocar a un controlador.
    - Para crear un servicio se debe anotar mediante @Service y además debe implementar la Interface del servicio.
      Un error muy común al arrancar un proyecto y ver que no funcionan las llamadas es que no existe la anotación @Service
      o que no se ha implementado la Interface.
     -La forma de inyectar y utilizar componentes manejados por Spring Boot es mediante la anotación @Autowired.
      NO intentes crear un objeto de CategoryServiceImpl, ni hacer un new, ya que no estará manejado por Springboot y dará fallos de NullPointer. Lo mejor es dejar que Spring Boot lo gestione y utilizar las inyecciones de dependencias.
     */

    /**
     * Recupera una {@link Category} a partir de su ID
     *
     * @param id PK de la entidad
     * @return {@link Category}
     */
    Category get(Long id);
    
    /**
     * Método para recuperar todas las categorías
     *
     * @return {@link List} de {@link Category}
     */
    List<Category> findAll();

    /**
     * Método para crear o actualizar una categoría
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, CategoryDto dto);

    /**
     * Método para borrar una categoría
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

}