package vn.edu.ntu.csms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.ntu.csms.entity.AvailableTime;
import vn.edu.ntu.csms.repository.AvailableTimeRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.util.List;

@Service
public class AvailableTimeService {

    @Autowired
    private AvailableTimeRepository availableTimeRepository;

    public List<AvailableTime> registerAvailableTimes(List<AvailableTime> registrations) {
        if (registrations.isEmpty()) return registrations;

        // Quy tắc: Chỉ cho phép đăng ký từ Thứ Hai đến Thứ Năm
        DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
        if (currentDay == DayOfWeek.FRIDAY || currentDay == DayOfWeek.SATURDAY || currentDay == DayOfWeek.SUNDAY) {
            throw new RuntimeException("Ngoài thời gian đăng ký (Hệ thống đóng đăng ký sau 23h59 Thứ Năm).");
        }

        for (AvailableTime time : registrations) {
            // Quy tắc: Giờ bắt đầu < Giờ kết thúc
            if (!time.getStartTime().isBefore(time.getEndTime())) {
                throw new RuntimeException("Thời gian không hợp lệ: Giờ bắt đầu phải nhỏ hơn giờ kết thúc.");
            }

            // Quy tắc: Kiểm tra khung giờ hoạt động của rạp
            LocalTime startLimit = LocalTime.of(8, 0);
            if (time.getStartTime().isBefore(startLimit)) {
                throw new RuntimeException("Thời gian không hợp lệ: Rạp chỉ hoạt động từ 08:00.");
            }

            // Quy tắc: Thời lượng ca làm chỉ được thuộc các mức 4, 6, 8, 10 tiếng
            long hours = Duration.between(time.getStartTime(), time.getEndTime()).toHours();
            if (hours != 4 && hours != 6 && hours != 8 && hours != 10) {
                throw new RuntimeException("Vi phạm giới hạn: Chỉ được chọn các mức ca làm 4, 6, 8, 10 giờ.");
            }
        }

        // Kiểm tra trùng lặp thời gian trong cùng một ngày
        for (int i = 0; i < registrations.size(); i++) {
            for (int j = i + 1; j < registrations.size(); j++) {
                AvailableTime t1 = registrations.get(i);
                AvailableTime t2 = registrations.get(j);
                if (t1.getAvailableDate().equals(t2.getAvailableDate())) {
                    if (t1.getStartTime().isBefore(t2.getEndTime()) && t2.getStartTime().isBefore(t1.getEndTime())) {
                        throw new RuntimeException("Thời gian trùng hoặc chồng chéo ca trong cùng một ngày.");
                    }
                }
            }
        }

        return availableTimeRepository.saveAll(registrations);
    }
}