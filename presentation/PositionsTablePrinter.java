package presentation;

import business.PositionsData;

import java.util.List;

public class PositionsTablePrinter {
    public static void printPositionsTable(List<PositionsData> positions){
        final int[] WIDTHS = {8, 32, 17};
        final String[] REGULATION_HEADER = {"Ticker", "Product_Name", "Quantity"};

        PositionsTablePrinter printer = new PositionsTablePrinter();
        printer.print(positions, WIDTHS, REGULATION_HEADER);
    }

    public void print(List<PositionsData> positions, int[] widths, String[] header){
        printTopBottomBorder(widths);
        printHeader(widths, header);
        printMiddleBorder(widths);
        printData(positions, widths);
        printTopBottomBorder(widths);
    }

    private void printData(List<PositionsData> positions, int[] widths){
        for(PositionsData position : positions) {
            System.out.print("|");
            System.out.print(leftAlign(position.getTicker(), widths[0]));
            System.out.print("|");
            System.out.print(leftAlign(position.getProductName(), widths[1]));
            System.out.print("|");
            String quantity = String.format("%,d", position.getQuantity());
            System.out.print(rightAlign(quantity, widths[2]));
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