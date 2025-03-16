package pl.elpepe.equipy.assigment;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.elpepe.equipy.asset.Asset;
import pl.elpepe.equipy.asset.AssetRepository;
import pl.elpepe.equipy.asset.AssetService;
import pl.elpepe.equipy.user.User;
import pl.elpepe.equipy.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AssignmentService {



    private final AssignmentRepository assignmentRepository;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, AssetRepository assetRepository, UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    AssignmentDto createAssignment(AssignmentDto assignmentDto){
        Optional<Assignment> activeAssigmentForAsset = assignmentRepository.findByAsset_IdAndEndIsNull(assignmentDto.getAssetId());
        activeAssigmentForAsset.ifPresent(assignment -> {
            throw new InvalidAssignmentException("this assignment is in use by other user at the moment");
        });
        Optional<User> user = userRepository.findById(assignmentDto.getUserId());
        Optional<Asset> asset = assetRepository.findById(assignmentDto.getAssetId());
        Assignment assignment = new Assignment();
        assignment.setUser(user.orElseThrow(() -> new InvalidAssignmentException("user not found")));
        assignment.setAsset(asset.orElseThrow(() -> new InvalidAssignmentException("asset not found")));
        assignment.setStart(LocalDateTime.now());
        return AssignmentMapper.toAssignmentDto(assignmentRepository.save(assignment));
    }

    @Transactional
    public LocalDateTime finishAssignment(Long assignmentId){
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        Assignment assignmentEntity = assignment.orElseThrow(AssignmentNotFoundException::new);
        if (assignmentEntity.getEnd() != null) {
            throw new AssignmentAlreadyFinishedException();
        } else {
            assignmentEntity.setEnd(LocalDateTime.now());
        }
        return assignmentEntity.getEnd();
    }

}
