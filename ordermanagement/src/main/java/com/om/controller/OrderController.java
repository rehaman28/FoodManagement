package com.om.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.om.dto.OrderRequestDto;
import com.om.dto.OrderResponseDto;
import com.om.model.OrderStatus;
import com.om.service.OrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

   
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

       
    @PostMapping("/placeorder")    
    public ResponseEntity<OrderResponseDto>placeOrder(@RequestBody OrderRequestDto orderRequest)
    {
        OrderResponseDto placedOrder = orderService.placeorder(orderRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(placedOrder);
    }

    //Get Order by ID
    @GetMapping("/{orderId}")
    public  ResponseEntity<OrderResponseDto> getOrders(@PathVariable (name ="orderId") Long orderId ){
        return  ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(orderId));
    }
    //Get All orders
    @GetMapping()
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(){
        return  ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrder());
    }

    // GET /orders/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUserId(@PathVariable (name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByUserId(userId));
    }
    
    // GET /orders/restaurant/{restaurantId}
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByRestaurantId(@PathVariable (name = "restaurantId") Long restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByRestaurantId(restaurantId));
    }

    // GET /orders/status/{status}
    @GetMapping("/status")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByStatus(
            @RequestParam(name = "orderStatus") OrderStatus orderStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByStatus(orderStatus));
    }

    
    // PATCH /orders/{orderId}/status
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable(name = "orderId") Long orderId,
            @RequestBody OrderStatusRequestDto orderStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderStatus(orderId, orderStatus));
    }



/*Pratically not happening when placed an order
    // PATCH /orders/{orderId}/cancel
    // POST   /orders/{orderId}/items
    // PATCH  /orders/{orderId}/items/{itemId}
    // DELETE /orders/{orderId}/items/{itemId}
*/

    
}
