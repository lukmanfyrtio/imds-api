package com.idx.imds.api.controller.contract;

public class RealtimePrice {
	private String kodeEfek;
	private String prevPrice;
	private String openPrice;
	private String highPrice;
	private String lowPrice;
	private String lastPriceValue;
	
	private String lastUpdate;

	public String getPrevPrice() {
		return prevPrice;
	}
	public void setPrevPrice(String prevPrice) {
		this.prevPrice = prevPrice;
	}
	public String getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(String openPrice) {
		this.openPrice = openPrice;
	}
	public String getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}
	public String getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}
	public String getLastPriceValue() {
		return lastPriceValue;
	}
	public void setLastPriceValue(String lastPriceValue) {
		this.lastPriceValue = lastPriceValue;
	}
	public String getKodeEfek() {
		return kodeEfek;
	}
	public void setKodeEfek(String kodeEfek) {
		this.kodeEfek = kodeEfek;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
	
}
