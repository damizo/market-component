package com.popielarski.market.product;

import com.popielarski.market.product.domain.ProductDTO;
import com.popielarski.market.product.domain.ProductFacade;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/products")
@Slf4j
@AllArgsConstructor
public class ProductController {

    private ProductFacade productFacade;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO create(@RequestBody ProductDTO productDTO){
        return productFacade.createProduct(productDTO);
    }

    @PutMapping(value = "/{productId}")
    public ProductDTO increaseQuantity(@PathVariable Long productId, @RequestParam Integer quantity){
        return productFacade.addQuantityOfProduct(productId, quantity);
    }

    @GetMapping
    public Page<ProductDTO> findAll(Pageable pageable){
        return productFacade.findAllProducts(pageable);
    }
}
