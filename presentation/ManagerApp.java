package presentation;
import java.util.List;
import java.util.Scanner;

import business.*;
import dataaccess.StocksCsvReader;
import dataaccess.TransactionsCsvReader;

public class ManagerApp{

    private static final String STOCKS_FILE_PATH = "stocks.csv";
    private static final String TRANSACTIONS_FILE_PATH = "transactions.csv";

    public void run(){
        System.out.println("取引を開始します。");
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while(flag){
            System.out.println("\nメニュー表示");
            System.out.println("A: 銘柄一覧");
            System.out.println("B: 新規登録");
            System.out.println("C: 取引入力");
            System.out.println("D: 取引一覧");
            System.out.println("Q: 終了\n");

            String input = scanner.nextLine();

            switch(input){
                case "A":
                    System.out.println("銘柄一覧を表示します。");
                    List<StocksData> stocks = StocksCsvReader.showStocksList(STOCKS_FILE_PATH);
                    if(stocks != null){
                        StocksTablePrinter.printStocksTable(stocks);
                    }
                    break;
        
                case "B":
                    System.out.println("新規登録を行います。");
                    StocksRegistration stock = new StocksRegistration(scanner, STOCKS_FILE_PATH);
                    stock.execute();
                    break;
                    
                case "C":
                    System.out.println("取引入力を行います。");
                    TransactionsRegistration transaction = new TransactionsRegistration(scanner, STOCKS_FILE_PATH, TRANSACTIONS_FILE_PATH);
                    transaction.execute();
                    break;
                    
                case "D":
                    System.out.println("取引一覧を表示します。");
                    List<TransactionsData> transactions = TransactionsCsvReader.showTransactionsList(STOCKS_FILE_PATH, TRANSACTIONS_FILE_PATH);
                    if(transactions != null){
                        if(transactions.isEmpty()){
                            System.out.println("表示する取引データがありません。");
                        }else{
                            TransactionsTablePrinter.printTransactionsTable(transactions);
                        }
                    }
                    break;

                case "E":
                    System.out.println("保有ポジション一覧を表示します。");
                    List<TransactionsData> transactionsE = TransactionsCsvReader.showTransactionsList(STOCKS_FILE_PATH, TRANSACTIONS_FILE_PATH);
                    if(transactionsE != null){
                        if(transactionsE.isEmpty()){
                            System.out.println("取引データが空のため、表示する保有ポジションがありません。");
                        }else{
                            PositionsCalculator calculator = new PositionsCalculator();
                            List<PositionsData> positions = calculator.calculatePositions(transactionsE);

                            PositionsTablePrinter.printPositionsTable(positions);
                        }
                    }

                    break;
                    
                case "Q":
                    System.out.println("メニューを終了します。");
                    flag = false;
                    break;
                
                default:
                    System.out.println("”" + input + "”はメニューに存在しません。");
                    break;
            }
        }
        scanner.close();
    }
}