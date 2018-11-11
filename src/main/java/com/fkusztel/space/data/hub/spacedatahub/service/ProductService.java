package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.entity.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Filip.Kusztelak
 */
@Service
public interface ProductService {

    void saveProduct (Product product);

    Optional<Product> findProduct (Long productId);

    Iterable<Product> findProductByDateLower (LocalDate date);

    Iterable<Product> findProductByDateGreater (LocalDate date);

    Iterable<Product> findProductByDateBetween (LocalDate startDate, LocalDate endDate);

    Iterable<Product> findAll();

    void deleteProduct(Long productId);

    String purchaseProduct(List<Long> productId);
}
