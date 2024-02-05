package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class MyLogger {
    private String uuid;
    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public void log(String message){
        System.out.println("["+uuid+"]"+ "["+url+"] " + message);
    }

    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString();
        System.out.println("["+uuid+"]"+ " request scope bean create");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("["+uuid+"]"+" request scope bean close");
    }
}
