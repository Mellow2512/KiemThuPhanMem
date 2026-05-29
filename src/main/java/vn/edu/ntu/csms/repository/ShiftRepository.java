package vn.edu.ntu.csms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ntu.csms.entity.Shift;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    List<Shift> findByUserIdAndWorkDate(String userId, LocalDate workDate);
    List<Shift> findByWorkDateBetween(LocalDate start, LocalDate end);

    // Hàm tìm kiếm toàn bộ ca làm việc của một nhân viên
    List<Shift> findByUserId(String userId);
}