package dev.beomseok.jpashop.service;

import dev.beomseok.jpashop.domain.Address;
import dev.beomseok.jpashop.domain.Member;
import dev.beomseok.jpashop.domain.Order;
import dev.beomseok.jpashop.domain.OrderStatus;
import dev.beomseok.jpashop.domain.item.Book;
import dev.beomseok.jpashop.exception.NotEnoughStockException;
import dev.beomseok.jpashop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private EntityManager em;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("놀면서 돈벌기", 10000, 10);
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(),book.getId(),orderCount);

        //then
        Order order = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, order.getStatus(),"상품 주문시 상태는 ORDER");
        assertEquals(1, order.getOrderItems().size(),"주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * 2, order.getTotalPrice(),"주문 가격은 가격 * 수량이다.");
        assertEquals(8, book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("놀면서 돈벌기", 10000, 10);

        //when
        int orderCount = 11;

        //then
        assertThrows(NotEnoughStockException.class,() -> {
                orderService.order(member.getId(),book.getId(),orderCount);
        });
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("놀면서 돈벌기",10000,10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(),book.getId(),orderCount);
        assertEquals(8,book.getStockQuantity(), "주문 시 재고 수량 감소");

        //when
        orderService.cancel(orderId);

        //then
        Order order = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL,order.getStatus(), "주문 취소 시 주문 상태는 cancel");
        assertEquals(10,book.getStockQuantity(), "주문 취소 시 재고 수량 증가");
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123")); em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

}