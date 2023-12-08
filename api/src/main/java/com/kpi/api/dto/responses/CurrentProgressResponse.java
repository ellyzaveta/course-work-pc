package com.kpi.api.dto.responses;

import lombok.Data;

import java.io.Serializable;

@Data
public class CurrentProgressResponse implements Response, Serializable {
    private static final long serialVersionID = 555555555L;
    private double progress;
}
