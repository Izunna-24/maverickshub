package com.maverickstube.maverickshub.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.maverickstube.maverickshub.dataTransferObjects.requests.UpdateMediaRequest;
import com.maverickstube.maverickshub.dataTransferObjects.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dataTransferObjects.responses.MediaResponse;
import com.maverickstube.maverickshub.dataTransferObjects.responses.UpdateMediaResponse;
import com.maverickstube.maverickshub.dataTransferObjects.responses.UpdateMediaResponse2;
import com.maverickstube.maverickshub.dataTransferObjects.responses.UploadMediaResponse;
import com.maverickstube.maverickshub.models.Media;
import com.maverickstube.maverickshub.models.User;

import java.io.IOException;
import java.util.List;

public interface MediaService {
    UploadMediaResponse upload(UploadMediaRequest request);

    Media getMediaBy(long id);


    UpdateMediaResponse updateMedia(UpdateMediaRequest updateMediaRequest) throws IOException;

    List<MediaResponse> getMediaFor(Long userId);
    UpdateMediaResponse2 updateMedia2(Long mediaId, JsonPatch jsonPatch);
}
