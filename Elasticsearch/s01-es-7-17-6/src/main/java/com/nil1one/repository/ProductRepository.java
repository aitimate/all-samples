package com.nil1one.repository;

import com.nil1one.dataobject.ESProductDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

// 说明: 无需`@Repository`，父类ElasticsearchRepository上已有注册相关的注解
public interface ProductRepository extends ElasticsearchRepository<ESProductDO, Integer> {
    // 自动生成，详见 `简单查询`
    ESProductDO findByName(String name);

    // 自动生成，详见 `简单查询`
    Page<ESProductDO> findByNameLike(String name, Pageable pageable);
}