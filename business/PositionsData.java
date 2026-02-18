package business;

public class PositionsData {
    private String ticker;
    private Long quantity;

    public PositionsData(String ticker, Long quantity){
        this.ticker = ticker;
        this.quantity = quantity;
    }

    // getter
    public Long getQuantity() {
        return quantity;
    }

    public String getTicker() {
        return ticker;
    }
}