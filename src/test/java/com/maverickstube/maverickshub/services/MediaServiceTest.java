package com.maverickstube.maverickshub.services;


import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.maverickstube.maverickshub.dataTransferObjects.requests.UpdateMediaRequest;
import com.maverickstube.maverickshub.dataTransferObjects.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dataTransferObjects.responses.MediaResponse;
import com.maverickstube.maverickshub.dataTransferObjects.responses.UpdateMediaResponse;
import com.maverickstube.maverickshub.dataTransferObjects.responses.UpdateMediaResponse2;
import com.maverickstube.maverickshub.dataTransferObjects.responses.UploadMediaResponse;
import com.maverickstube.maverickshub.models.Category;
import com.maverickstube.maverickshub.models.Media;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.maverickstube.maverickshub.models.Category.*;
import static com.maverickstube.maverickshub.utils.TestUtils.*;
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

    updateMediaRequest.setMediaId(101L);
    updateMediaRequest.setCategory(STEP_MOM);
    updateMediaRequest.setDescription("All games when alone");

    UpdateMediaResponse updateMediaResponse = mediaService.updateMedia(updateMediaRequest);
    log.info("updated media -> {}", updateMediaResponse);
    assertThat(updateMediaResponse).isNotNull();
    media = mediaService.getMediaBy(101L);
    assertThat(media.getCategory()).isEqualTo(STEP_MOM);
    assertThat(media.getDescription()).isEqualTo("All games when alone");
    }

    @Test
    @DisplayName("test that media can be updated")
    public void testUpdateMedia2() throws JsonPointerException{
        Category category = mediaService.getMediaBy(103L).getCategory();
      assertThat(category).isEqualTo(COMEDY);

       List<JsonPatchOperation> operations = List.of(
       new ReplaceOperation(new JsonPointer("/category"),
        new TextNode(ACTION.name())));

        JsonPatch updateMediaRequest = new JsonPatch(operations);
        UpdateMediaResponse2 response2 = mediaService.updateMedia2(103L, updateMediaRequest);
        System.out.println(response2);
        assertThat(response2).isNotNull();
        assertThat(category).isNotNull();
        category = mediaService.getMediaBy(103L).getCategory();
        assertThat(category).isEqualTo(ACTION);
    }

    @Test
    public void getMediaByIdTest(){
       Media media =  mediaService.getMediaBy(101L);
       log.info("found content -> {}", media);
        assertThat(media).isNotNull();


    }

    @Test
    public void getMediaForUserTest(){
        Long userId = 200L;
        List<MediaResponse> media = mediaService.getMediaFor(userId);
        assertThat(media).hasSize(3);
    }
    }





