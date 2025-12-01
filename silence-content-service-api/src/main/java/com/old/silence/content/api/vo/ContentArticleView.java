package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;


import java.math.BigInteger;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface ContentArticleView {

    BigInteger getId();

    String getReprintDeclaration();

    String getSummary();

    ContentView getContent();
}
