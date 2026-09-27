package com.example.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    /**
     * Lấy danh sách thực đơn theo ID nhà hàng.
     * Sử dụng @Cacheable để lưu kết quả vào cache với tên "restaurantMenu" và key là tham số "id".
     */
    @Cacheable(value = "restaurantMenu", key = "#id")
    public List<MenuItem> getMenuByRestaurantId(Long id) {
        // In log giả lập truy vấn DB (Chỉ xuất hiện khi Cache Miss)
        System.out.println(">>> [CACHE MISS] Truy vấn Database lấy thực đơn cho nhà hàng ID: " + id);

        // Mô phỏng độ trễ database (3 giây)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Lỗi giả lập độ trễ Database", e);
        }

        // Dữ liệu mẫu mô phỏng từ database
        List<MenuItem> menuList = new ArrayList<>();
        if (Long.valueOf(101).equals(id)) {
            menuList.add(new MenuItem(1L, 101L, "Phở Bò", 55000.0));
            menuList.add(new MenuItem(2L, 101L, "Bún Chả", 60000.0));
        } else {
            menuList.add(new MenuItem(3L, id, "Món Ăn Mẫu", 50000.0));
        }

        return menuList;
    }

    // Class DTO mô phỏng đối tượng MenuItem
    public static class MenuItem {
        private Long id;
        private Long restaurantId;
        private String dishName;
        private Double price;

        public MenuItem() {}

        public MenuItem(Long id, Long restaurantId, String dishName, Double price) {
            this.id = id;
            this.restaurantId = restaurantId;
            this.dishName = dishName;
            this.price = price;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(Long restaurantId) {
            this.restaurantId = restaurantId;
        }

        public String getDishName() {
            return dishName;
        }

        public void setDishName(String dishName) {
            this.dishName = dishName;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }
}