package midas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MidasDataApplication.class)
@WebAppConfiguration
public class TapestryClientTest {

    @Autowired
    private TickerRepository tickerRepository;

    @Autowired
    private StockUpdateRepository stockUpdateRepository;

    @Test
    public void testTapestryClientReturnsData() throws UnsupportedEncodingException {

        TapestryClient tapestryClient = new TapestryClient();

        Object returnedData = tapestryClient.getStockQuote("YHOO");

        System.out.println(returnedData);

    }

    /*@Test
    public void testMongo(){
        repository.deleteAll();

        // save a couple of customers
        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }
    }*/

    @Test
      public void testMongoTickers(){

        tickerRepository.deleteAll();

        tickerRepository.save(new Ticker("AAPL"));
        tickerRepository.save(new Ticker("GOOG"));
        tickerRepository.save(new Ticker("MSFT"));

        System.out.println("Tickers found with findAll():");
        System.out.println("-------------------------------");
        for (Ticker ticker : tickerRepository.findAll()) {
            System.out.println(ticker);
        }
        System.out.println();
    }


    @Test
    public void testMongoStockUpdateGetFoobaa2(){

        stockUpdateRepository.deleteAll();

        stockUpdateRepository.save(new StockUpdate("AAPL", "foobaa"));
        stockUpdateRepository.save(new StockUpdate("AAPL","foobaa1"));
        stockUpdateRepository.save(new StockUpdate("AAPL", "foobaa2"));


        System.out.println("Stock Updates found with findAll():");
        System.out.println("-------------------------------");
        for (StockUpdate record : stockUpdateRepository.findAll()) {
            System.out.println(record);
        }
        System.out.println();


        System.out.println("Stock Updates Get Last entered:");
        System.out.println("-------------------------------");
        StockUpdate queryStockUpdate = new StockUpdate();
//        List<StockUpdate> all = stockUpdateRepository.findAll().sort()
//
//
//                new Comparator<StockUpdate>() {
//            @Override
//            public int compare(StockUpdate o1, StockUpdate o2) {
//
//                if (o1.insertTime.toEpochMilli() > o2.insertTime.toEpochMilli()) {
//                    return 1;
//                } else if (o1.insertTime.toEpochMilli() < o2.insertTime.toEpochMilli()){
//                    return -1;
//                }
//
//
//            }
//        });
//        for (StockUpdate record : all) {
//            System.out.println(record);
//        }
        System.out.println();
    }
}
