package site.v1.balpyo.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.v1.balpyo.test.entity.TestEntity;

public interface TestRepository extends JpaRepository<TestEntity, Integer> {
}

