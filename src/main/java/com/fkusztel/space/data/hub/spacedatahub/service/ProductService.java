package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.entity.Product;
import com.fkusztel.space.data.hub.spacedatahub.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Filip.Kusztelak
 */
@Service
public interface ProductService {

    void saveProduct (Product product);

    Product findProduct (Long productId) throws ProductNotFoundException;

    Iterable<Product> findProductByDateLower (LocalDate date);

    Iterable<Product> findProductByDateGreater (LocalDate date);

    Iterable<Product> findProductByDateBetween (LocalDate startDate, LocalDate endDate);

    Iterable<Product> findAll();

    void deleteProduct(Long productId) throws ProductNotFoundException;

    String purchaseProduct(List<Long> productId);
}
