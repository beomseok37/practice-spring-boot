package hello.core;

import hello.core.member.*;
import hello.core.order.OrderServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;

public class OrderApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        Member member = new Member(memberId,"member1", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(member.getId(), "item1",10000);
        System.out.println(order.getDiscountPrice());
    }
}
