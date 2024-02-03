package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {
    DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP라면 10% 할인")
    void rate_o(){
        Member memberVIP = new Member(1L, "memberVIP", Grade.VIP);

        int discount = discountPolicy.discount(memberVIP,20000);
        Assertions.assertThat(discount).isEqualTo(2000);
    }

    @Test
    @DisplayName("BASIC이라면 10% 할인")
    void rate_x(){
        Member memberVIP = new Member(1L, "memberVIP", Grade.BASIC);

        int discount = discountPolicy.discount(memberVIP,20000);
        Assertions.assertThat(discount).isEqualTo(0);
    }
}