package com.maverickstube.maverickshub.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.maverickstube.maverickshub.dataTransferObjects.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dataTransferObjects.responses.UploadMediaResponse;
import com.maverickstube.maverickshub.exceptions.MediaNotFoundException;
import com.maverickstube.maverickshub.exceptions.MediaUploadFailedException;
import com.maverickstube.maverickshub.models.Media;
import com.maverickstube.maverickshub.models.User;
import com.maverickstube.maverickshub.repositories.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j// simple logic 4 java
public class MavericksHubMediaService implements MediaService {

    private final MediaRepository mediaRepository;
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;
    private final MavericksHubUserService mavericksHubUserService;


    @Override
    public UploadMediaResponse upload(UploadMediaRequest request) {
    User user = mavericksHubUserService.getById(request.getUserId());
        try {
            Uploader uploader = cloudinary.uploader();
            Map<?, ?> response = uploader.upload(request.getMediaFile()
                    .getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            String url = response.get("url").toString();
            Media media = modelMapper.map(request, Media.class);
            media.setUrl(url);
            media.setUploader(user);
            media = mediaRepository.save(media);
            return modelMapper.map(media, UploadMediaResponse.class);

        } catch (IOException exception) {
            throw new MediaUploadFailedException("media upload failed");
        }

    }

    @Override
    public Media getMediaBy(long id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException("media not found"));
    }
}
