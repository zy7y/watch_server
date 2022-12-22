package com.zy7y.watch_server.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.Future;

public interface AsyncFileService {
    Future asyncHandleFile(MultipartFile file);
}
