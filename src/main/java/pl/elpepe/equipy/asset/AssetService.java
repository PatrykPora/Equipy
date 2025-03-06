package pl.elpepe.equipy.asset;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    public AssetService(AssetRepository assetRepository, AssetMapper assetMapper) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }


    List<AssetDto> findAll() {
        return assetRepository.findAll()
                .stream()
                .map(assetMapper::toDto)
                .collect(Collectors.toList());
    }

    List<AssetDto> findAllByNameOrSerialNumber(String text){
        return assetRepository.findAllByNameOrSerialNumber(text)
                .stream()
                .map(assetMapper::toDto)
                .toList();
    }

    AssetDto save(AssetDto assetDto) {
        Optional<Asset> assetBySerialNumber = assetRepository.findBySerialNumber(assetDto.getSerialNumber());
        assetBySerialNumber.ifPresent(asset -> {throw new DuplicatedSerialNumberException();});
        Asset assetEntity  = assetMapper.toEntity(assetDto);
        Asset savedAsset = assetRepository.save(assetEntity);
        return assetMapper.toDto(savedAsset);
    }


}
