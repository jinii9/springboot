package com.example.ex11_food.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter // db 관련은 setter 안쓰고 몇 개만 change 되는 것(name, address, updatedAt)을
@AllArgsConstructor
@NoArgsConstructor
@Builder // 데이터 가지고오고 변경할 때 더 편하게 해주는
@Entity
@Table(name = "food") // db 이름을 food로 저장하겠다.
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // null값이 있으면 안되므로 Long 사용

    private String name;
    private String address;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    // 원래 setter 쓰는데, createdAt, updatedAt 변하면 안되기 때문에 취약점 없애기 위해
    public void changeNameAndAddress(String name, String address) {
        this.name = name;
        this.address = address;
        this.updatedAt = ZonedDateTime.now();
    }
}
