package com.popielarski.market.configuration;

import com.popielarski.market.discount.boughttogether.BoughtTogetherDiscount;
import com.popielarski.market.discount.boughttogether.ProductDiscountPair;
import com.popielarski.market.item.domain.ItemDTO;
import com.popielarski.market.item.domain.MultiItemsDiscount;
import com.popielarski.market.product.Product;

import java.util.Set;

public class DataContainer {
    public Product cola() {
        return Product.builder()
                .barCode("P_00001")
                .quantity(50)
                .name("Cola 2l")
                .price(50L)
                .build();
    }

    public MultiItemsDiscount multiItemsDiscount(Set<Product> products){
        return MultiItemsDiscount.builder()
                .description("If you buy more than one item of this product, your price will be decreased by price of one quantity of this product")
                .products(products)
                .build();
    }

    public BoughtTogetherDiscount boughtTogetherDiscount(Set<ProductDiscountPair> productPairs){
        return BoughtTogetherDiscount.builder()
                .description("Each price of pair's product will be divided by 2")
                .productPairs(productPairs)
                .build();
    }

    public ItemDTO colaItemDTO(Integer quantity) {
        Product cola = cola();
        return ItemDTO.builder()
                .name(cola.getName())
                .price(cola.getPrice())
                .quantity(quantity)
                .build();
    }

    public Product snickers() {
        return Product.builder()
                .barCode("P_00002")
                .quantity(50)
                .name("Snickers")
                .price(40L)
                .build();
    }

    public ItemDTO snickersItemDTO(Integer quantity) {
        Product snickers = snickers();
        return ItemDTO.builder()
                .name(snickers.getName())
                .price(snickers.getPrice())
                .quantity(quantity)
                .build();
    }

    public Product rafaello() {
        return Product.builder()
                .barCode("P_00003")
                .quantity(50)
                .name("Rafaello")
                .price(70L)
                .build();
    }

    public ItemDTO rafaelloDTO(Integer quantity) {
        Product rafaello = rafaello();
        return ItemDTO.builder()
                .barCode(rafaello.getBarCode())
                .name(rafaello.getName())
                .price(rafaello.getPrice())
                .quantity(quantity)
                .build();
    }

    public Product flakes() {
        return Product.builder()
                .barCode("P_00004")
                .quantity(20)
                .name("Oat flakes")
                .price(90L)
                .build();
    }

    public ItemDTO flakesDTO(Integer quantity) {
        Product flakes = flakes();
        return ItemDTO.builder()
                .barCode(flakes.getBarCode())
                .name(flakes.getName())
                .price(flakes.getPrice())
                .quantity(quantity)
                .build();
    }

    public Product wine() {
        return Product.builder()
                .barCode("P_00005")
                .quantity(30)
                .name("Wine")
                .price(350L)
                .build();
    }

    public ItemDTO wineDTO(Integer quantity) {
        Product wine = wine();
        return ItemDTO.builder()
                .barCode(wine.getBarCode())
                .name(wine.getName())
                .price(wine.getPrice())
                .quantity(quantity)
                .build();
    }

    public ProductDiscountPair pair(Product wine, Product rafaello) {
        return ProductDiscountPair.builder()
                .firstProduct(wine)
                .secondProduct(rafaello)
                .build();
    }
}
