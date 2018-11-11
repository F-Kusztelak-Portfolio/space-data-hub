package com.fkusztel.space.data.hub.spacedatahub.controller;

import com.fkusztel.space.data.hub.spacedatahub.dto.FootprintDto;
import com.fkusztel.space.data.hub.spacedatahub.entity.Mission;
import com.fkusztel.space.data.hub.spacedatahub.entity.Product;
import com.fkusztel.space.data.hub.spacedatahub.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Controller
@RequestMapping(path = "product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(path = "/create")
    public @ResponseBody
    String createProduct(@RequestParam String acquisitionDate, @RequestParam String missionName,
                         @RequestParam List<String> footprint, @RequestParam Double price, @RequestParam String url) {

        Product product = Product.builder()
                .acquisitionDate(LocalDate.parse(acquisitionDate))
                .footprint(footprint)
                .nameMission(missionName)
                .price(price)
                .url(url)
                .build();

        log.info("createProduct: {}", product.toString());
        productService.saveProduct(product);
        return "Created " + product.toString();
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping(path = "/lower")
    public @ResponseBody
    Iterable<Product> findProductByDateLower(String date) {
        return productService.findProductByDateLower(LocalDate.parse(date));
    }

    @GetMapping(path = "/greater")
    public @ResponseBody
    Iterable<Product> findProductByDateGreater(String date) {
        return productService.findProductByDateGreater(LocalDate.parse(date));
    }

    @GetMapping(path = "/between")
    public @ResponseBody
    Iterable<Product> findProductByDateBetween(String startDate,
                                               String endDate) {

        return productService.findProductByDateBetween(LocalDate.parse(startDate),
                LocalDate.parse(endDate));
    }
}
