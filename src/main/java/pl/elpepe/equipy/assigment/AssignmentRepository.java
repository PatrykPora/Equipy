package pl.elpepe.equipy.assigment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {


    Optional<Assignment> findByAsset_IdAndEndIsNull(Long assetId);
}
