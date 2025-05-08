package com.sub.subscriptions.service;

import java.util.List;

public interface SubscriptionService {
    void createSubscribeUser();
    List<SubscriptionService> getSubscribeList(long userId);
    void deleteSubscribe(long id);
}
