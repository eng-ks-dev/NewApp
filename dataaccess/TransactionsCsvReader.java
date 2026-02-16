package dataaccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.Side;
import business.StocksData;
import business.TransactionsData;

public class TransactionsCsvReader {
    private final String[] REGULATION_HEADER = {"traded_datetime", "ticker", "side", "quantity", "unit_price", "input_datetime"};

    public static List<TransactionsData> showTransactionsList(String stocksFilePath, String transactionsFilePath){
        List<StocksData> stocks = StocksCsvReader.showStocksList(stocksFilePath);
        TransactionsCsvReader reader = new TransactionsCsvReader();
        List<TransactionsData> transactions = reader.readWithHandling(transactionsFilePath);

        Map<String, String> tickerToName = new HashMap<>();
        for(StocksData stock : stocks){
            String ticker = stock.getTicker();
            String productName = stock.getProductName();
            tickerToName.put(ticker, productName);
        }

        for(TransactionsData transaction : transactions){
            String productName = tickerToName.get(transaction.getTicker());
            transaction.setProductName(productName);
        }

        transactions.sort(Comparator.comparing(TransactionsData::getTradedDateTime).reversed());

        return transactions;
    }

    public List<TransactionsData> readWithHandling(String transactionsFilePath){
        try{
            return read(transactionsFilePath);

        }catch(NoSuchFileException e){
            System.out.println("指定されたパスにCSVファイルがありません。");
            return null;

        }catch(IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<TransactionsData> read(String transactionsFilePath) throws IOException{
        List<TransactionsData> transactionsList = new ArrayList<>();
        Path path = Paths.get(transactionsFilePath);

        try(BufferedReader bufferdReader = Files.newBufferedReader(path)){
            String[] header = bufferdReader.readLine().split(",");
            if(!Arrays.equals(header, REGULATION_HEADER)){
                throw new IOException("エラー：ヘッダーの列順が不正です。");
            }

            String line;
            int rowNum = 2;
            while((line = bufferdReader.readLine()) != null){
                String[] values = line.split(",");
                if(values.length != REGULATION_HEADER.length){
                    throw new IOException("エラー：" + rowNum +"行目の列数が不正です。");
                }

                try{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                    LocalDateTime tradedDateTime = LocalDateTime.parse(values[0], formatter);
                    String ticker = values[1];
                    Side side = Side.fromCode(values[2]);
                    Long quantity = Long.parseLong(values[3]);
                    BigDecimal tradedUnitPrice = new BigDecimal(values[4]);
                    LocalDateTime inputDateTime = LocalDateTime.parse(values[5], formatter);

                    TransactionsData transactiosData = new TransactionsData(tradedDateTime, ticker, side, quantity, tradedUnitPrice, inputDateTime);

                    transactionsList.add(transactiosData);

                }catch(IllegalArgumentException | DateTimeParseException e){
                    throw new IOException("エラー：" + rowNum + "行目のデータ形式が不正です。", e);
                }

                rowNum++;
            }
        }  
        return transactionsList; 
    }
}
