package midas;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TickerRepository extends MongoRepository<Ticker, String> {

    public Ticker findByName(String firstName);
}
