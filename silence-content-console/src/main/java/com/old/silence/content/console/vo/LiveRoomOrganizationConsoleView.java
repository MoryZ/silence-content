package com.old.silence.content.console.vo;

import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
* LiveRoomOrganization视图接口
*/
public interface LiveRoomOrganizationConsoleView extends AuditableView {
    BigInteger getId();

    BigInteger getLiveRoomId();

    String getOrgCode();

}