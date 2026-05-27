package vn.edu.ntu.csms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ntu.csms.entity.AvailableTime;
import vn.edu.ntu.csms.service.AvailableTimeService;
import java.util.List;

@RestController
@RequestMapping("/api/available-times")
@CrossOrigin(origins = "*")
public class AvailableTimeController {

    @Autowired
    private AvailableTimeService availableTimeService;

    @PostMapping("/register")
    public List<AvailableTime> register(@RequestBody List<AvailableTime> registrations) {
        return availableTimeService.registerAvailableTimes(registrations);
    }
}