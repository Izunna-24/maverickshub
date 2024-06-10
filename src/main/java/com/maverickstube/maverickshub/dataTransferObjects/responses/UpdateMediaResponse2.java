package com.maverickstube.maverickshub.dataTransferObjects.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maverickstube.maverickshub.models.Category;
import lombok.Data;

@Data
public class UpdateMediaResponse2 {
    @JsonProperty("media_url")
    private String url;
    @JsonProperty("media_description")
    private String description;
    private Long id;
    private Category category;
}
