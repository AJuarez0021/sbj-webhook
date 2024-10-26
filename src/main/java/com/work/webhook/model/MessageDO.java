package com.work.webhook.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "message")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDO implements Serializable {

    public static final long MESSAGE_TIMEOUT = 24 * 60 * 60 * 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String messageBody;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Timestamp timestamp;

    @ManyToOne(optional = false)
    private ApplicationDO application;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "creation_datetime")
    private LocalDateTime modificationDatetime;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "modification_datetime", insertable = true, updatable = false)
    private LocalDateTime creationDatetime;

    public MessageDO(String messageBody, String contentType, ApplicationDO application) {
        super();
        this.messageBody = messageBody;
        this.contentType = contentType;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.application = application;
    }

    public Long getDestinationId() {
        return application.getId();
    }

    public String getDestinationUrl() {
        return application.getUrl();
    }

    public Boolean isMessageTimeout() {
        return timestamp.getTime() < System.currentTimeMillis() - MESSAGE_TIMEOUT;
    }

    @PrePersist
    public void onCreate() {
        this.setCreationDatetime(LocalDateTime.now());
        this.setModificationDatetime(LocalDateTime.now());
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void onUpdate() {
        this.setModificationDatetime(LocalDateTime.now());
    }
}
