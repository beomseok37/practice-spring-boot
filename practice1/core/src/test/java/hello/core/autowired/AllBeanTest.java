package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    void findAllBean(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member user1 = new Member(1L, "user1", Grade.VIP);

        int discount = discountService.discount(user1, 20000, "rateDiscountPolicy");

        Assertions.assertThat(discount).isEqualTo(2000);
    }

    @RequiredArgsConstructor
    static class DiscountService{
        final Map<String, DiscountPolicy> policyMap;
        final List<DiscountPolicy> discountPolicies;

        public int discount(Member member, int price, String discountCode){
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            int discount = discountPolicy.discount(member, price);

            return discount;
        }
    }
}
