package com.example.ex11_food.repository;

import com.example.ex11_food.model.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    public List<MenuEntity> findAllByFoodId(Long foodId);
}
