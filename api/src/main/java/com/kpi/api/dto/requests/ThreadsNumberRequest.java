package com.kpi.api.dto.requests;

import lombok.Data;

import java.io.Serializable;

@Data
public class ThreadsNumberRequest implements Request, Serializable {
    private static final long serialVersionID = 444444444L;
    public int threadsNumber;
}
