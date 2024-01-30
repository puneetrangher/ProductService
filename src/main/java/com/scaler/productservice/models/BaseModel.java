package com.scaler.productservice.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    /**
     * This is the base model class for all the models in the application.
     * It contains the id field which is the primary key for all the models.
     * This class is extended by all the models.
     */
//    private Long id;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
    private boolean isDeleted;
}
