package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest {

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class, PrototypeBean.class);

        SingletonBean singletonBean = ac.getBean(SingletonBean.class);

        int count1 = singletonBean.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        int count2 = singletonBean.logic();
        Assertions.assertThat(count2).isEqualTo(1);

    }
    @Scope("singleton")
    static class SingletonBean{
        @Autowired
        private Provider<PrototypeBean> provider;

        public int logic(){
            PrototypeBean prototypeBean = provider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean{
        private int count=0;

        public int getCount() {
            return count;
        }

        public void addCount(){
            count++;
        }

        @PostConstruct
        public void init(){
            System.out.println("prototypebean.init "+this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("prototypebean.distroy");
        }
    }
}
