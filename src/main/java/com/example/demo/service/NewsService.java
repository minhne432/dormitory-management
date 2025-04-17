package com.example.demo.service;

import com.example.demo.entity.News;
import com.example.demo.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepo;

    /** Bảng tin cho sinh viên – lấy tối đa `limit` tin mới nhất */
    public List<News> getLatest(int limit) {
        return newsRepo.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit))
                .getContent();
    }

    // CRUD cho quản lý
    public News save(News n) { return newsRepo.save(n); }
    public Page<News> findAll(int page, int size) {
        return newsRepo.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }
    public void delete(Long id) { newsRepo.deleteById(id); }

    public Optional<News> findById(Long id) {
        return newsRepo.findById(id);
    }
}
