package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.config.Constants;
import com.fkusztel.space.data.hub.spacedatahub.entity.Product;
import com.fkusztel.space.data.hub.spacedatahub.entity.ProductRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    //Save product to database
    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    //Finds product with given ID
    @Override
    public Optional<Product> findProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        //Check if product is purchased and set correct URL
        if (product.isPresent()) {
            if (product.get().getPurchased()){
                product.get().setUrl(Constants.ftp.FTP_URL);
                saveProduct(product.get());
                return product;
            } else {
                product.get().setUrl(Constants.ftp.FTP_PURCHASE);
                saveProduct(product.get());
                return product;
            }
        }
        return Optional.empty();
    }

    //Finds a date with lower value than given one
    @Override
    public Iterable<Product> findProductByDateLower(LocalDate date) {
        List<Product> productList = Lists.newArrayList(findAll());

        //Filter products and collect all with date lower than given by User
        return productList.stream()
                .filter(product -> product.getAcquisitionDate().isBefore(date))
                .collect(Collectors.toList());
    }

    //Finds a date with grater value than given one
    @Override
    public Iterable<Product> findProductByDateGreater(LocalDate date) {
        List<Product> productList = Lists.newArrayList(findAll());

        //Filter products and collect all with date greater than given by User
        return productList.stream()
                .filter(product -> product.getAcquisitionDate().isAfter(date))
                .collect(Collectors.toList());
    }

    //Finds a date between two given dates
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

    //Find all products in database
    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    //Delete product with given ID
    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    //Purchase product with given ID
    @Override
    public String purchaseProduct(List<Long> productId) {
        //Get all products from the list and update purchase field
        for (Long id : productId) {

            log.info("purchaseProduct: {}", id);

            Optional<Product> product = findProduct(id);

            //Check if product exists and update purchase field
            if (product.isPresent()) {
                Product purchasedProduct = product.get();
                purchasedProduct.setPurchased(true);
                saveProduct(purchasedProduct);
                log.info("Product with ID: {}", id + " purchased");
            } else {
                return "Purchase failed, no such products available";
            }
        }
        return "Order completed";
    }
}
