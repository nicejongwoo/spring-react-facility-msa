package com.practice.facility.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Getter @Setter
@MappedSuperclass // 공통 맵핑 정보가 필요할 때 사용(상속관계X, 자식 클래스에 매핑 정보만 제공)
@EntityListeners(AuditingEntityListener.class) // persist 할 때 자동으로 createdAt, updatedAt 등록
@JsonIgnoreProperties( // 지정된 필드값의 JSON 직렬, 역직렬화를 무시가능
    value = {"createdAt", "updatedAt"},
    allowGetters = true // 직렬화는 허용하나, 역직렬화는 허용X
)
public abstract class DateAudit implements Serializable {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

}
