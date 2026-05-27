package vn.edu.ntu.csms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ntu.csms.entity.AvailableTime;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Integer> {
    List<AvailableTime> findByUserIdAndWeekStartDate(String userId, LocalDate weekStartDate);
}