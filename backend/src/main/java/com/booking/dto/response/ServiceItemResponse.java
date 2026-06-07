package com.booking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ServiceItemResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer duration;

    private String imageUrl;

    private Boolean isActive;
}