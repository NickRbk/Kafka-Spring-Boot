package com.corevalue.producer.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "invalid_resources")
public class InvalidResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String url;

    @NotNull
    @Column(name = "error_code")
    private int errorCode;

    @NotNull
    @Column(name = "error_msg")
    private String errorMsg;
}
