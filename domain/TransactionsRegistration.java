package domain;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import repository.StocksCsvReader;
import repository.TransactionsCsvWritter;
import ui.TransactionsInput;

public class TransactionsRegistration {
    private final Scanner SCANNER;
    private final String STOCKS_FILE_PATH;
    private final String TRANSACTIONS_FILE_PATH;

    public TransactionsRegistration(Scanner scanner, String stocksFilePath, String transactionsFilePath){
        this.SCANNER = scanner;
        this.STOCKS_FILE_PATH = stocksFilePath;
        this.TRANSACTIONS_FILE_PATH = transactionsFilePath;
    }

    public void execute(){
        try{
            List<StocksData> existingStocks = StocksCsvReader.showStocksList(STOCKS_FILE_PATH);
            if(existingStocks == null || existingStocks.isEmpty()){
                System.out.println("エラー：銘柄マスタが空か、読み込めませんでした。先に銘柄を登録してください。");
                return;
            }

            TransactionsInput inputHelper = new TransactionsInput(SCANNER, existingStocks);
            String ticker = inputHelper.askTicker();
            LocalDateTime tradedDateTime = inputHelper.askTradedDateTime();
            Side side = inputHelper.askSide();
            Long quantity = inputHelper.askQuantity();
            BigDecimal tradedUnitPrice = inputHelper.askTradedUnitPrice();
            LocalDateTime inputDateTime = LocalDateTime.now();

            TransactionsData transactions = new TransactionsData(tradedDateTime, ticker, side, quantity, tradedUnitPrice, inputDateTime);

            TransactionsCsvWritter writter = new TransactionsCsvWritter();
            writter.append(TRANSACTIONS_FILE_PATH, transactions);
            System.out.println("受け付けました。");

        }catch(IOException e){
            System.out.println("ファイル処理中に問題が発生しました。" + e.getMessage());
        }
    }
    
    
}
