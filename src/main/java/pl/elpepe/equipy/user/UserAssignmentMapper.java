package pl.elpepe.equipy.user;

import pl.elpepe.equipy.asset.Asset;
import pl.elpepe.equipy.assigment.Assignment;

public class UserAssignmentMapper {

    static UserAssignmentDto toDto(Assignment assignment) {
        UserAssignmentDto dto = new UserAssignmentDto();
        dto.setId(assignment.getId());
        dto.setStartRentDay(assignment.getStartRentTime());
        dto.setEndRentDay(assignment.getEndRentTime());
        Asset asset = assignment.getAsset();
        dto.setAssetId(asset.getId());
        dto.setAssetName(asset.getName());
        dto.setAssetSerialNumber(asset.getSerialNumber());
        return dto;
    }


}
