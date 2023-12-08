package com.kpi.api.dto.responses;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DocumentsSetResponse implements Response, Serializable {
    private static final long serialVersionID = 777777777L;
    private String keyword;
    private List<String> docs;
}
