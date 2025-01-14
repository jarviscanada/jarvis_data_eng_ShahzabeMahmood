package ca.jrvs.apps.jbdc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GlobalQuoteResponse {
    @JsonProperty("Global Quote")
    private GlobalQuote globalQuote;

    public GlobalQuote getGlobalQuote() { return globalQuote; }
    public void setGlobalQuote(GlobalQuote globalQuote) { this.globalQuote = globalQuote; }
}
