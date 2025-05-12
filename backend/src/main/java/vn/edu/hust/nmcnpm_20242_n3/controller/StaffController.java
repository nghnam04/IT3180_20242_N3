package vn.edu.hust.nmcnpm_20242_n3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hust.nmcnpm_20242_n3.entity.User;
import vn.edu.hust.nmcnpm_20242_n3.service.StaffService;

import java.util.List;

@RestController
@RequestMapping("/admin/staff")
public class StaffController {

    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public List<User> getAllStaff() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    public User getStaffById(@PathVariable String id) {
        return staffService.findById(id);
    }

    @PostMapping
    public User addStaff(@RequestBody User user) {
        return staffService.addStaff(user);
    }

    @PutMapping("/{id}")
    public User updateStaff(@PathVariable String id, @RequestBody User userDetails) {
        return staffService.updateStaff(id, userDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable String id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok().body("Staff with ID " + id + " deleted successfully");
    }
}