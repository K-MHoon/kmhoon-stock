package com.kmhoon.licensingservice.model.dto.organization;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
@Builder
@RedisHash("organization") // 조직 데이터가 저장될 레디스 서버의 해시 이름 설정
public class Organization extends RepresentationModel<Organization> {

    private String id;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
}
