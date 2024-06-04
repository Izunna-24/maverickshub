package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dataTransferObjects.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dataTransferObjects.responses.UploadMediaResponse;
import com.maverickstube.maverickshub.models.Category;
import com.maverickstube.maverickshub.models.Media;
import com.maverickstube.maverickshub.models.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.maverickstube.maverickshub.models.Category.ACTION;
import static com.maverickstube.maverickshub.models.Category.ROMANCE;
import static com.maverickstube.maverickshub.utils.TestUtils.TEST_IMAGE_LOCATION;
import static com.maverickstube.maverickshub.utils.TestUtils.TEST_VIDEO_LOCATION;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/data.sql"})
class MediaServiceTest {
    @Autowired
    private MediaService mediaService;

    @Test

    public void uploadMediaPhotoTest() {
        Path path = Paths.get(TEST_IMAGE_LOCATION);
        try (var inputStream = Files.newInputStream(path)) {
            UploadMediaRequest request = buildUploadMediaRequest(inputStream);
            UploadMediaResponse response = mediaService.upload(request);
            //log.info
            assertThat(response).isNotNull();
            assertThat(response.getUrl()).isNotNull();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void uploadVideoTest(){
        Path path = Paths.get(TEST_VIDEO_LOCATION);
        try (var inputStream = Files.newInputStream(path)){
            UploadMediaRequest request = buildUploadMediaRequest(inputStream);
            UploadMediaResponse response = mediaService.upload(request);

            assertThat(response).isNotNull();
            assertThat(response.getUrl()).isNotNull();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static UploadMediaRequest buildUploadMediaRequest(InputStream inputStream) throws IOException{
        UploadMediaRequest request = new UploadMediaRequest();
        MultipartFile file = new MockMultipartFile("funnyShorts", inputStream);
        request.setMediaFile(file);
        request.setUserId(201L);
        request.setCategory(ACTION);
        return request;


    }

    @Test
    @DisplayName("test that media can be updated")
    public void testUpdateMedia(){
    UpdateMediaRequest updateMediaRequest = new UpdateMediaRequest();
    Media media = new Media();
    User user = new User();
    updateMediaRequest.setMedia(media);
    media.setUploader(user);
    media.setDescription("");
    media.setCategory(ROMANCE);
    UpdateMediaResponse updateMediaResponse = mediaService.updateMedia(updateMediaRequest);
    }


    @Test
    public void getMediaByIdTest(){
       Media media =  mediaService.getMediaBy(101L);
       log.info("found content -> {}", media);
        assertThat(media).isNotNull();


    }
}



