package business;

public enum Side {
    BUY("B", "Buy"),
    SELL("S", "Sell");

    private final String CODE;
    private final String DISPLAY_NAME;

    Side(String code, String displayName){
        this.CODE = code;
        this.DISPLAY_NAME = displayName;
    }

    public static Side fromDisplayName(String displayName){
        for(Side side : values()){
            if(side.DISPLAY_NAME.equals(displayName)){
                return side;
            }
        }
        throw new IllegalArgumentException("指定された売買区分はありません。");
    }

    public String getCode() {
        return CODE;
    }

    public static Side fromCode(String code){
        for(Side side : values()){
            if(side.CODE.equals(code)){
                return side;
            }
        }
        throw new IllegalStateException("売買コードが不正です。" + code);
    }

    public String getDisplayName(){
        return DISPLAY_NAME;
    }
    

}
