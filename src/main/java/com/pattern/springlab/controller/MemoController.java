package com.pattern.springlab.controller;

import com.pattern.springlab.domain.Memo;
import com.pattern.springlab.service.MemoService;
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

    public record MemoRequest(String title, String content) {}
}