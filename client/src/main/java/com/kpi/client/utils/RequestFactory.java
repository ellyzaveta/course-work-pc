package com.kpi.client.utils;

import com.kpi.api.dto.requests.CurrentProgressRequest;
import com.kpi.api.dto.requests.DocumentsSetRequest;
import com.kpi.api.dto.requests.Request;
import com.kpi.api.dto.requests.ThreadsNumberRequest;

public class RequestFactory {

    public static Request getDocumentsSetRequest(String keyword) {
        DocumentsSetRequest request = new DocumentsSetRequest();
        request.setKeyword(keyword);
        return request;
    }

    public static Request getCurrentProgressRequest() {
        return new CurrentProgressRequest();
    }

    public static Request getThreadsNumberRequest(int threadsNumber) {
        ThreadsNumberRequest request = new ThreadsNumberRequest();
        request.setThreadsNumber(threadsNumber);
        return request;
    }

}
