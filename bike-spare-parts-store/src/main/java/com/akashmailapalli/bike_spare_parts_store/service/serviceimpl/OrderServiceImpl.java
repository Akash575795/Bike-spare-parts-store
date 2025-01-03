package com.akashmailapalli.bike_spare_parts_store.service.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.akashmailapalli.bike_spare_parts_store.dto.OrderDTO;
import com.akashmailapalli.bike_spare_parts_store.model.Order;
import com.akashmailapalli.bike_spare_parts_store.model.OrderStatus;
import com.akashmailapalli.bike_spare_parts_store.model.Product;
import com.akashmailapalli.bike_spare_parts_store.model.User;
import com.akashmailapalli.bike_spare_parts_store.repository.OrderRepository;
import com.akashmailapalli.bike_spare_parts_store.repository.ProductRepository;
import com.akashmailapalli.bike_spare_parts_store.repository.UserRepository;
import com.akashmailapalli.bike_spare_parts_store.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


     public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
                            UserRepository userRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < orderDTO.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        product.setStock(product.getStock() - orderDTO.getQuantity());
        productRepository.save(product);

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(orderDTO.getQuantity());
        order.setStatus(OrderStatus.PLACED);
        order.setDeliveryAddress(orderDTO.getDeliveryAddress());
        order.setPhoneNumber(orderDTO.getPhoneNumber());

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        Order updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }
}

