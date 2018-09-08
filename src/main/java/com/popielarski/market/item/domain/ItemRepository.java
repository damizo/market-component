package com.popielarski.market.item.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ItemRepository extends JpaRepository<Item, Long> {
    /*Optional<Item> findByBarCode(String barCode);*/
}
