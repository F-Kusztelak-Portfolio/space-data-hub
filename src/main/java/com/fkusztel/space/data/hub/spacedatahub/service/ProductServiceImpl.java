package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.entity.Product;
import com.fkusztel.space.data.hub.spacedatahub.entity.ProductRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Filip.Kusztelak
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Iterable<Product> findProductByDateLower(LocalDate date) {
        List<Product> productList = Lists.newArrayList(findAll());

        //Filter products and collect all with date lower than given by User
        return productList.stream()
                .filter(product -> product.getAcquisitionDate().isBefore(date))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Product> findProductByDateGreater(LocalDate date) {
        List<Product> productList = Lists.newArrayList(findAll());

        //Filter products and collect all with date greater than given by User
        return productList.stream()
                .filter(product -> product.getAcquisitionDate().isAfter(date))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Product> findProductByDateBetween(LocalDate startDate, LocalDate endDate) {
        List<Product> productList = Lists.newArrayList(findAll());
        List<Product> datesBetween = Lists.newArrayList();

        //Check all products and if date is between start and end date
        for(Product product: productList){
            if (product.getAcquisitionDate().isAfter(startDate)
                    && product.getAcquisitionDate().isBefore(endDate)) {

                datesBetween.add(product);

            }
        }
        return datesBetween;
        }

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
