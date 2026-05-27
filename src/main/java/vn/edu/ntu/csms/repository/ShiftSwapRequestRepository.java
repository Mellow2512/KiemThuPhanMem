package vn.edu.ntu.csms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ntu.csms.entity.ShiftSwapRequest;

@Repository
public interface ShiftSwapRequestRepository extends JpaRepository<ShiftSwapRequest, Integer> {
}