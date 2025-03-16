package pl.elpepe.equipy.assigment;

import pl.elpepe.equipy.asset.Asset;
import pl.elpepe.equipy.user.User;

public class AssignmentMapper {

    static AssignmentDto toAssignmentDto(Assignment assignment) {
        AssignmentDto dto = new AssignmentDto();
        User user = assignment.getUser();
        dto.setId(assignment.getId());
        dto.setStart(assignment.getStart());
        dto.setEnd(assignment.getEnd());
        dto.setUserId(user.getId());
        Asset asset = assignment.getAsset();
        dto.setAssetId(asset.getId());
        return dto;
    }
}
