package business;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import dataaccess.StocksCsvReader;
import dataaccess.StocksCsvWritter;
import presentation.StocksInput;

public class StocksRegistration {
    private final Scanner SCANNER;
    private final String FILE_PATH;
    
    public StocksRegistration(Scanner scanner, String filePath){
        this.SCANNER = scanner;
        this.FILE_PATH = filePath;
    }

    public void execute(){
        try{
            StocksCsvReader reader = new StocksCsvReader();
            List<StocksData> stocks = reader.read(FILE_PATH);

            StocksInput inputHelper = new StocksInput(SCANNER);

            String productName = inputHelper.askProductName();
            String ticker = inputHelper.askTicker();
            for(StocksData EXISTING_STOCKS : stocks){
                if(EXISTING_STOCKS.getTicker().equals(ticker)){
                    System.out.println("既に登録されている銘柄です。");
                    return;
                }
            }
            String marketStr = inputHelper.askMarket();
            String sharesIssued = inputHelper.askSharesIssued();

            Market market = Market.fromDisplayName(marketStr);

            StocksCsvWritter writter = new StocksCsvWritter();
            writter.append(FILE_PATH, ticker, productName, market.getCode(), sharesIssued);
            System.out.println("「" + productName + "」の新規登録を行いました。");
        }catch(IOException e){
            System.out.println("エラー：ファイルの処理に失敗しました。" + e.getMessage());
        }
    }
}
