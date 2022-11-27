package com.yjw.sprint.tech.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EnableJpaAuditing
public class AbstractAuditingEntity implements Serializable {

    @CreatedBy
    @Column(name = "create_by",  updatable = false)
    @JsonIgnore
    public String createBy;

    @CreatedDate
    @Column(name = "create_date", updatable = false)
    @JsonIgnore
    public Instant createDate = Instant.now();

    @LastModifiedBy
    @Column(name = "lastModified_by")
    @JsonIgnore
    public String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "lastModified_date")
    @JsonIgnore
    public Instant lastModifiedDate = Instant.now();

}
