package com.ifengxue.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;
import lombok.Data;
import org.springframework.data.domain.Persistable;

/**
 * 实体类都应继承该字段
 */
@MappedSuperclass
@Data
public abstract class AbstractEntity<PK extends Serializable> implements Serializable, Persistable<PK> {
  private static final long serialVersionUID = -4151961289952434522L;
  @Id
  @GeneratedValue
  private PK id;
  @Version
  private Long version = 1L;
  private Long createdAt;
  private Long updatedAt;

  /**
   * 在持久化之前设置插入时间
   */
  @PrePersist
  public void prePersist() {
    this.createdAt = System.currentTimeMillis();
    preUpdate();
  }

  /**
   * 在持久化之前设置更新时间
   */
  @PreUpdate
  public void preUpdate() {
    this.updatedAt = System.currentTimeMillis();
  }

  /**
   * 该字段不持久化
   */
  @Override
  @Transient
  @JsonIgnore
  public boolean isNew() {
    return getId() == null;
  }
}
