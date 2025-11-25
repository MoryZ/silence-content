package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

/**
* LiveRoomOrganization视图接口
*/
@ProjectedPayload
public interface LiveRoomOrganizationView extends AuditableView {
    BigInteger getId();

    BigInteger getLiveRoomId();

    String getOrgCode();


}