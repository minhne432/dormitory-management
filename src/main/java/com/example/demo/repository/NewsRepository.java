package com.example.demo.repository;

import com.example.demo.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NewsRepository extends
        JpaRepository<News, Long>,
        PagingAndSortingRepository<News, Long> {

    /** Lấy n bản tin mới nhất */
    Page<News> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
