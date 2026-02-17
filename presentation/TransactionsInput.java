package presentation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import business.Side;
import business.StocksData;
import business.TransactionsData;
import business.TransactionsValidator;

public class TransactionsInput {
    private final Scanner SCANNER;
    private final List<StocksData> STOCKS;
    private final List<TransactionsData> HISTORY;
    

    public TransactionsInput(Scanner scanner, List<StocksData> stocks, List<TransactionsData> history){
        this.SCANNER = scanner;
        this.STOCKS = stocks;
        this.HISTORY = history;
    }

    public String askTicker(){
        while(true){
            System.out.print("銘柄コード> ");
            String ticker = SCANNER.nextLine().trim();
            if(TransactionsValidator.checkTicker(ticker, STOCKS)){
                return ticker;
            }
        }
    }

    public LocalDateTime askTradedDateTime(String ticker){
        while(true){
            System.out.print("取引日時(Ex: 2025-09-04 10:30)> ");
            String tradedDateTimeStr = SCANNER.nextLine().trim();
            LocalDateTime tradedDateTime = TransactionsValidator.checkTradedDateTime(tradedDateTimeStr);
            if(tradedDateTime != null && TransactionsValidator.checkDateTimeOrder(ticker, tradedDateTime, HISTORY)){
                return tradedDateTime;
            }
        }
    }

    public Side askSide(){
        while(true){
            System.out.print("売買区分(Buy / Sell)> ");
            String side = SCANNER.nextLine().trim();
            if(TransactionsValidator.checkSide(side)){
                return Side.fromDisplayName(side);
            }
        }
    }

    public Long askQuantity(String ticker,Side side){
        while(true){
            System.out.print("取引数量(100株単位)> ");
            String quantityStr = SCANNER.nextLine().trim();
            Long quantity = TransactionsValidator.checkQuantity(quantityStr);
            if(quantity != null && TransactionsValidator.checkAvailableQuantity(ticker, side, quantity, HISTORY)){
                return quantity;
            }
        }
    }

    public BigDecimal askTradedUnitPrice(){
        while(true){
            System.out.print("取引単価> ");
            String tradedUnitPriceStr = SCANNER.nextLine().trim();
            BigDecimal tradedUnitPrice = TransactionsValidator.checkTradedUnitPrice(tradedUnitPriceStr);
            if(tradedUnitPrice != null){
                return tradedUnitPrice;
            }
        }
    }
}
