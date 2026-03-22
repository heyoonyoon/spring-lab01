package com.pattern.springlab.domain.memo.repository;

import com.pattern.springlab.domain.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}