package vn.edu.ntu.csms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.ntu.csms.entity.ShiftSwapRequest;
import vn.edu.ntu.csms.service.ShiftSwapRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/shift-swaps")
@CrossOrigin(origins = "*")
public class ShiftSwapController {

    @Autowired
    private ShiftSwapRequestService swapService;

    @PostMapping("/request")
    public ShiftSwapRequest createRequest(@RequestBody ShiftSwapRequest request) {
        return swapService.createRequest(request);
    }

    @PutMapping("/review/{id}")
    public ShiftSwapRequest reviewRequest(@PathVariable Integer id, @RequestParam String decision) {
        return swapService.reviewRequest(id, decision);
    }
    @GetMapping
    public List<ShiftSwapRequest> getAllRequests() {
        return swapService.getAllRequests();
    }
}