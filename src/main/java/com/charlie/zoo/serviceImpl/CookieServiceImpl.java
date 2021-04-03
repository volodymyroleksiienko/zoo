package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.service.CookieService;
import com.charlie.zoo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CookieServiceImpl implements CookieService {
    private final OrderService orderService;

    @Override
    public void checkCookie(String id, HttpServletResponse httpServletResponse, Model model) {
        if(!id.isEmpty()) {
            OrderInfo orderInfo = orderService.findById(UUID.fromString(id));
            if(orderInfo!=null) {
                model.addAttribute("order", orderInfo);
                return;
            }
        }
        OrderInfo order = new OrderInfo();
        order.setId(UUID.randomUUID());
        order = orderService.save(order);
        httpServletResponse.addCookie(new Cookie("id", order.getId().toString()));
        model.addAttribute("order",order);
    }
}
