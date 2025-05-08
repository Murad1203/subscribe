package com.sub.subscriptions.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @JoinColumn(name = "count-subscribers")
    private int countSubscribers;
    @ManyToOne
    @JoinColumn(name = "user-id")
    private User userId;
}
