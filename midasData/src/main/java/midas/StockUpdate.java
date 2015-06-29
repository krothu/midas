package midas;

import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Comparator;
import java.util.UUID;

public class StockUpdate implements Comparator{

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public String ticker;
    public String data;
    public Instant insertTime;

    public StockUpdate() {
    }

    public StockUpdate(String ticker, String data) {
        this.ticker = ticker;
        this.data = data;
        this.insertTime = Instant.now();
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return String.format(
                "StockUpdate[id=%s, ticker='%s', data='%s']",
                id, ticker, data);
    }

    @Override
    public int compare(Object obj1, Object obj2) {
        StockUpdate p1 = (StockUpdate) obj1;
        StockUpdate p2 = (StockUpdate) obj2;

        if (p1.insertTime.toEpochMilli() > p2.insertTime.toEpochMilli()) {
            return 1;
        } else if (p1.insertTime.toEpochMilli() < p2.insertTime.toEpochMilli()){
            return -1;
        } else {
            return 0;
        }
    }
}
