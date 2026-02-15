package domain;

public class StocksData {
    private String ticker;
    private String productName;
    private Market market;
    private long sharesIssued;

    // getter
    public String getTicker(){
        return ticker;
    }
    public String getProductName(){
        return productName;
    }
    public Market getMarket(){
        return market;
    }
    public long getSharesissued(){
        return sharesIssued;
    }

    // setter
    public void setTicker(String ticker){
        this.ticker = ticker;
    }
    public void setProducName(String productName){
        this.productName = productName;
    }
    public void setMarket(Market market){
        this.market = market;
    }
    public void setSharesIssued(Long sharesIssued){
        this.sharesIssued = sharesIssued;
    }
    
}
