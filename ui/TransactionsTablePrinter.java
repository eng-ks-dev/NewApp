package ui;

import java.time.format.DateTimeFormatter;
import java.util.List;

import domain.TransactionsData;

public class TransactionsTablePrinter {
    public static void printTransactionsTable(List<TransactionsData> transactions){
        final int[] WIDTHS = {20, 8, 32, 6, 17, 17};
        final String[] REGULATION_HEADER = {"Traded_DateTime", "Ticker", "Product_Name" ,"Side", "Unit_Price", "Input_DateTime"};

        TransactionsTablePrinter printer = new TransactionsTablePrinter();
        printer.print(transactions, WIDTHS, REGULATION_HEADER);
    }

    public void print(List<TransactionsData> transactions, int[] widths, String[] header){
        printTopBottomBorder(widths);
        printHeader(widths, header);
        printMiddleBorder(widths);
        printData(transactions, widths);
        printTopBottomBorder(widths);
    }

    private void printData(List<TransactionsData> transactions, int[] widths){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        for(TransactionsData transaction : transactions){
            System.out.print("|");
            System.out.print(leftAlign(transaction.getTradedDateTime().format(formatter), widths[0]));
            System.out.print("|");
            System.out.print(leftAlign(transaction.getTicker(), widths[1]));
            System.out.print("|");
            System.out.print(leftAlign(transaction.getProductName(),widths[2]));
            System.out.print("|");
            System.out.print(leftAlign(transaction.getSide().getDisplayName(), widths[3]));
            System.out.print("|");
            String quantity = String.format("%,d", transaction.getQuantity());
            System.out.print(rightAlign(quantity, widths[4]));
            System.out.print("|");
            String tradedUnitPrice = String.format("%.2f", transaction.getTradedUnitPrice());
            System.out.print(rightAlign(tradedUnitPrice, widths[5]));
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
