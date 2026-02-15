package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class StocksCsvWritter {
    public void append(String filePath, String ticker, String productName, String market, String sharedIssued) throws IOException{
        Path path = Paths.get(filePath);
        String line = String.join(",", ticker, productName, market, sharedIssued) + "\n";

        Files.writeString(path, line, StandardOpenOption.APPEND);
    }
}
