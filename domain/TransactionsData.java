package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionsData{
    private LocalDateTime tradedDateTime;
    private String ticker;
    private Side side;
    private long quantity;
    private BigDecimal tradedUnitPrice;
    private LocalDateTime inputDateTime;

    private String productName;
    
    
    public TransactionsData(LocalDateTime tradedDateTime, String ticker, Side side, long quantity, BigDecimal tradedUnitPrice, LocalDateTime inputDateTime){
        this.tradedDateTime = tradedDateTime;
        this.ticker = ticker;
        this.side = side;
        this.quantity = quantity;
        this.tradedUnitPrice = tradedUnitPrice;
        this.inputDateTime = inputDateTime;
    }


    // getter
    public LocalDateTime getTradedDateTime() {
        return tradedDateTime;
    }
    public String getTicker() {
        return ticker;
    }
    public Side getSide() {
        return side;
    }
    public long getQuantity() {
        return quantity;
    }
    public BigDecimal getTradedUnitPrice() {
        return tradedUnitPrice;
    }
    public LocalDateTime getInputDateTime() {
        return inputDateTime;
    }
    public String getProductName() {
        return productName;
    }

    // setter
    public void setProductName(String productName) {
        this.productName = productName;
    }    
}