package com.exadel.borsch.domain;

/**
 * Created with IntelliJ IDEA.
 * User: 55
 * Date: 5.8.13
 * Time: 23.42
 */
public class MessageForView {
    private static final int SHORT_TEXT_LENGTH = 30;

    private static final String THREE_DOTS = "...";

    private String messageText;

    private String shortText;

    private String senderName;

    private boolean readStatus;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
        this.setShortText();
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public String getShortText() {
        return shortText;
    }

    private void setShortText() {
        if (messageText.length() <= SHORT_TEXT_LENGTH) {
            shortText = messageText;
        } else {
            shortText = messageText.substring(0, SHORT_TEXT_LENGTH).concat(THREE_DOTS);
        }
    }
}
