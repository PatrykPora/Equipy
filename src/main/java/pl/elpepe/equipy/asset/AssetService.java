package pl.elpepe.equipy.asset;

import org.springframework.stereotype.Service;

import java.util.List;
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


}
