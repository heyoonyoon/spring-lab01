package com.pattern.springlab.service;

import com.pattern.springlab.domain.Memo;
import com.pattern.springlab.repository.MemoRepository;
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
}