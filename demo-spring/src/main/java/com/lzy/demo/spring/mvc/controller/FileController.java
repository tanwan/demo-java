package com.lzy.demo.spring.mvc.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件操作的controller
 *
 * @author LZY
 * @version v1.0
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 文件上传
     *
     * @param file   the file
     * @param params the params
     * @return the string
     */
    @PostMapping("/file-upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> params) throws IOException {
        file.transferTo(new File("/Users/luke.luo/Desktop/untitled/dddd"));
        return file.getOriginalFilename() + " " + file.getSize() + params;
    }

    /**
     * 上传不定数量的文件,这里上传的每个文件的键名都是file
     *
     * @param files the files
     * @return the string
     */
    @PostMapping("/files-upload")
    public String filesUpload(@RequestParam("file") MultipartFile[] files) {
        return Arrays.stream(files).map(MultipartFile::getOriginalFilename).collect(Collectors.joining(","));
    }

    /**
     * StreamingResponseBody 文件下载
     *
     * @return the streaming response body
     */
    @RequestMapping("/download-file")
    public ResponseEntity<StreamingResponseBody> downloadFile() {
        HttpHeaders headers = new HttpHeaders();
        //设置下载弹出框的文件名,如果不设置的话,文件名默认是最后一层路径,在这里就是download-file,中文需要进行url编码
        headers.setContentDispositionFormData("attachment", "test.txt");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        StreamingResponseBody streamingResponseBody = outputStream -> outputStream.write("hello world".getBytes());
        return new ResponseEntity<>(streamingResponseBody, headers, HttpStatus.CREATED);
    }
}
