package com.popielarski.market.product.domain;

import com.popielarski.market.common.exception.LogicValidationException;
import com.popielarski.market.common.utils.PageableHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProductFacade {

    private ProductRepository productRepository;
    private ProductFactory productFactory;
    private ProductMapper productMapper;

    public ProductDTO createProduct(ProductDTO productDTO) {
        productRepository.findByBarCode(productDTO.getBarCode())
                .ifPresent((product) -> new LogicValidationException(String.format("Product with barCode %s already exists",
                        product.getBarCode())));

        Product product = productFactory.create(productDTO.getName(), productDTO.getPrice(), productDTO.getQuantity(), productDTO.getBarCode());
        log.debug("New product is about to create: {}", product);

        product = productRepository.save(product);
        log.debug("Persisted product: {}", product);

        return productMapper.toDTO(product);
    }

    public ProductDTO addQuantityOfProduct(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new LogicValidationException(String.format("Product with barCode %s already exists",
                        productId)));
        log.debug("Product with id {} has been found: {}", productId, product);

        product.increaseQuantity(quantity);
        Product productAfterUpdate = productRepository.save(product);
        log.debug("Updated product: {}", productAfterUpdate);

        return productMapper.toDTO(productAfterUpdate);
    }

    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        log.debug("Fetched products: {}", products);

        return PageableHelper.of(products.getContent().stream().map(productMapper::toDTO)
                .collect(Collectors.toList()), pageable);
    }
}
