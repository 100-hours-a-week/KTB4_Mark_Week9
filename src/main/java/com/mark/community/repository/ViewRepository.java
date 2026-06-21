package com.mark.community.repository;

import com.mark.community.entity.View;
import com.mark.community.entity.key.PostViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<View, PostViewId> {

}
