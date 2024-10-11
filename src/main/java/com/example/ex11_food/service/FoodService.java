package com.example.ex11_food.service;

import com.example.ex11_food.api.response.FoodDetailView;
import com.example.ex11_food.api.response.FoodView;
import com.example.ex11_food.model.FoodEntity;
import com.example.ex11_food.model.MenuEntity;
import com.example.ex11_food.repository.FoodRepository;
import com.example.ex11_food.repository.MenuRepository;
import com.example.ex11_food.request.CreateAndEditFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class FoodService {
    // 데이터 insert하려면 FoodRepository, MenuRepository 둘 다 필요하다.
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Transactional
    public FoodEntity createFood(
            CreateAndEditFoodRequest request // 인자값(api에 받아야하는 값) 만들어놨으니까 사용하기
    ) {
//        FoodEntity food = new FoodEntity(); // 원래 이렇게 했었는데
        FoodEntity food = FoodEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        foodRepository.save(food); // 데이터에 저장

        request.getMenus().forEach((menu) -> {
//            MenuEntity menuEntity = new MenuEntity();
            MenuEntity menuEntity = MenuEntity.builder()
                    .foodId(food.getId())
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createdAt(ZonedDateTime.now())
                    .updatedAt(ZonedDateTime.now())
                    .build();

            menuRepository.save(menuEntity);
        });
        return food; // 화면에 데이터 보여주기 위해서 return
    }

    @Transactional
    public void editFood(
            Long foodId,
            CreateAndEditFoodRequest request
    ) {
        // id 찾기
        FoodEntity food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException("no food")); // 해당되는 데이터가 없으면 터지기 때문에 runtimeException 넣어주기
        food.changeNameAndAddress(request.getName(), request.getAddress()); // id 값이 있으면 name과 address 값 변경시키기 : 원래 setter 쓰는데, createdAt, updatedAt 변하면 안되기 때문에 취약점 없애기 위해

        foodRepository.save(food);

        // 이제 menu테이블도 바꿔줘야 한다.
        List<MenuEntity> menus = menuRepository.findAllByFoodId(foodId); // foodId에 해당하는 메뉴 찾기
        menuRepository.deleteAll(menus); // 해당 메뉴들 모두 삭제

        request.getMenus().forEach((menu) -> {
            MenuEntity menuEntity = MenuEntity.builder()
                    .foodId(food.getId())
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createdAt(ZonedDateTime.now())
                    .updatedAt(ZonedDateTime.now())
                    .build();

            menuRepository.save(menuEntity);
        });
    }

    public void deleteFood(Long foodId) {
        FoodEntity food = foodRepository.findById(foodId).orElseThrow();
        foodRepository.delete(food);

        List<MenuEntity> menus = menuRepository.findAllByFoodId(foodId);
        menuRepository.deleteAll(menus); // 해당 메뉴들 모두 삭제
    }

    public List<FoodView> getAllFoods() {
        List<FoodEntity> foods = foodRepository.findAll();
        // 여러 개 들어온 foods 값을 배열에 넣기
        return foods.stream().map((food) ->  FoodView.builder()
                .id(food.getId())
                .name(food.getName())
                .address(food.getAddress())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build()
        ).toList(); // stream : 컬렉션, 배열 같은 거 효율적으로 쓰기 위해 붙인다.
    }


    public FoodDetailView getFoodDetail(Long foodId) {
        FoodEntity food = foodRepository.findById(foodId).orElseThrow(); // 예외 처리 추가
        List<MenuEntity> menus = menuRepository.findAllByFoodId(foodId);

        return FoodDetailView.builder()
                .id(food.getId()) // Long type
                .name(food.getName())
                .address(food.getAddress())
                .createdAt(food.getCreatedAt())
                .updatedAt(food.getUpdatedAt())
                .menus(menus.stream().map(menu -> // 매개변수 이름 수정
                        FoodDetailView.Menu.builder()
                                .foodId(menu.getFoodId()) // Long type
                                .name(menu.getName())
                                .price(menu.getPrice())
                                .createdAt(menu.getCreatedAt())
                                .updatedAt(menu.getUpdatedAt())
                                .build()
                ).toList()) // Stream을 List로 변환
                .build();
    }


}
