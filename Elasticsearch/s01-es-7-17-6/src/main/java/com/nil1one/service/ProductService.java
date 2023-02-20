package com.nil1one.service;


import com.nil1one.repository.ProductRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductService {
    @Resource
    ProductRepository productRepository;
    @Resource
    ElasticsearchRestTemplate restTemplate;


}
