package com.kh.opendata.model.vo;

public class Corona19Data {

	private String createDt;
    private String deathCnt;
    private String defCnt;
    private String gubun;
    private String gubunCn;
    private String gubunEn;
    private String incDec;
    private String isolClearCnt;
    private String isolIngCnt;
    private String localOccCnt;
    private String overFlowCnt;
    private String qurRate;
    private String seq;
    private String stdDay;
    private String updateDt;
    
    public Corona19Data() {}

	public Corona19Data(String createDt, String deathCnt, String defCnt, String gubun, String gubunCn, String gubunEn,
			String incDec, String isolClearCnt, String isolIngCnt, String localOccCnt, String overFlowCnt,
			String qurRate, String seq, String stdDay, String updateDt) {
		super();
		this.createDt = createDt;
		this.deathCnt = deathCnt;
		this.defCnt = defCnt;
		this.gubun = gubun;
		this.gubunCn = gubunCn;
		this.gubunEn = gubunEn;
		this.incDec = incDec;
		this.isolClearCnt = isolClearCnt;
		this.isolIngCnt = isolIngCnt;
		this.localOccCnt = localOccCnt;
		this.overFlowCnt = overFlowCnt;
		this.qurRate = qurRate;
		this.seq = seq;
		this.stdDay = stdDay;
		this.updateDt = updateDt;
	}

	public String getCreateDt() {
		return createDt;
	}

	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}

	public String getDeathCnt() {
		return deathCnt;
	}

	public void setDeathCnt(String deathCnt) {
		this.deathCnt = deathCnt;
	}

	public String getDefCnt() {
		return defCnt;
	}

	public void setDefCnt(String defCnt) {
		this.defCnt = defCnt;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getGubunCn() {
		return gubunCn;
	}

	public void setGubunCn(String gubunCn) {
		this.gubunCn = gubunCn;
	}

	public String getGubunEn() {
		return gubunEn;
	}

	public void setGubunEn(String gubunEn) {
		this.gubunEn = gubunEn;
	}

	public String getIncDec() {
		return incDec;
	}

	public void setIncDec(String incDec) {
		this.incDec = incDec;
	}

	public String getIsolClearCnt() {
		return isolClearCnt;
	}

	public void setIsolClearCnt(String isolClearCnt) {
		this.isolClearCnt = isolClearCnt;
	}

	public String getIsolIngCnt() {
		return isolIngCnt;
	}

	public void setIsolIngCnt(String isolIngCnt) {
		this.isolIngCnt = isolIngCnt;
	}

	public String getLocalOccCnt() {
		return localOccCnt;
	}

	public void setLocalOccCnt(String localOccCnt) {
		this.localOccCnt = localOccCnt;
	}

	public String getOverFlowCnt() {
		return overFlowCnt;
	}

	public void setOverFlowCnt(String overFlowCnt) {
		this.overFlowCnt = overFlowCnt;
	}

	public String getQurRate() {
		return qurRate;
	}

	public void setQurRate(String qurRate) {
		this.qurRate = qurRate;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getStdDay() {
		return stdDay;
	}

	public void setStdDay(String stdDay) {
		this.stdDay = stdDay;
	}

	public String getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}

	@Override
	public String toString() {
		return "Corona19Data [createDt=" + createDt + ", deathCnt=" + deathCnt + ", defCnt=" + defCnt + ", gubun="
				+ gubun + ", gubunCn=" + gubunCn + ", gubunEn=" + gubunEn + ", incDec=" + incDec + ", isolClearCnt="
				+ isolClearCnt + ", isolIngCnt=" + isolIngCnt + ", localOccCnt=" + localOccCnt + ", overFlowCnt="
				+ overFlowCnt + ", qurRate=" + qurRate + ", seq=" + seq + ", stdDay=" + stdDay + ", updateDt="
				+ updateDt + "]";
	}
    
}
