package vn.edu.hust.nmcnpm_20242_n3.service;

import org.springframework.stereotype.Service;
import vn.edu.hust.nmcnpm_20242_n3.entity.Staff;
import vn.edu.hust.nmcnpm_20242_n3.repository.StaffRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Optional<Staff> getStaffById(String id) {
        return staffRepository.findById(id);
    }

    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public Staff updateStaff(String id, Staff staffDetails) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
        staff.setName(staffDetails.getName());
        staff.setEmail(staffDetails.getEmail());
        staff.setRole(staffDetails.getRole());
        return staffRepository.save(staff);
    }

    public void deleteStaff(String id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
        staffRepository.delete(staff);
    }
}