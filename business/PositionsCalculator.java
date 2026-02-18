package business;

import java.util.*;

public class PositionsCalculator {
    public List<PositionsData> calculatePositions(List<TransactionsData> transactions){
        Map<String, Long> tickerToQuantity = new HashMap<>();

        for(TransactionsData transaction : transactions){
            String ticker = transaction.getTicker();

            long currentQuantity;
            if(tickerToQuantity.containsKey(ticker)){
                currentQuantity = tickerToQuantity.get(ticker);
            }else{
                currentQuantity = 0L;
            }

            if(transaction.getSide() == Side.BUY){
                currentQuantity += transaction.getQuantity();
            }else{
                currentQuantity -= transaction.getQuantity();
            }

            tickerToQuantity.put(ticker, currentQuantity);
        }

        List<PositionsData> positions = new ArrayList<>();
        for(String ticker : tickerToQuantity.keySet()){
            PositionsData data = new PositionsData(ticker, tickerToQuantity.get(ticker));
            positions.add(data);
        }

        positions.sort(Comparator.comparing(PositionsData::getTicker));

        return positions;
    }
}
