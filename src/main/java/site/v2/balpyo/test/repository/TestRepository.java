package site.v2.balpyo.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.v2.balpyo.test.entity.TestEntity;

public interface TestRepository extends JpaRepository<TestEntity, Integer> {
}

