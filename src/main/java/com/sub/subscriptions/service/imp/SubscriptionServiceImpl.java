package com.sub.subscriptions.service.imp;

import com.sub.subscriptions.exception.SubscribeNotFoundException;
import com.sub.subscriptions.model.Subscription;
import com.sub.subscriptions.model.User;
import com.sub.subscriptions.repository.SubscriptionRepository;
import com.sub.subscriptions.repository.UserRepository;
import com.sub.subscriptions.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;


    /**
     * Сохраняет новую подписку в базу данных.
     */
    @Override
    public void createSubscribe(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    /**
     * Возвращает подписку по ID или выбрасывает исключение.
     */
    @Override
    public Subscription getSubscription(long id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> new SubscribeNotFoundException("Not found Subscribe with id: " + id));
    }

    /**
     * Возвращает пользователя по ID или выбрасывает исключение.
     */
    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new SubscribeNotFoundException("Not found User with id: " + id));
    }


    /**
     * Подписывает или отписывает пользователя от подписки.
     * @param userId ID пользователя
     * @param subId ID подписки
     * @param isSubscribe 0 — подписка, 1 — отписка
     */
    @Override
    public void createSubscribeUser(long userId, long subId, int isSubscribe) {
        User user = getUser(userId);
        Subscription subscription = getSubscription(subId);

        List<Subscription> subscriptions = user.getSubscribers();
        boolean modified = false;

        if (isSubscribe == 0) {
            if (!subscriptions.contains(subscription)) {
                subscriptions.add(subscription);
                subscription.setCountSubscribers(subscription.getCountSubscribers() + 1);
                modified = true;
            }
        } else {
            if (subscriptions.remove(subscription)) {
                subscription.setCountSubscribers(Math.max(0, subscription.getCountSubscribers() - 1));
                modified = true;
            }
        }

        if (modified) {
            subscriptionRepository.save(subscription);
            userRepository.save(user);
        }
    }

    /**
     * Получает список подписок пользователя.
     */
    @Override
    public List<Subscription> getSubscribeList(long userId) {
        return getUser(userId).getSubscribers();
    }

    /**
     * Удаляет подписку по ID.
     */
    @Override
    public void deleteSubscribe(long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new SubscribeNotFoundException("Подписка с id=" + id + " не найдена для удаления");
        }
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
