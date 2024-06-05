package com.maverickstube.maverickshub.dataTransferObjects.requests;

import com.maverickstube.maverickshub.models.Category;
import lombok.Data;


@Data
public class UpdateMediaRequest {
    private Long mediaID;
    private String description;
    private Category category;
}
