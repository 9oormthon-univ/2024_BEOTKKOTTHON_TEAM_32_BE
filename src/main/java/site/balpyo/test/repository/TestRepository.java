package site.balpyo.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.balpyo.test.entity.TestEntity;

public interface TestRepository extends JpaRepository<TestEntity, Integer> {
}

