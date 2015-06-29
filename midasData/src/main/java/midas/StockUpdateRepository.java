package midas;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.awt.print.Pageable;

public interface StockUpdateRepository extends MongoRepository<StockUpdate, String> {

    public StockUpdate findByTicker(String ticker);

}
