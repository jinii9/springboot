package com.example.ex11_food.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodView { // dto
    private Long id;
    private String name;
    private String address;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
