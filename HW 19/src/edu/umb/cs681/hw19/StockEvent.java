package edu.umb.cs681.hw19;

public class StockEvent {
    private String ticker;
    private Double quote;
    
    public StockEvent(String ticker, Double quote) {
        this.ticker = ticker;
        this.quote = quote;
    }
    
    public String getTicker() {
        return ticker;
    }
    
    public Double getQuote() {
        return quote;
    }

}
