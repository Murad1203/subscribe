package com.sub.subscriptions.service;

import com.sub.subscriptions.model.Subscription;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionService {

    void createSubscribe(Subscription subscription);
    void createSubscribeUser(long userId, long subId, int isSubscribe);
    List<Subscription> getSubscribeList(long userId);
    Subscription getSubscription(long id);
    void deleteSubscribe(long id);
    List<Subscription> getTopSubscribe();
}
