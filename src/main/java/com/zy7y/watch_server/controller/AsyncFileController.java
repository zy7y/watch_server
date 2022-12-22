package com.zy7y.watch_server.controller;


import com.zy7y.watch_server.pojo.rep.R;
import com.zy7y.watch_server.service.AsyncFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.lingala.zip4j.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@EnableAsync // 异步
@Tag(name="文件上传")
@RequestMapping("/upload")
@RestController
public class AsyncFileController {

    @Autowired
    private AsyncFileService asyncFileService;

    @Operation(summary = "上传单文件", description = "file参数、单个文件")
    @PostMapping("/file")
    public R<String> uploadFile(MultipartFile file){
        return R.success(file.getOriginalFilename());
    }

    @Operation(summary = "上传多个文件", description = "files 参数、上传多个文件")
    @PostMapping("/files")
    public R<List<String>> uploadFiles(List<MultipartFile> files){
        List<String> fileNames = files.stream()
                .map(MultipartFile::getOriginalFilename)
                .collect(Collectors.toList());
        return R.success(fileNames);
    }

    @Operation(summary = "表单+文件", description = "表单参数 + 文件")
    @PostMapping("/formWithFile")
    public R<String> formWithFile(String name, MultipartFile file){
        return R.success(name +file.getOriginalFilename());
    }

    @Operation(summary = "上传并解压", description = "上传zip文件并解压")
    @PostMapping("/unzip")
    public R<String> unzipFile(MultipartFile file) throws Exception {
        // 上传文件写入本地
        File saveFile = new File("/Users/qy_air/Coding/"+ file.getOriginalFilename());
        Files.write(saveFile.toPath(),file.getBytes());

        // 解压本地文件
        ZipFile zipFile = new ZipFile(saveFile);
        String path = "/Users/qy_air/Coding/";
        zipFile.extractAll(path);
        return R.success("处理中...");
    }

    @Operation(summary = "上传并解压异步处理", description = "上传zip文件并解压、后台处理")
    @PostMapping("/unzipAsync")
    public R<String> unzipFileAsync(MultipartFile file) {
        asyncFileService.asyncHandleFile(file);
        return R.success("ok");
    }
}
