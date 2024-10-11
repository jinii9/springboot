package com.example.ex11_food.api;

import com.example.ex11_food.api.response.FoodDetailView;
import com.example.ex11_food.api.response.FoodView;
import com.example.ex11_food.model.FoodEntity;
import com.example.ex11_food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ex11_food.request.CreateAndEditFoodRequest;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class foodApi {
    @Autowired
    FoodService foodService;

    @GetMapping("/foods")
    public List<FoodView> getFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/food/{foodId}")
    public FoodDetailView viewFood(
            @PathVariable("foodId") Long foodId
    ) {
        return foodService.getFoodDetail(foodId);
    }

    @PostMapping("/food")
    public FoodEntity postFood(
            @RequestBody CreateAndEditFoodRequest request // dto 만들어주기 : 여기서 받아주는건 모두 dto라고 생각
    ) {
        return foodService.createFood(request);
//        return "postFood name: " + request.getName() + ", address: " + request.getAddress() + ",메뉴[0]" + request.getMenus().get(0).getName();

    }

    @PutMapping("/food/{foodId}")
    public void editFood(
            @PathVariable("foodId") Long foodId,
            @RequestBody CreateAndEditFoodRequest request
    ) {
        foodService.editFood(foodId, request);
//        return "editFood" + foodId + " name: " + request.getName() + ", address: " + request.getAddress();
    }

    @DeleteMapping("/food/{foodId}")
    public void deleteFood(
            @PathVariable("foodId") Long foodId
    ) {
        foodService.deleteFood(foodId);
//        return "delete" + foodId;
    }
}
