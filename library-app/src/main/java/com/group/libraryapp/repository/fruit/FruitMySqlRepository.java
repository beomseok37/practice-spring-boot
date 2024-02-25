package com.group.libraryapp.repository.fruit;

import com.group.libraryapp.dto.fruit.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.FruitStatProjection;
import com.group.libraryapp.dto.fruit.FruitStatResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class FruitMySqlRepository implements FruitRepository {

    private final JdbcTemplate jdbcTemplate;

    public FruitMySqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<FruitStatProjection> rowMapper = BeanPropertyRowMapper.newInstance(FruitStatProjection.class);

    @Override
    public void registerFruit(FruitCreateRequest request) {
        String sql = "INSERT INTO fruit(name,warehousing_date,price,is_sold) VALUES(?,?,?,?)";
        jdbcTemplate.update(sql, request.getName(), request.getWarehousingDate(), request.getPrice(), false);
    }

    @Override
    public void updateFruit(Long fruitId) {
        if(isFruitNotExist(fruitId)){
            throw new IllegalArgumentException("해당 id에 일치하는 과일이 없습니다.");
        }

        String sql = "UPDATE fruit SET is_sold=true WHERE id = ?";
        jdbcTemplate.update(sql,fruitId);
    }

    @Override
    public FruitStatResponse findPriceInfoByName(String fruitName) {
        String readSql = "SELECT SUM(price) as price,is_sold FROM fruit GROUP BY is_sold";
        List<FruitStatProjection> result = jdbcTemplate.query(readSql, rowMapper);

        return FruitStatResponse.createFruitStatDB(result);
    }

    @Override
    public boolean isFruitNotExist(long id) {
        String readSql = "SELECT * FROM fruit WHERE id = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();
    }
}
