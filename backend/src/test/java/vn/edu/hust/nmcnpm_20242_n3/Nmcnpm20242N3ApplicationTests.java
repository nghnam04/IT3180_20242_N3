package vn.edu.hust.nmcnpm_20242_n3;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
@EntityScan(basePackages = "vn.edu.hust.nmcnpm_20242_n3.entity")
@EnableJpaRepositories(basePackages = "vn.edu.hust.nmcnpm_20242_n3.repository")
@ComponentScan(basePackages = "vn.edu.hust.nmcnpm_20242_n3")
class Nmcnpm20242N3ApplicationTests {

	@Test
	void contextLoads() {
	}

}
