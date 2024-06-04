package com.maverickstube.maverickshub.dataTransferObjects.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maverickstube.maverickshub.models.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadMediaResponse {
    @JsonProperty("media_url")
    private String url;
    private Long id;
    @JsonProperty("media_description")
    private String description;
    @JsonProperty("category")
    private Category category;
}
