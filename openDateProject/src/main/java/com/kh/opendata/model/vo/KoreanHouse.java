package com.kh.opendata.model.vo;

public class KoreanHouse {

	private String brandnm;
    private String brandno;
    private String goodsnm;
    private String goodsno;
    private String price;
    private String selltype;
    private String posx;
    private String posy;
    
    public KoreanHouse() {}

	public KoreanHouse(String brandnm, String brandno, String goodsnm, String goodsno, String price, String selltype,
			String posx, String posy) {
		super();
		this.brandnm = brandnm;
		this.brandno = brandno;
		this.goodsnm = goodsnm;
		this.goodsno = goodsno;
		this.price = price;
		this.selltype = selltype;
		this.posx = posx;
		this.posy = posy;
	}

	public String getBrandnm() {
		return brandnm;
	}

	public void setBrandnm(String brandnm) {
		this.brandnm = brandnm;
	}

	public String getBrandno() {
		return brandno;
	}

	public void setBrandno(String brandno) {
		this.brandno = brandno;
	}

	public String getGoodsnm() {
		return goodsnm;
	}

	public void setGoodsnm(String goodsnm) {
		this.goodsnm = goodsnm;
	}

	public String getGoodsno() {
		return goodsno;
	}

	public void setGoodsno(String goodsno) {
		this.goodsno = goodsno;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSelltype() {
		return selltype;
	}

	public void setSelltype(String selltype) {
		this.selltype = selltype;
	}

	public String getPosx() {
		return posx;
	}

	public void setPosx(String posx) {
		this.posx = posx;
	}

	public String getPosy() {
		return posy;
	}

	public void setPosy(String posy) {
		this.posy = posy;
	}

	@Override
	public String toString() {
		return "KoreanHouse [brandnm=" + brandnm + ", brandno=" + brandno + ", goodsnm=" + goodsnm + ", goodsno="
				+ goodsno + ", price=" + price + ", selltype=" + selltype + ", posx=" + posx + ", posy=" + posy + "]";
	}
    
}
