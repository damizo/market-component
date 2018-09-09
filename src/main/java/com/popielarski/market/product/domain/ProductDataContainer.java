package com.popielarski.market.product.domain;

import com.popielarski.market.common.domain.PriceDTO;
import com.popielarski.market.discount.domain.boughttogether.BoughtTogetherDiscount;
import com.popielarski.market.discount.domain.boughttogether.ProductDiscountPair;
import com.popielarski.market.discount.domain.multiitems.MultiItemsDiscount;
import com.popielarski.market.item.ItemDTO;

import java.util.Set;

public class ProductDataContainer {
    public Product cola() {
        return Product.builder()
                .barCode("P_00001")
                .quantity(50)
                .name("Cola 2l")
                .price(Price.of("5.00"))
                .build();
    }

    public MultiItemsDiscount multiItemsDiscount(Set<Product> products) {
        return MultiItemsDiscount.builder()
                .description("If you buy more than one item of this product, your price will be decreased by price of one quantity of this product")
                .products(products)
                .build();
    }

    public BoughtTogetherDiscount boughtTogetherDiscount(Set<ProductDiscountPair> productPairs) {
        return BoughtTogetherDiscount.builder()
                .description("Each price of pair's product will be divided by 2")
                .productPairs(productPairs)
                .build();
    }

    public ItemDTO colaItemDTO(Integer quantity) {
        Product cola = cola();
        return ItemDTO.builder()
                .name(cola.getName())
                .price(PriceDTO.of(cola.getPrice()))
                .quantity(quantity)
                .build();
    }

    public Product snickers() {
        return Product.builder()
                .barCode("P_00002")
                .quantity(50)
                .name("Snickers")
                .price(Price.of("2.00"))
                .build();
    }

    public ItemDTO snickersItemDTO(Integer quantity) {
        Product snickers = snickers();
        return ItemDTO.builder()
                .name(snickers.getName())
                .price(PriceDTO.of(snickers.getPrice()))
                .quantity(quantity)
                .build();
    }

    public Product rafaello() {
        return Product.builder()
                .barCode("P_00003")
                .quantity(50)
                .name("Rafaello")
                .price(Price.of("10.00"))
                .build();
    }

    public ItemDTO rafaelloDTO(Integer quantity) {
        Product rafaello = rafaello();
        return ItemDTO.builder()
                .barCode(rafaello.getBarCode())
                .name(rafaello.getName())
                .price(PriceDTO.of(rafaello.getPrice()))
                .quantity(quantity)
                .build();
    }

    public Product flakes() {
        return Product.builder()
                .barCode("P_00004")
                .quantity(20)
                .name("Oat flakes")
                .price(Price.of("3.00"))
                .build();
    }

    public ItemDTO flakesDTO(Integer quantity) {
        Product flakes = flakes();
        return ItemDTO.builder()
                .barCode(flakes.getBarCode())
                .name(flakes.getName())
                .price(PriceDTO.of(flakes.getPrice()))
                .quantity(quantity)
                .build();
    }

    public Product wine() {
        return Product.builder()
                .barCode("P_00005")
                .quantity(30)
                .name("Wine")
                .price(Price.of("20.00"))
                .build();
    }

    public ProductDTO vodka() {
        return ProductDTO.builder()
                .barCode("P_01005")
                .quantity(15)
                .name("Absolvent")
                .price(PriceDTO.of("15.00"))
                .build();
    }

    public ItemDTO wineDTO(Integer quantity) {
        Product wine = wine();
        return ItemDTO.builder()
                .barCode(wine.getBarCode())
                .name(wine.getName())
                .price(PriceDTO.of(wine.getPrice()))
                .quantity(quantity)
                .build();
    }

    public ProductDTO cognac() {
        return ProductDTO.builder()
                .price(PriceDTO.of(230))
                .quantity(10)
                .name("Hennessy")
                .barCode("X_9440")
                .build();
    }

    public ProductDTO whisky() {
        return ProductDTO.builder()
                .price(PriceDTO.of(180))
                .quantity(12)
                .name("Chivas")
                .barCode("X_5161")
                .build();
    }

    public ProductDTO rum() {
        return ProductDTO.builder()
                .price(PriceDTO.of(140))
                .quantity(10)
                .name("Havanna")
                .barCode("X_3101")
                .build();
    }

    public ProductDiscountPair pair(Product wine, Product rafaello) {
        return ProductDiscountPair.builder()
                .firstProduct(wine)
                .secondProduct(rafaello)
                .build();
    }
}
