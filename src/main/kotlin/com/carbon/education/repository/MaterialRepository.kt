package com.carbon.education.repository;

import com.carbon.education.model.Material
import org.springframework.data.jpa.repository.JpaRepository

interface MaterialRepository : JpaRepository<Material, Long> {
}