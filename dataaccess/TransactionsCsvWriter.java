package dataaccess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;

import business.TransactionsData;

public class TransactionsCsvWriter {
    public void append(String filePath, TransactionsData transactions) throws IOException{
        Path path = Paths.get(filePath);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String line = String.join(",", 
            transactions.getTradedDateTime().format(formatter),
            transactions.getTicker(),
            transactions.getSide().getCode(),
            String.valueOf(transactions.getQuantity()),
            transactions.getTradedUnitPrice().toPlainString(),
            transactions.getInputDateTime().format(formatter)
        ) + "\n";

        if(Files.notExists(path)){
            String header = "traded_datetime,ticker,side,quantity,unit_price,input_datetime";
            Files.writeString(path, header +line, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        }else{
            Files.writeString(path, line, StandardOpenOption.APPEND);
        }      
    }
}
