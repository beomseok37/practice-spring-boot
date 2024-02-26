package com.group.libraryapp.repository.fruit;

import com.group.libraryapp.domain.fruit.Fruit;
import com.group.libraryapp.dto.fruit.FruitStatProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FruitJpaRepository extends JpaRepository<Fruit, Long> {

    @Query(value = "SELECT new com.group.libraryapp.dto.fruit.FruitStatProjection(SUM(f.price),f.isSold) FROM Fruit f GROUP BY f.isSold")
    List<FruitStatProjection> findByIsSold();

    Long countByName(String name);

    List<Fruit> findByPriceGreaterThanEqual(long price);

    List<Fruit> findByPriceLessThanEqual(long price);
}

