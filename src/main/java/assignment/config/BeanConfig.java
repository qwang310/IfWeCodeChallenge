package assignment.config;

import assignment.dao.PlayerRank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class BeanConfig {

    @Bean
    public ConcurrentHashMap<String, PlayerRank> rankMap() {
        return new ConcurrentHashMap<String, PlayerRank>();
    }



}
