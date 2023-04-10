package com.timedeal.timedeal;


import com.timedeal.timedeal.facade.OptimisticLockFacade;
import com.timedeal.timedeal.item.repository.ItemRepository;
import com.timedeal.timedeal.order.repository.OrderRepository;
import com.timedeal.timedeal.order.repository.RedisLockRepository;
import com.timedeal.timedeal.order.service.*;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Autowired
    private final ItemRepository itemRepository;

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final RedisLockRepository redisLockRepository;

    @Autowired
    private final RedissonClient redissonClient;

    @Bean
    public OrderService orderService() {
//        return new OrderServiceImpl(itemRepository, orderRepository);
//        return new SynchronizedOrderServiceImpl(itemRepository, orderRepository);
//        return new PessimisticLockOrderServiceImpl(itemRepository, orderRepository);
//        return new OptimisticLockOrderServiceImpl(itemRepository, orderRepository);
//        return new LettuceLockOrderServiceImpl(redisLockRepository, itemRepository, orderRepository);
        return new RedissonLockOrderServiceImpl(redissonClient, itemRepository, orderRepository);
    }
}
