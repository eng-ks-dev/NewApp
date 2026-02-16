package business;

public class PositionsData {
    private String ticker;
    private String productName;
    private Long quantity;

    public PositionsData(String ticker, String productName, Long quantity){
        this.ticker = ticker;
        this.productName = productName;
        this.quantity = quantity;
    }

    // getter
    public Long getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    public String getTicker() {
        return ticker;
    }
}