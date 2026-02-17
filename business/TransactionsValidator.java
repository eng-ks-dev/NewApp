package business;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TransactionsValidator {
    public static boolean checkTicker(String ticker, List<StocksData> stocks){
        for(StocksData stock : stocks){
            if(stock.getTicker().equals(ticker)){
                return true;
            }
        }
        System.out.println("エラー：入力された銘柄コードはマスタに存在しません。");
            return false;
    }

    public static LocalDateTime checkTradedDateTime(String tradedDateTimeStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try{
            String datePart = tradedDateTimeStr.split(" ")[0];
            String[] parts = datePart.split("-");
            if(parts.length == 3){
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                
                if(!Year.of(year).isLeap() && month == 2 && day == 29){
                    System.out.println("エラー：" + year + "は閏年ではありません。");
                    return null;
                }
            }
        }catch(Exception e){
            System.out.println("エラー：日付の形式が正しくありません。「yyyy-MM-dd HH:mm」の形式で入力してください。");
            return null;
        }

        LocalDateTime tradedDateTime;
        try{
            tradedDateTime = LocalDateTime.parse(tradedDateTimeStr, formatter);
        }catch(DateTimeParseException e){
            System.out.println("エラー：日付の形式が正しくありません。「yyyy-MM-dd HH:mm」の形式で入力してください。");
            return null;
        }

        if(tradedDateTime.isAfter(LocalDateTime.now())){
            System.out.println("エラー：未来の日時は入力できません。");
            return null;
        }

        DayOfWeek dayOfWeek = tradedDateTime.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
            System.out.println("エラー：休日は入力できません。平日（月〜金）を指定してください。");
            return null;
        }

        LocalTime tradedTime = tradedDateTime.toLocalTime();
        LocalTime marketOpen = LocalTime.of(9, 0);
        LocalTime marketClose = LocalTime.of(15, 30);
        if(tradedTime.isBefore(marketOpen) || tradedTime.isAfter(marketClose)){
            System.out.println("エラー：取引時間は 9:00〜15:00 の間で指定してください。");
            return null;
        }

        return tradedDateTime;
    }

    public static boolean checkSide(String side){
        try{
            Side.fromDisplayName(side);
            return true;
        }catch(IllegalArgumentException e){
            System.out.println("エラー：Buy または Sell を入力してください。");
            return false;
        }
    }

    public static Long checkQuantity(String InputQuantity){
        try{
            long quantity = Long.parseLong(InputQuantity);
            if(quantity < 1 || quantity > 999_999_999_999L){
                System.out.println("エラー：株式数は1〜999,999,999,999の範囲で入力してください。");
                return null;
            }

            if(quantity % 100 != 0){
                System.out.println("エラー：株式数は100株単位で入力してください。");
                return null;
            }

            return quantity;

        }catch(NumberFormatException e){
            System.out.println("エラー：株式数は数値で入力してください。");
            return null;
        }
    }

    public static BigDecimal checkTradedUnitPrice(String inputTradedUnitPrice){
        try{
            BigDecimal tradedUnitPrice = new BigDecimal(inputTradedUnitPrice);
            if(tradedUnitPrice.scale() <= 2 && tradedUnitPrice.compareTo(BigDecimal.ZERO) > 0){
                return tradedUnitPrice;
            }else{
                System.out.println("エラー：少数第二位までの整数で入力してください。");
                return null;
            }

        }catch(NumberFormatException e){
            System.out.println("エラー：株式数は整数で入力してください。");
            return null;
        }
    }

    public static boolean checkAvailableQuantity(String ticker, Side side, long inputQuantity, List<TransactionsData> history){
        if(side == Side.BUY){
            return true;
        }

        long currentQuantity = 0;
        for(TransactionsData transaction : history){
            if(transaction.getTicker().equals(ticker)){
                if(transaction.getSide() == Side.BUY){
                    currentQuantity += transaction.getQuantity();
                }else{
                    currentQuantity -= transaction.getQuantity();
                }
            }
        }

        if(currentQuantity < inputQuantity){
            System.out.println("保有数量を超えた取引は行えません。\n現在の保有数量は、" + currentQuantity + "です。");
            return false;
        }else{
            return true;
        }
    }

    public static boolean checkDateTimeOrder(String ticker, LocalDateTime inputDataTime, List<TransactionsData> history){
        LocalDateTime latestDateTime = null;

        for(TransactionsData transaction : history){
            if(transaction.getTicker().equals(ticker)){
                if(latestDateTime == null || transaction.getTradedDateTime().isAfter(latestDateTime)){
                    latestDateTime = transaction.getTradedDateTime();
                }
            }
        }

        if(latestDateTime != null && !inputDataTime.isAfter(latestDateTime)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedLatestDateTime = latestDateTime.format(formatter);
            System.out.println("最新の取引日時（" + formattedLatestDateTime +"）より後の日時を入力してください。");
            return false;
        }

        return true;
    }
    
}
