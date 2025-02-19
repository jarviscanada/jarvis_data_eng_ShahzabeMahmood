package ca.jrvs.apps.stockquote.model;

import ca.jrvs.apps.stockquote.dao.ID;

public class Position {

    private ID id;
    private String symbol; //id
    private int numOfShares;
    private double valuePaid; //total amount paid for shares

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getNumOfShares() {
        return numOfShares;
    }

    public void setNumOfShares(int numOfShares) {
        this.numOfShares = numOfShares;
    }

    public double getValuePaid() {
        return valuePaid;
    }

    public void setValuePaid(double valuePaid) {
        this.valuePaid = valuePaid;
    }
}