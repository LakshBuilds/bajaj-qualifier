package com.bajajfinserv.healthqualifier.repository;

import com.bajajfinserv.healthqualifier.entity.SqlResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlResultRepository extends JpaRepository<SqlResult, Long> {
} 