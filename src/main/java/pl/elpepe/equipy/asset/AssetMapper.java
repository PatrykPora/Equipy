package pl.elpepe.equipy.asset;


import org.springframework.stereotype.Service;
import pl.elpepe.equipy.category.Category;
import pl.elpepe.equipy.category.CategoryRepository;

import java.util.Optional;

@Service
public class AssetMapper {


    private final CategoryRepository categoryRepository;

    public AssetMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    AssetDto toDto(Asset asset) {
        AssetDto assetDto = new AssetDto();
        assetDto.setId(asset.getId());
        assetDto.setName(asset.getName());
        assetDto.setDescription(asset.getDescription());
        assetDto.setSerialNumber(asset.getSerialNumber());
        if (asset.getCategory() != null) {
            assetDto.setCategory(asset.getCategory().getName());
        }
        return assetDto;
    }

    Asset toEntity(AssetDto assetDto) {
        Asset asset = new Asset();
        asset.setId(assetDto.getId());
        asset.setName(assetDto.getName());
        asset.setDescription(assetDto.getDescription());
        asset.setSerialNumber(assetDto.getSerialNumber());
        Optional<Category> category = categoryRepository.findByName(assetDto.getCategory());
        category.ifPresent(asset::setCategory);
        return asset;
    }
}
