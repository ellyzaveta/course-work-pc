package com.kpi.server.utils;

import com.kpi.api.dto.responses.*;

import java.util.List;

public class ResponseFactory {

    public static Response getCurrentProgressResponse(double progress) {
        CurrentProgressResponse currentProgressResponse = new CurrentProgressResponse();
        currentProgressResponse.setProgress(progress);
        return currentProgressResponse;
    }

    public static Response getDocumentsSetResponse(List<String> docs, String keyword) {
        DocumentsSetResponse documentsSetResponse = new DocumentsSetResponse();
        documentsSetResponse.setDocs(docs);
        documentsSetResponse.setKeyword(keyword);

        return documentsSetResponse;
    }

    public static Response getThreadsNumberResponse() {
        return new ThreadsNumberResponse();
    }

    public static Response getServerReadyResponse() {
        return new ServerReadyResponse();
    }
}
