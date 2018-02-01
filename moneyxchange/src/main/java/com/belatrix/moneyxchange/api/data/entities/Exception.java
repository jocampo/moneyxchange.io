package com.belatrix.moneyxchange.api.data.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Entity
@Table(name = "TBL_EXCEPTION")
public class Exception {
    public Exception() {

    }
    public Exception(java.lang.Exception ex) {
        this.message = ex.getMessage();
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.setRootCause(ExceptionUtils.getRootCauseMessage(ex));
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name="ROOT_CAUSE")
    private String rootCause;

    @Column(name="MESSAGE")
    private String message;

    @Column(name="CREATION_DATE")
    private Timestamp creationDate;

    @Column(name="AFFECTED_USER")
    private String affectedUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getAffectedUser() {
        return affectedUser;
    }

    public void setAffectedUser(String affectedUser) {
        this.affectedUser = affectedUser;
    }

}
