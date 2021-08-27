package com.ru.questiondiary.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ImageService {
    String uploadImage(MultipartFile multipartFile) throws IOException;
}
