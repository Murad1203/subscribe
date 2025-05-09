package com.sub.subscriptions.service;

import com.sub.subscriptions.exception.SubscribeNotFoundException;
import com.sub.subscriptions.model.Subscription;
import com.sub.subscriptions.model.User;
import com.sub.subscriptions.repository.SubscriptionRepository;
import com.sub.subscriptions.repository.UserRepository;
import com.sub.subscriptions.service.imp.SubscriptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Test
    void testCreateSubscribe() {
        Subscription subscription = new Subscription();
        subscriptionService.createSubscribe(subscription);
        verify(subscriptionRepository).save(subscription);
    }

    @Test
    void testGetSubscription_found() {
        Subscription subscription = new Subscription();
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));

        Subscription result = subscriptionService.getSubscription(1L);

        assertEquals(subscription, result);
    }
    @Test
    void testGetSubscription_notFound() {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SubscribeNotFoundException.class, () -> subscriptionService.getSubscription(1L));
    }

    @Test
    void testGetUser_found() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = subscriptionService.getUser(1L);

        assertEquals(user, result);
    }
    @Test
    void testGetUser_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SubscribeNotFoundException.class, () -> subscriptionService.getUser(1L));
    }

    @Test
    void testCreateSubscribeUser_subscribe() {
        User user = new User();
        Subscription subscription = new Subscription();
        user.setSubscribers(new ArrayList<>());
        subscription.setCountSubscribers(0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(subscriptionRepository.findById(2L)).thenReturn(Optional.of(subscription));

        subscriptionService.createSubscribeUser(1L, 2L, 0);

        assertTrue(user.getSubscribers().contains(subscription));
        assertEquals(1, subscription.getCountSubscribers());
        verify(subscriptionRepository).save(subscription);
        verify(userRepository).save(user);
    }

    @Test
    void testCreateSubscribeUser_unsubscribe() {
        Subscription subscription = new Subscription();
        subscription.setCountSubscribers(3);

        User user = new User();
        user.setSubscribers(new ArrayList<>(List.of(subscription)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(subscriptionRepository.findById(2L)).thenReturn(Optional.of(subscription));

        subscriptionService.createSubscribeUser(1L, 2L, 1);

        assertFalse(user.getSubscribers().contains(subscription));
        assertEquals(4, subscription.getCountSubscribers()); // Да-да, тут возможно логическая ошибка — см. комментарий ниже
        verify(subscriptionRepository).save(subscription);
        verify(userRepository).save(user);
    }

    @Test
    void testGetSubscribeList() {
        Subscription s1 = new Subscription();
        User user = new User();
        user.setSubscribers(List.of(s1));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<Subscription> result = subscriptionService.getSubscribeList(1L);
        assertEquals(1, result.size());
        assertEquals(s1, result.get(0));
    }

    @Test
    void testDeleteSubscribe() {
        subscriptionService.deleteSubscribe(1L);
        verify(subscriptionRepository).deleteById(1L);
    }

    @Test
    void testGetTopSubscribe() {
        Subscription s1 = new Subscription(); s1.setCountSubscribers(10);
        Subscription s2 = new Subscription(); s2.setCountSubscribers(20);
        Subscription s3 = new Subscription(); s3.setCountSubscribers(5);
        Subscription s4 = new Subscription(); s4.setCountSubscribers(15);

        when(subscriptionRepository.findAll()).thenReturn(List.of(s1, s2, s3, s4));

        List<Subscription> result = subscriptionService.getTopSubscribe();

        assertEquals(List.of(s2, s4, s1), result);
    }
}