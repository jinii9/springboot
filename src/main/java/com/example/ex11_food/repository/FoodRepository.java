package com.example.ex11_food.repository;

import com.example.ex11_food.model.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
}
