package br.edu.ufscar.backend.mealsfinder.dtos.image;

import br.edu.ufscar.backend.mealsfinder.models.enums.ImageType;

public class ImageUpsertRequestDTO {
    private String url;
    private ImageType imageType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }
}
