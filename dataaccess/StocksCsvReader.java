package dataaccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import business.Market;
import business.StocksData;

public class StocksCsvReader {
    private static final String[] REGULATION_HEADER = {"ticker", "product_name", "market", "shares_issued"};

    public static List<StocksData> showStocksList(String filePath){
        StocksCsvReader reader = new StocksCsvReader();
        return reader.readWithHandling(filePath);
    }

    public List<StocksData> readWithHandling(String filePath){
        try{
            return read(filePath);

        }catch(NoSuchFileException e){
            System.out.println("指定されたパスにCSVファイルがありません。");
            return null;

        }catch(IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<StocksData> read(String filePath) throws IOException{
        List<StocksData> stocks = new ArrayList<>();
        Path path = Paths.get(filePath);

        try(BufferedReader bufferedReader = Files.newBufferedReader(path)){
            String[] header = bufferedReader.readLine().split(",");
            if(!Arrays.equals(header, REGULATION_HEADER)){
                throw new IOException("エラー：CSVヘッダーの列順が不正です。");
            }

            String line;
            int rowNum = 2;

            while((line = bufferedReader.readLine()) != null){
                String[] values = line.split(",");
                if(values.length != REGULATION_HEADER.length){
                    throw new IOException("エラー：" + rowNum + "行目の列数が不正です。");
                }
                try{
                    StocksData stock = new StocksData();

                    stock.setTicker(values[0]);
                    stock.setProducName(values[1]);
                    stock.setMarket(Market.fromCode(values[2]));
                    stock.setSharesIssued(Long.parseLong(values[3]));

                    stocks.add(stock);

                }catch(IllegalArgumentException e){
                    throw new IOException("エラー：" + rowNum + "行目のデータ形式が不正です。", e);
                }
                rowNum++;
            }
        }
        return stocks;   
    }
    
}
