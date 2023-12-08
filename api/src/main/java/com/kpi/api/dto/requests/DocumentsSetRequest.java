package com.kpi.api.dto.requests;

import lombok.Data;

import java.io.Serializable;

@Data
public class DocumentsSetRequest implements Request, Serializable {
    private static final long serialVersionID = 222222222L;
    String keyword;
}
