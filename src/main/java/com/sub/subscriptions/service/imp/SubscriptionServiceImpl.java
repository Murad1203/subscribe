package com.sub.subscriptions.service.imp;

import com.sub.subscriptions.exception.SubscribeNotFoundException;
import com.sub.subscriptions.model.Subscription;
import com.sub.subscriptions.model.User;
import com.sub.subscriptions.repository.SubscriptionRepository;
import com.sub.subscriptions.repository.UserRepository;
import com.sub.subscriptions.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createSubscribe(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public Subscription getSubscription(long id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> new SubscribeNotFoundException("Not found Subscribe with id: " + id));
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new SubscribeNotFoundException("Not found User with id: " + id));
    }

    @Override
    public void createSubscribeUser(long userId, long subId, int isSubscribe) {
        User user = getUser(userId);
        Subscription subscription = getSubscription(subId);
        List<Subscription> subscriptions = user.getSubscribers();
        //Здесь реализовано два функционала подписки и отписки, так дублировать не нужно будет
        if (isSubscribe == 0) {
            subscriptions.add(subscription);
            subscription.setCountSubscribers(subscription.getCountSubscribers() + 1);
        }
        else {
            subscriptions.remove(subscription);
            subscription.setCountSubscribers(subscription.getCountSubscribers() + 1);
        }
        subscriptionRepository.save(subscription);
        userRepository.save(user);
    }

    @Override
    public List<Subscription> getSubscribeList(long userId) {
        return getUser(userId).getSubscribers();
    }

    @Override
    public void deleteSubscribe(long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public List<Subscription> getTopSubscribe() {
        return subscriptionRepository.findAll().stream()
                .sorted(Comparator.comparing(Subscription::getCountSubscribers).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
}
