package com.notes.notenestor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseModel {

    @CreatedBy
    @Column(updatable = false)  // update ke time pr iske work nhi
    private Integer createdBy;

    @CreatedDate
    @Column(updatable = false)
    private Date createdDate;

    @LastModifiedBy
    private Integer updatedBy;

    @LastModifiedDate
    private Date updatedDate;

}
