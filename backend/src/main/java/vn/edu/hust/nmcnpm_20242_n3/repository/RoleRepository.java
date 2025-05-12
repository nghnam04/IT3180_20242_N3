package vn.edu.hust.nmcnpm_20242_n3.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hust.nmcnpm_20242_n3.entity.Role;
import vn.edu.hust.nmcnpm_20242_n3.constant.RoleEnum;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(RoleEnum name);
}