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

    List<AssetDto> findAllByNameOrSerialNumber(String text) {
        return assetRepository.findAllByNameOrSerialNumber(text)
                .stream()
                .map(assetMapper::toDto)
                .toList();
    }

    AssetDto save(AssetDto assetDto) {
        Optional<Asset> assetBySerialNumber = assetRepository.findBySerialNumber(assetDto.getSerialNumber());
        assetBySerialNumber.ifPresent(asset -> {
            throw new DuplicatedSerialNumberException();
        });
        Asset assetEntity = assetMapper.toEntity(assetDto);
        Asset savedAsset = assetRepository.save(assetEntity);
        return assetMapper.toDto(savedAsset);
    }

    Optional<AssetDto> findById(Long id) {
        return assetRepository.findById(id).map(assetMapper::toDto);
    }

    AssetDto update(AssetDto assetDto) {
        Optional<Asset> assetBySerialNumber = assetRepository.findBySerialNumber(assetDto.getSerialNumber());
        assetBySerialNumber.ifPresent(asset -> {
            if (!asset.getId().equals(assetDto.getId())) {
                throw new DuplicatedSerialNumberException();
            }
        });
        Asset assetEntity = assetMapper.toEntity(assetDto);
        Asset savedAsset = assetRepository.save(assetEntity);
        return assetMapper.toDto(savedAsset);
    }

    public List<AssetAssignmentDto> getAssetAssignments(Long assetId) {
        return assetRepository.findById(assetId)
                .map(Asset::getAssignments)
                .orElseThrow(AssetNotFoundException::new)
                .stream()
                .map(AssetAssignmentMapper::toDto)
                .toList();
    }
}
