package com.mapstruct.explicit.model.entity;

import com.mapstruct.explicit.enumeration.PhoneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "phone_number")
    private String number;
    @Enumerated(EnumType.STRING)
    @Column(name = "phone_type")
    private PhoneType phoneType;
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}