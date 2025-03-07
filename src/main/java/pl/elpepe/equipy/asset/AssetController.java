package pl.elpepe.equipy.asset;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }


    @GetMapping("")
    public List<AssetDto> findAll(@RequestParam(required = false) String text) {
        if (text != null) {
            return assetService.findAllByNameOrSerialNumber(text);
        } else {
            return assetService.findAll();
        }
    }

    @PostMapping("")
    public ResponseEntity<AssetDto> create(@RequestBody AssetDto assetDto) {
        if (assetDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "asset should not have an id");
        }
        AssetDto asset = assetService.save(assetDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(assetDto.getId()).toUri();
        return ResponseEntity.created(location).body(asset);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetDto> findById(@PathVariable Long id) {
        return assetService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetDto> update(@PathVariable Long id, @RequestBody AssetDto assetDto) {
        if (!id.equals(assetDto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id should be equal to id");
        }
        AssetDto updatedAsset = assetService.update(assetDto);
        return ResponseEntity.ok(updatedAsset);
    }

}

