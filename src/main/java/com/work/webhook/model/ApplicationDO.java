package com.work.webhook.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "application")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String url;

    private String name;

    @OneToMany(mappedBy = "application", cascade = CascadeType.REMOVE)
    private List<MessageDO> messages;

    @Column(nullable = false)
    private Boolean online;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "creation_datetime")
    private LocalDateTime modificationDatetime;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "modification_datetime", insertable = true, updatable = false)
    private LocalDateTime creationDatetime;

    public ApplicationDO(String url) {
        super();
        this.url = url;
        this.online = true;
    }

    @PrePersist
    public void onCreate() {
        this.setCreationDatetime(LocalDateTime.now());
        this.setModificationDatetime(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate() {
        this.setModificationDatetime(LocalDateTime.now());
    }
}
