package com.pattern.springlab.domain.memo.service;

import com.pattern.springlab.domain.memo.entity.Memo;
import com.pattern.springlab.domain.memo.repository.MemoRepository;
import com.pattern.springlab.global.exception.BusinessException;
import com.pattern.springlab.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    public Memo saveMemo(String title, String content) {
        Memo memo = new Memo(title, content);
        return memoRepository.save(memo);
    }

    public List<Memo> getAllMemos() {
        return memoRepository.findAll();
    }

    @Transactional
    public Memo updateMemo(Long id, String title, String content) {
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMO_NOT_FOUND));
        memo.update(title, content);
        return memo;
    }



    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }
}