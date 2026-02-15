package ui;

import java.util.Scanner;

import domain.StocksValidator;

public class StocksInput {
    private final Scanner SCANNER;
    
    public StocksInput(Scanner scanner){
        this.SCANNER = scanner;
    }

    public String askProductName(){
        while(true){
            System.out.print("銘柄名> ");
            String productName = SCANNER.nextLine().trim();
            if(StocksValidator.checkProductName(productName)){
                return productName;
            }
        }
    }

    public String askTicker(){
        while(true){
            System.out.print("銘柄コード> ");
            String ticker = SCANNER.nextLine().trim();
            if(StocksValidator.checkTicker(ticker)){
                return ticker;
            }
        }
    }

    public String askMarket(){
        while(true){
            System.out.print("上場市場名> ");
            String market = SCANNER.nextLine().trim();
            if(StocksValidator.checkMarket(market)){
                return market;
            }
        }
    }

    public String askSharesIssued(){
        while(true){
            System.out.print("発行済み株式数> ");
            String sharesIssued = SCANNER.nextLine().trim();
            if(StocksValidator.checkSharesIssud(sharesIssued)){
                return sharesIssued;
            }
        }
    }
}