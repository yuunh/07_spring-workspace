package com.kh.opendata.model.vo;

public class BusVO {

	private String busRoute;	// 노선
	private String station;		// 정류소
	private String direction;	// 방향
	private String routeType;	// 노선유형
	private String beginTm;		// 첫차시간
	private String lastTm;		// 막차시간
	
	public BusVO() {}

	public BusVO(String busRoute, String station, String direction, String routeType, String beginTm, String lastTm) {
		super();
		this.busRoute = busRoute;
		this.station = station;
		this.direction = direction;
		this.routeType = routeType;
		this.beginTm = beginTm;
		this.lastTm = lastTm;
	}

	public String getBusRoute() {
		return busRoute;
	}

	public void setBusRoute(String busRoute) {
		this.busRoute = busRoute;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getBeginTm() {
		return beginTm;
	}

	public void setBeginTm(String beginTm) {
		this.beginTm = beginTm;
	}

	public String getLastTm() {
		return lastTm;
	}

	public void setLastTm(String lastTm) {
		this.lastTm = lastTm;
	}

	@Override
	public String toString() {
		return "BusVO [busRoute=" + busRoute + ", station=" + station + ", direction=" + direction + ", routeType="
				+ routeType + ", beginTm=" + beginTm + ", lastTm=" + lastTm + "]";
	}
	
}
