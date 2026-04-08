package com.notifi.domain.model;

import java.util.Objects;

/**
 * Value object representing the content of a notification.
 * Immutable and ensures content validity.
 */
public final class NotificationContent {
    private final String body;
    private final ContentType contentType;

    /**
     * Constructs a NotificationContent with the given body and content type.
     *
     * @param body the notification body text
     * @param contentType the type of content
     * @throws NullPointerException if body or contentType is null
     */
    public NotificationContent(String body, ContentType contentType) {
        this.body = Objects.requireNonNull(body, "body cannot be null");
        this.contentType = Objects.requireNonNull(contentType, "contentType cannot be null");
    }

    public String getBody() {
        return body;
    }

    public ContentType getContentType() {
        return contentType;
    }

    /**
     * Helper method to check if content is HTML.
     *
     * @return true if content type is HTML, false otherwise
     */
    public boolean isHtml() {
        return contentType == ContentType.HTML;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationContent that = (NotificationContent) o;
        return Objects.equals(body, that.body) &&
               contentType == that.contentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, contentType);
    }

    @Override
    public String toString() {
        return "NotificationContent{" +
                "body='" + body + '\'' +
                ", contentType=" + contentType +
                '}';
    }
}
