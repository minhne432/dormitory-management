package com.example.demo.service;

import com.example.demo.entity.ApprovedApplication;
import com.example.demo.repository.ApprovedApplicationRepository;
import com.example.demo.specifications.ApprovedApplicationSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApprovedApplicationService {

    @Autowired
    private ApprovedApplicationRepository approvedApplicationRepository;

    /**
     * Lấy danh sách ứng dụng đã duyệt (ApprovedApplication) có áp dụng bộ lọc.
     */
    public List<ApprovedApplication> getFilteredApplications(
            Long applicationId,
            LocalDate submissionDate,
            LocalDate approvalDate,
            String dormitoryArea,
            String address,
            String department
    ) {
        var spec = ApprovedApplicationSpecification.filter(
                applicationId,
                submissionDate,
                approvalDate,
                dormitoryArea,
                address,
                department
        );

        // Sắp xếp theo approvalDate giảm dần (mới nhất đến cũ nhất)
        return approvedApplicationRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "approvalDate"));
    }
}
