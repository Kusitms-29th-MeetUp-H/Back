package com.kusitms29.backendH.domain.category.domain;

import com.kusitms29.backendH.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "category")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;


    //--UserCategory 연관개체--
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    //--
}
