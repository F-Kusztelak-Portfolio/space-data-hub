package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.config.TestObjectFactory;
import com.fkusztel.space.data.hub.spacedatahub.entity.Product;
import com.fkusztel.space.data.hub.spacedatahub.entity.ProductRepository;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Filip.Kusztelak
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProductServiceImplTest.class, ProductServiceImpl.class})
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void purchaseProduct_Success() {
        List<Long> productId = Arrays.asList(1L);

        Mockito.when(productRepository
                .findById(productId.get(0)))
                .thenReturn(Optional.of(TestObjectFactory.ProductPurchased.product));

        String exceptedResult = "Order completed";

        String result = productService.purchaseProduct(productId);

        Assert.assertEquals(exceptedResult, result);
    }

    @Test
    public void purchaseProduct_Fail() {
        List<Long> productId = Arrays.asList(1L);

        Mockito.when(productRepository
                .findById(productId.get(0)))
                .thenReturn(Optional.empty());

        String exceptedResult = "Purchase failed, no such products available";

        String result = productService.purchaseProduct(productId);

        Assert.assertEquals(exceptedResult, result);
    }

    @Test
    public void findProductByDateLower() {
        Mockito.when(productRepository.findAll()).thenReturn(TestObjectFactory.ProductList.datesAll);

        List<Product> result = Lists
                        .newArrayList(productService
                        .findProductByDateLower(LocalDate.parse(TestObjectFactory.Dates.THIRD_DATE)));

        List<Product> exceptedResult = TestObjectFactory.ProductList.datesLower;

        Assert.assertEquals(exceptedResult, result);
    }

    @Test
    public void findProductByDateGreater() {
        Mockito.when(productRepository.findAll()).thenReturn(TestObjectFactory.ProductList.datesAll);

        List<Product> result = Lists
                .newArrayList(productService
                        .findProductByDateGreater(LocalDate.parse(TestObjectFactory.Dates.THIRD_DATE)));

        List<Product> exceptedResult = TestObjectFactory.ProductList.datesGreater;

        Assert.assertEquals(exceptedResult, result);
    }

    @Test
    public void findProductByDateBetween() {
        Mockito.when(productRepository.findAll()).thenReturn(TestObjectFactory.ProductList.datesAll);

        List<Product> result = Lists
                .newArrayList(productService
                        .findProductByDateBetween(LocalDate.parse(TestObjectFactory.Dates.FIRST_DATE),
                                LocalDate.parse(TestObjectFactory.Dates.FIFTH_DATE)));

        List<Product> exceptedResult = TestObjectFactory.ProductList.datesBetween;

        Assert.assertEquals(exceptedResult, result);
    }
}
