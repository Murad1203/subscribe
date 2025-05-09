package com.sub.subscriptions.service;

import com.sub.subscriptions.model.Subscription;

import java.util.List;

public interface SubscriptionService {

    void createSubscribe(Subscription subscription);
    void createSubscribeUser(long userId, long subId, int isSubscribe);
    List<Subscription> getSubscribeList(long userId);
    void deleteSubscribe(long id);
    List<Subscription> getTopSubscribe();
}
