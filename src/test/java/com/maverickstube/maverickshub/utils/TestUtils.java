package com.maverickstube.maverickshub.utils;

import com.maverickstube.maverickshub.dataTransferObjects.requests.UploadMediaRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.maverickstube.maverickshub.models.Category.ACTION;

public class TestUtils {
    public static final String TEST_IMAGE_LOCATION = "C:\\Users\\DELL\\IdeaProjects\\maverickshub\\src\\main\\resources\\static\\mentor.jpg";
    public static final String TEST_VIDEO_LOCATION = "C:\\Users\\DELL\\IdeaProjects\\maverickshub\\src\\main" +
            "\\resources\\static\\funnyShorts.mp4";


    public static UploadMediaRequest buildUploadMediaRequest(InputStream inputStream) throws IOException {
        UploadMediaRequest request = new UploadMediaRequest();
        MultipartFile file = new MockMultipartFile("funnyShorts", inputStream);
        request.setMediaFile(file);
        request.setUserId(201L);
        request.setCategory(ACTION);
        return request;


    }

}