package com.pattern.springlab.domain.memo.controller;

import com.pattern.springlab.domain.memo.entity.Memo;
import com.pattern.springlab.domain.memo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memos")
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    public Memo saveMemo(@RequestBody MemoRequest request) {
        return memoService.saveMemo(request.title(), request.content());
    }

    @GetMapping
    public List<Memo> getAllMemos() {
        return memoService.getAllMemos();
    }

    @PutMapping("/{id}")
    public Memo updateMemo(@PathVariable Long id, @RequestBody MemoRequest request) {
        return memoService.updateMemo(id, request.title(), request.content());
    }

    @DeleteMapping("/{id}")
    public void deleteMemo(@PathVariable Long id) {
        memoService.deleteMemo(id);
    }

    public record MemoRequest(String title, String content) {}

}