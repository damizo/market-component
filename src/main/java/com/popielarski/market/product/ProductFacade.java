package com.popielarski.market.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@Slf4j
public class ProductFacade {
    private ProductRepository productRepository;

    public Page<ProductDTO> findProducts(){
        return null;
    }

    public ProductDTO createProduct(ProductDTO productDTO){
        return null;
    }

    public ProductDTO addQuantityOfProduct(Long productId){
        return null;
    }

}
