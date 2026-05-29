package vn.edu.ntu.csms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ntu.csms.entity.Shift;
import vn.edu.ntu.csms.service.ShiftService;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@CrossOrigin(origins = "*")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    // Lấy tất cả ca làm (Dành cho Quản lý)
    @GetMapping
    public List<Shift> getAllShifts() {
        return shiftService.getAllShifts();
    }

    // Duyệt ca làm (Dành cho Quản lý)
    @PutMapping("/publish/{id}")
    public Shift publishShift(@PathVariable Integer id) {
        return shiftService.publishShift(id);
    }

    // Lấy ca làm theo nhân viên (Dành cho Nhân viên xem lịch)
    @GetMapping("/user/{userId}")
    public List<Shift> getShiftsByUserId(@PathVariable String userId) {
        return shiftService.getShiftsByUserId(userId);
    }
}