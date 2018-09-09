package com.popielarski.market.discount.domain;


import com.popielarski.market.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DISCOUNTS")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Discount extends BaseEntity {

    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "TYPE", updatable = false)
    @Enumerated(EnumType.STRING)
    protected DiscountType type;

    @Column(name = "UNIT", updatable = false)
    @Enumerated
    protected DiscountUnit unit;

}
