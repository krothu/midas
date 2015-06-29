package midas;

import com.bnymellon.amt.tapestry.client.core.TapestryDataServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

@Controller
public class TapestryClient {

    float applStock = 40.0f;
    float msftStock = 43.0f;
    float googStock = 41.0f;

    static String AAPL = "AAPL";
    static String GOOG = "GOOG";
    static String MSFT = "MSFT";

    @Autowired
    private StockUpdateRepository stockUpdateRepository;

    @RequestMapping("/exampleStock")
    @ResponseBody
    public Object getStockQuote(@RequestParam(value = "ticker", required = false, defaultValue = "YHOO") String ticker) throws UnsupportedEncodingException {

        Object response = callTapestryForYahooData(ticker);

        return response;
    }

    private Object callTapestryForYahooData(String ticker) throws UnsupportedEncodingException {
        String guid = "3b25bc9a-362d-4963-ab57-7cb46ed9c823";
        String key = "FrBgJWEFLSAbiMTzzDzwiwE6cDOGRq82";
        String category = "Stock Data";
        String mode = "GET";

        String endOfQuery = "&env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json";
        String startOfQuery = "q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in";

        String dataInQuery = "(\"" + ticker + "\")";

        String data = startOfQuery + URLEncoder.encode(dataInQuery, "UTF-8") + endOfQuery;

        return TapestryDataServiceFactory.getInstance().invokeService(guid, key, "any", category, mode, data);
    }

    @Scheduled(fixedDelay = 8000)
    public void publishSingleStock() throws InterruptedException {
        String guid = "a487124b-714a-4b22-a5a5-cc0afe65bb72";

        float minX = 50.0f;
        float maxX = 60.0f;

        Random rand = new Random();

        float lastAppl = applStock;
        float lastMsft = msftStock;
        float lastGoog = googStock;

        applStock = rand.nextFloat() * (maxX - minX) + minX;
        msftStock = rand.nextFloat() * (maxX - minX) + minX;
        googStock = rand.nextFloat() * (maxX - minX) + minX;

        publishArrayStock();

        String aaplData = "{\"auth_token\" : \"YOUR_AUTH_TOKEN\", \"current\": \"" + String.format("%.2f", applStock) + "\", \"last\": \""+lastAppl+"\", \"ticker\": \"AAPL\"}";
        publishIntoTapestry(guid, aaplData);
        System.out.println("Publised AAPL");

        Thread.sleep(2000);

        String googData = "{\"auth_token\" : \"YOUR_AUTH_TOKEN\", \"current\": \"" + String.format("%.2f", msftStock) + "\", \"last\": \""+lastGoog+"\", \"ticker\": \"GOOG\"}";
        publishIntoTapestry(guid, googData);
        System.out.println("Publised GOOG");

        Thread.sleep(2000);

        String msftData = "{\"auth_token\" : \"YOUR_AUTH_TOKEN\", \"current\": \"" + String.format("%.2f", googStock) + "\", \"last\": \""+lastMsft+"\", \"ticker\": \"MSFT\"}";
        publishIntoTapestry(guid, msftData);
        System.out.println("Publised MSFT");

    }


    public void publishArrayStock() {
        String guid = "6526cf9d-6079-413b-b3d4-d1aa5dd5af51";
        Random random = new Random();

        String aapl = "{\"label\": \"AAPL\",\"value\":  \"" + String.format("%.2f", applStock) + "\"}";
        String msft = "{\"label\": \"MSFT\",\"value\":  \"" + String.format("%.2f", msftStock) + "\"}";
        String goog = "{\"label\": \"GOOG\",\"value\":  \"" + String.format("%.2f", googStock) + "\"}";

        String data = "{\"auth_token\" : \"YOUR_AUTH_TOKEN\",\"items\": [" + aapl + "," + msft + "," + goog + "] }";
        publishIntoTapestry(guid, data);

        System.out.println("Published List");
    }

    private void publishIntoTapestry(String guid, String data) {

        String key = "FrBgJWEFLSAbiMTzzDzwiwE6cDOGRq82";
        String category = "Stock Data";
        String mode = "PUT";

        Object response = TapestryDataServiceFactory.getInstance().invokeService(guid, key, "any", category, mode, data);
    }

    @Scheduled(fixedDelay = 2000)
    public void getStock() throws UnsupportedEncodingException, InterruptedException {

        updateStock(AAPL);
        Thread.sleep(1000);
        updateStock(GOOG);
        Thread.sleep(1000);
        updateStock(MSFT);
    }

    private void updateStock(String ticker) throws UnsupportedEncodingException {

        Object tapestryData = callTapestryForYahooData(ticker);

        StockUpdate stockToUpdate = stockUpdateRepository.findByTicker(ticker);
        if (stockToUpdate == null) {
            stockToUpdate = new StockUpdate(ticker, String.valueOf(tapestryData));
        } else {
            stockToUpdate.data = String.valueOf(tapestryData);
        }
        stockUpdateRepository.save(stockToUpdate);

        System.out.println("Updating From Yahoo: " + stockToUpdate.getId() + ", ticker: " + stockToUpdate.ticker);
    }
}
