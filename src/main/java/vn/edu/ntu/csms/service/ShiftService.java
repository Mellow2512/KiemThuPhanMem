package vn.edu.ntu.csms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.ntu.csms.entity.Shift;
import vn.edu.ntu.csms.repository.ShiftRepository;
import java.util.List;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    public Shift publishShift(Integer shiftId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ca làm."));

        if (shift.getStatus() == Shift.ShiftStatus.PUBLISHED) {
            throw new RuntimeException("Ca làm này đã được duyệt trước đó.");
        }

        shift.setStatus(Shift.ShiftStatus.PUBLISHED);
        return shiftRepository.save(shift);
    }

    // Lấy danh sách ca làm theo mã nhân viên
    public List<Shift> getShiftsByUserId(String userId) {
        return shiftRepository.findByUserId(userId);
    }
}