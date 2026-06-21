package com.mark.community.repository;

import com.mark.community.entity.Report;
import com.mark.community.entity.key.PostReportId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReportRepository extends JpaRepository<Report, PostReportId> {
}
