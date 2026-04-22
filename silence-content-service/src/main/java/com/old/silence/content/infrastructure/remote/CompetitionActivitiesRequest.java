package com.old.silence.content.infrastructure.remote;


public class CompetitionActivitiesRequest {

    //客户号【报名校验成功时返回】
    private String customerNo;
    //活力GO渠道编码
    private String distChannel;
    //票券编码
    private String partnerCode;
    private String eventGameId;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getDistChannel() {
        return distChannel;
    }

    public void setDistChannel(String distChannel) {
        this.distChannel = distChannel;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getEventGameId() {
        return eventGameId;
    }

    public void setEventGameId(String eventGameId) {
        this.eventGameId = eventGameId;
    }
}
