package com.work.webhook.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationDTO {

    private Boolean online;
    private Long id;
    private String url;
    private String name;

}
