package eai.formation.scurity.demo.repositories;

import eai.formation.scurity.demo.entities.UserDemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDemo, Long> {
}
