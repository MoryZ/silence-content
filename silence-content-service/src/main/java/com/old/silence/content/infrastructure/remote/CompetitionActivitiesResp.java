package com.old.silence.content.infrastructure.remote;


public class CompetitionActivitiesResp {

    //报名校验结果【报名校验时返回】
    private boolean checkRegister;

    //客户号【报名校验成功时返回】
    private String customerNo;

    //活力GO渠道编码【报名校验成功时返回】
    private String distChannel;

    public boolean isCheckRegister() {
        return checkRegister;
    }

    public void setCheckRegister(boolean checkRegister) {
        this.checkRegister = checkRegister;
    }

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
}
