package vn.edu.hust.nmcnpm_20242_n3.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hust.nmcnpm_20242_n3.entity.User;
import vn.edu.hust.nmcnpm_20242_n3.entity.Role;
import vn.edu.hust.nmcnpm_20242_n3.repository.UserRepository;
import vn.edu.hust.nmcnpm_20242_n3.constant.RoleEnum;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {

    private final UserRepository userRepository;

    @Autowired
    public StaffService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Lấy danh sách tất cả staff (STAFF và ADMIN)
    public List<User> getAllStaff() {
        List<User> staffList = new ArrayList<>();
        staffList.addAll(userRepository.findByRole_Name(RoleEnum.STAFF));
        staffList.addAll(userRepository.findByRole_Name(RoleEnum.ADMIN));
        return staffList;
    }

    // Tìm staff theo ID
    public User findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + id));
        if (user.getRole() == null || (!RoleEnum.STAFF.equals(user.getRole().getName()) && !RoleEnum.ADMIN.equals(user.getRole().getName()))) {
            throw new IllegalArgumentException("User is not a staff or admin");
        }
        return user;
    }

    // Thêm staff mới
    @Transactional
    public User addStaff(User user) {
        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null ||
            user.getName().trim().isEmpty() || user.getEmail().trim().isEmpty() || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Name, email, and password are required");
        }

        if (userRepository.findByEmail(user.getEmail()).stream().anyMatch(u -> u.getRole() != null &&
            (RoleEnum.STAFF.equals(u.getRole().getName()) || RoleEnum.ADMIN.equals(u.getRole().getName())))) {
            throw new IllegalArgumentException("Staff with email " + user.getEmail() + " already exists");
        }

        Role role = user.getRole();
        if (role == null || role.getName() == null) {
            role = new Role();
            role.setName(RoleEnum.STAFF);
            user.setRole(role);
        } else if (!RoleEnum.STAFF.equals(role.getName()) && !RoleEnum.ADMIN.equals(role.getName())) {
            throw new IllegalArgumentException("Role must be STAFF or ADMIN");
        }

        return userRepository.save(user);
    }

    // Cập nhật staff
    @Transactional
    public User updateStaff(String id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + id));
        if (existingUser.getRole() == null || (!RoleEnum.STAFF.equals(existingUser.getRole().getName()) && !RoleEnum.ADMIN.equals(existingUser.getRole().getName()))) {
            throw new IllegalArgumentException("User is not a staff or admin");
        }

        if (userDetails.getName() != null && !userDetails.getName().trim().isEmpty()) {
            existingUser.setName(userDetails.getName());
        }
        if (userDetails.getEmail() != null && !userDetails.getEmail().trim().isEmpty()) {
            existingUser.setEmail(userDetails.getEmail());
        }
        if (userDetails.getUserName() != null && !userDetails.getUserName().trim().isEmpty()) {
            existingUser.setUserName(userDetails.getUserName());
        }
        if (userDetails.getPassword() != null && !userDetails.getPassword().trim().isEmpty()) {
            existingUser.setPassword(userDetails.getPassword());
        }
        if (userDetails.getRole() != null && userDetails.getRole().getName() != null) {
            if (!RoleEnum.STAFF.equals(userDetails.getRole().getName()) && !RoleEnum.ADMIN.equals(userDetails.getRole().getName())) {
                throw new IllegalArgumentException("Role must be STAFF or ADMIN");
            }
            existingUser.setRole(userDetails.getRole());
        }

        return userRepository.save(existingUser);
    }

    // Xóa staff
    @Transactional
    public void deleteStaff(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + id));
        if (user.getRole() == null || (!RoleEnum.STAFF.equals(user.getRole().getName()) && !RoleEnum.ADMIN.equals(user.getRole().getName()))) {
            throw new IllegalArgumentException("User is not a staff or admin");
        }
        userRepository.delete(user);
    }
}