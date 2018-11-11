package com.fkusztel.space.data.hub.spacedatahub.entity;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Filip.Kusztelak
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

    void deleteById(Long id);
}
