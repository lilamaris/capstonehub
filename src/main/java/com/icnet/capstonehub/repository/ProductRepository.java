package com.icnet.capstonehub.repository;

import com.icnet.capstonehub.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
