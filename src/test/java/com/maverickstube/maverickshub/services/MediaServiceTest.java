package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dataTransferObjects.requests.UpdateMediaRequest;
import com.maverickstube.maverickshub.dataTransferObjects.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dataTransferObjects.responses.UpdateMediaResponse;
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

import static com.maverickstube.maverickshub.models.Category.*;
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


    @Test
    @DisplayName("test that media can be updated")
    public void testUpdateMedia() throws IOException {
    UpdateMediaRequest updateMediaRequest = new UpdateMediaRequest();
    Media media = mediaService.getMediaBy(101L);
    assertThat(media.getCategory()).isEqualTo(ROMANCE);
    assertThat(media.getDescription()).isEqualTo("media 2");

    updateMediaRequest.setMediaID(101L);
    updateMediaRequest.setCategory(STEP_MOM);
    updateMediaRequest.setDescription("All games when alone");

    UpdateMediaResponse updateMediaResponse = mediaService.updateMedia(updateMediaRequest);
    log.info("updated media -> {}", updateMediaResponse);
    assertThat(updateMediaResponse).isNotNull();
    media = mediaService.getMediaBy(101L);
    assertThat(media.getCategory()).isEqualTo(STEP_MOM);
    assertThat(media.getDescription()).isEqualTo("All games when alone");
    }

//    @Test
//    @DisplayName("test that media can be updated")
//    public void testUpdateMedia2(){
//        UpdateMediaRequest updateMediaRequest = new UpdateMediaRequest();
//        updateMediaRequest.setCategory(SCI_FI);
//        mediaService.updateMedia(103L,updateMediaRequest);
//    }

    @Test
    public void getMediaByIdTest(){
       Media media =  mediaService.getMediaBy(101L);
       log.info("found content -> {}", media);
        assertThat(media).isNotNull();


    }

    private static UploadMediaRequest buildUploadMediaRequest(InputStream inputStream) throws IOException{
        UploadMediaRequest request = new UploadMediaRequest();
        MultipartFile file = new MockMultipartFile("funnyShorts", inputStream);
        request.setMediaFile(file);
        request.setUserId(201L);
        request.setCategory(ACTION);
        return request;


    }

}



