package vn.edu.ntu.csms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ntu.csms.entity.Shift;
import vn.edu.ntu.csms.entity.ShiftSwapRequest;
import vn.edu.ntu.csms.repository.ShiftRepository;
import vn.edu.ntu.csms.repository.ShiftSwapRequestRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ShiftSwapRequestService {

    @Autowired
    private ShiftSwapRequestRepository swapRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    public ShiftSwapRequest createRequest(ShiftSwapRequest request) {
        Shift current = request.getCurrentShift();

        // Quy tắc: Không đồng ý đổi ca trước giờ làm ít hơn 2 tiếng
        LocalDateTime shiftStartDateTime = LocalDateTime.of(current.getWorkDate(), current.getStartTime());
        if (LocalDateTime.now().until(shiftStartDateTime, ChronoUnit.HOURS) < 2) {
            throw new RuntimeException("Không được gửi yêu cầu khi ca làm sắp bắt đầu (Hạn chót: trước 2 giờ).");
        }

        // Quy tắc: Hai ca làm phải thuộc cùng một tuần làm việc
        if (!current.getWorkDate().minusDays(current.getWorkDate().getDayOfWeek().getValue() - 1)
                .equals(request.getTargetShift().getWorkDate().minusDays(request.getTargetShift().getWorkDate().getDayOfWeek().getValue() - 1))) {
            throw new RuntimeException("Hai ca làm phải thuộc cùng một tuần làm việc.");
        }

        request.setStatus(ShiftSwapRequest.SwapStatus.PENDING);
        return swapRepository.save(request);
    }

    @Transactional
    public ShiftSwapRequest reviewRequest(Integer requestId, String decision) {
        ShiftSwapRequest request = swapRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu đổi ca."));

        if (!request.getStatus().equals(ShiftSwapRequest.SwapStatus.PENDING)) {
            throw new RuntimeException("Yêu cầu này đã được xử lý từ trước.");
        }

        if ("APPROVE".equalsIgnoreCase(decision)) {
            request.setStatus(ShiftSwapRequest.SwapStatus.APPROVED);

            // Tiến hành hoán đổi nhân sự của 2 ca làm việc trong Database
            Shift currentShift = request.getCurrentShift();
            Shift targetShift = request.getTargetShift();

            var userTemp = currentShift.getUser();
            currentShift.setUser(targetShift.getUser());
            targetShift.setUser(userTemp);

            shiftRepository.save(currentShift);
            shiftRepository.save(targetShift);
        } else {
            request.setStatus(ShiftSwapRequest.SwapStatus.REJECTED);
        }

        return swapRepository.save(request);
    }
}