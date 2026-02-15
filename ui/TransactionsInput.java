package ui;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import domain.Side;
import domain.StocksData;
import domain.TransactionsValidator;

public class TransactionsInput {
    private final Scanner SCANNER;
    private final List<StocksData> STOCKS;
    

    public TransactionsInput(Scanner scanner, List<StocksData> stocks){
        this.SCANNER = scanner;
        this.STOCKS = stocks;
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

    public LocalDateTime askTradedDateTime(){
        while(true){
            System.out.print("取引日時(Ex: 2025-09-04 10:30)> ");
            String tradedDateTimeStr = SCANNER.nextLine().trim();
            LocalDateTime tradedDateTime = TransactionsValidator.checkTradedDateTime(tradedDateTimeStr);
            if(tradedDateTime != null){
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

    public Long askQuantity(){
        while(true){
            System.out.print("取引数量(100株単位)> ");
            String quantityStr = SCANNER.nextLine().trim();
            Long quantity = TransactionsValidator.checkQuantity(quantityStr);
            if(quantity != null){
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
