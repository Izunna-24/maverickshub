package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dataTransferObjects.requests.UpdateMediaRequest;
import com.maverickstube.maverickshub.dataTransferObjects.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dataTransferObjects.responses.UpdateMediaResponse;
import com.maverickstube.maverickshub.dataTransferObjects.responses.UploadMediaResponse;
import com.maverickstube.maverickshub.models.Media;
import com.maverickstube.maverickshub.models.User;

import java.io.IOException;

public interface MediaService {
    UploadMediaResponse upload(UploadMediaRequest request);

    Media getMediaBy(long id);


    UpdateMediaResponse updateMedia(UpdateMediaRequest updateMediaRequest) throws IOException;
}
