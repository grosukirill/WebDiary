package com.ru.questiondiary.service;

import lombok.Data;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        return writeToStore(multipartFile.getBytes());
    }

    private String writeToStore(byte[] bytes) {
        String url = "https://api.imgbb.com/1/upload?key=806d06f1eaf4888b86029a1d1339564f";
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("image", Base64.getEncoder().encode(bytes));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ImgBBRes> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ImgBBRes.class);

        return response.getBody().getData().getDisplay_url();
    }

    @Data
    public static class ImgBBRes {
        private Detail data;

        private boolean success;

        private String status;

        @Data
        public class Detail {
            private String display_url;

            private String size;

            private String delete_url;

            private String expiration;

            private String id;

            private String time;

            private String title;

            private String url_viewer;

            private String url;

        }
    }
}
