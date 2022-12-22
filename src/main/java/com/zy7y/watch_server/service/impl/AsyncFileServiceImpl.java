package com.zy7y.watch_server.service.impl;

import com.zy7y.watch_server.service.AsyncFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.Future;

@Slf4j
@Service
public class AsyncFileServiceImpl implements AsyncFileService {
    @Async
    @Override
    public Future<String> asyncHandleFile(MultipartFile file) {
        // 处理文件 -》耗时操作
        log.info("耗时操作开始 {}", System.currentTimeMillis());
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("耗时操作处理完了 {}", System.currentTimeMillis());
        return AsyncResult.forValue(file.getOriginalFilename());
    }
}
