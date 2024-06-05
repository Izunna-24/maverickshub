package com.maverickstube.maverickshub.dataTransferObjects.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maverickstube.maverickshub.models.Category;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UpdateMediaResponse {
    @JsonProperty("media_description")
    private String newDescription;
    @JsonProperty("category")
    private Category newCategory;
}
