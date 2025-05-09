package com.sub.subscriptions.controller;


import com.sub.subscriptions.model.Subscription;
import com.sub.subscriptions.model.User;
import com.sub.subscriptions.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscribe")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscribe(@PathVariable long id) {
        return ResponseEntity.ok(subscriptionService.getSubscription(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubscribe(@PathVariable long id) {
        subscriptionService.deleteSubscribe(id);
        return ResponseEntity.ok("Успешно удален");
    }

    @PostMapping("/create-subscribe")
    public ResponseEntity<Subscription> createSubscribe(@RequestBody Subscription subscription) {
        subscriptionService.createSubscribe(subscription);
        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/user-on-subscribe")
    public ResponseEntity<String> handleSubscribeAction(
            @RequestParam long userId,
            @RequestParam long subId,
            @RequestParam int isSubscribe) {

        subscriptionService.createSubscribeUser(userId, subId, isSubscribe);

        String action = isSubscribe == 0 ? "подписан" : "отписан";
        return ResponseEntity.ok("Пользователь " + action + " на подписку с ID " + subId);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<Subscription>> getSubscribersUsers(@PathVariable long id) {
        return ResponseEntity.ok(subscriptionService.getSubscribeList(id));
    }

    @GetMapping("/top")
    public ResponseEntity<List<Subscription>> getTopSubcribers() {
        return ResponseEntity.ok(subscriptionService.getTopSubscribe());
    }
}
