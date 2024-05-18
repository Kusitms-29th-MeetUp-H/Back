package com.kusitms29.backendH.domain.category.entity;

import com.kusitms29.backendH.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "user_category")
@Entity
public class UserCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_category_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void updateUserCategory(User user, Category category){
        this.user = user;
        this.category = category;
    }
    public static UserCategory createUserCategory(User user, Category category){
        return UserCategory.builder()
                .user(user)
                .category(category)
                .build();
    }
}
