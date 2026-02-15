package domain;

public class StocksValidator {
    public static boolean checkProductName(String productName){
        if(productName.isEmpty() || !productName.matches("^[a-zA-Z0-9.() ]+$")){
            System.out.println("エラー：銘柄名が規則に従っていません。");
            return false;
        }
        return true;
    }

    public static boolean checkTicker(String ticker){
        if(ticker.isEmpty() || !ticker.matches("^[0-9][0-9ACDF-HJ-NPR-UW-Y][0-9][0-9ACDF-HJ-NPR-UW-Y]$")){
            System.out.println("エラー：銘柄コードの規則に従っていません。");
            return false;
        }
        return true;
    }

    public static boolean checkMarket(String market){
        try{
            Market.fromDisplayName(market);
            return true;
        }catch(IllegalArgumentException e){
            System.out.println("エラー：市場はPrime / Standard / Growth のいずれかを入力してください。");
            return false;
        }
    }

    public static boolean checkSharesIssud(String sharesIssuedStr){
        try{
            Long sharesIssued = Long.parseLong(sharesIssuedStr);
            if(sharesIssued < 1 || sharesIssued > 999_999_999_999L){
                System.out.println("エラー：株式数は1〜999,999,999,999の範囲の整数で入力してください。");
                return false;
            }
            return true;
        }catch(NumberFormatException e){
            System.out.println("エラー：株式数は整数の数字で入力してください。");
            return false;
        }
    }
}
