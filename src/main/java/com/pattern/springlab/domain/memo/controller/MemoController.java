package com.pattern.springlab.domain.memo.controller;

import com.pattern.springlab.domain.memo.entity.Memo;
import com.pattern.springlab.domain.memo.service.MemoService;
import com.pattern.springlab.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memos")
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    public ResponseEntity<ApiResponse<Memo>> saveMemo(@RequestBody MemoRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(memoService.saveMemo(request.title(), request.content())));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Memo>>> getAllMemos() {
        return ResponseEntity.ok(ApiResponse.ok(memoService.getAllMemos()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Memo>> updateMemo(@PathVariable Long id, @RequestBody MemoRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(memoService.updateMemo(id, request.title(), request.content())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMemo(@PathVariable Long id) {
        memoService.deleteMemo(id);
        return ResponseEntity.ok(ApiResponse.ok("삭제되었습니다.", null));
    }

    public record MemoRequest(String title, String content) {}
}