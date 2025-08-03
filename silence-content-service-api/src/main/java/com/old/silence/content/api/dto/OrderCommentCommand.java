package com.old.silence.content.api.dto;

/**
 * @author moryzang
 * @Description
 */
public class OrderCommentCommand {

    private String comment;

    public OrderCommentCommand() {
    }

    public OrderCommentCommand(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
