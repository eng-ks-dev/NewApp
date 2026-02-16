package presentation;

import java.util.List;

import business.StocksData;

public class StocksTablePrinter {
    public static void printStocksTable(List<StocksData> stocks){
        final int[] WIDTHS = {8, 32, 10, 17};
        final String[] HEADER = {"Ticker", "Product_Name", "Market", "Shared_Issued"};

        StocksTablePrinter printer = new StocksTablePrinter();
        printer.print(stocks, WIDTHS, HEADER);
    }

    public void print(List<StocksData> stocks, int[] widths, String[] header){
        printTopBottomBorder(widths);
        printHeader(widths, header);
        printMiddleBorder(widths);
        printData(stocks, widths);
        printTopBottomBorder(widths);
    }

    private void printData(List<StocksData> stocks, int[] widths){
        for(StocksData stock : stocks){
            System.out.print("|");
            System.out.print(leftAlign(stock.getTicker(), widths[0]));
            System.out.print("|");
            System.out.print(leftAlign(stock.getProductName(), widths[1]));
            System.out.print("|");
            System.out.print(leftAlign(stock.getMarket().getDisplayName(), widths[2]));
            System.out.print("|");
            String formattedShares = String.format("%,d", stock.getSharesissued());
            System.out.print(rightAlign(formattedShares, widths[3]));
            System.out.println("|");
        }
    }

    private void printTopBottomBorder(int[] widths){
        System.out.print("|");
        int totalWidths = 0;
        for(int width : widths){
            totalWidths += width;
        }
        totalWidths += widths.length - 1;
        
        System.out.print("-".repeat(totalWidths));
        System.out.println("|");
    }

    private void printHeader(int[] widths, String[] header){
        System.out.print("|");
        for(int i = 0; i < header.length; i++){
            System.out.print(leftAlign(header[i], widths[i]));
            System.out.print("|");
        }
        System.out.println();
    }

    private void printMiddleBorder(int[] widths){
        System.out.print("+");
        for(int width : widths){
            System.out.print("-".repeat(width));
            System.out.print("+");
        }
        System.out.println();
    }

    private String truncateText(String text, int width){
        if(text.length() > width - 2){
            return text.substring(0, width - 5) + "...";
        }
        return text;
    }

    private String leftAlign(String text, int width){
        text = truncateText(text, width);
        String paddedText = " " + text + " ";
        return paddedText + " ".repeat(width - paddedText.length());
    }

    private String rightAlign(String text, int width){
        text = truncateText(text, width);
        String paddedText = " " + text + " ";
        return " ".repeat(width - paddedText.length()) + paddedText;
    }
}
