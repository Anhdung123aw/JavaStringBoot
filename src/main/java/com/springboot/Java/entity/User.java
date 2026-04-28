package com.springboot.Java.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id; // Đổi String sang Integer hoặc Long cho khớp với INT AUTO_INCREMENT

    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;

    @ManyToMany
    @JoinTable(
            name = "user_roles", // Tên bảng trung gian bạn đã tạo
            joinColumns = @JoinColumn(name = "user_id"), // Tên cột khóa ngoại trỏ tới bảng User
            inverseJoinColumns = @JoinColumn(name = "roles_name") // Tên cột khóa ngoại trỏ tới bảng Role (có chữ 's')
    )
    Set<Role> roles;

}