package business;

public enum Market {
    PRIME("P", "Prime"),
    STANDARD("S", "Standard"),
    GROWTH("G", "Growth");

    private final String CODE;
    private final String DISPLAY_NAME;

    Market(String code, String displayName){
        this.CODE = code;
        this.DISPLAY_NAME = displayName;
    }

    public static Market fromCode(String code){
        for(Market market : values()){
            if(market.CODE.equals(code)){
                return market;
            }
        }
        throw new IllegalStateException("市場コードが不正です。" + code);
    }

    public static Market fromDisplayName(String displayName){
        for(Market market : values()){
            if(market.DISPLAY_NAME.equals(displayName)){
                return market;
            }
        }
        throw new IllegalArgumentException("指定された市場名は存在しません。" + displayName);
    }

    public String getDisplayName(){
        return DISPLAY_NAME;
    }

    public String getCode(){
        return CODE;
    }
    
}
