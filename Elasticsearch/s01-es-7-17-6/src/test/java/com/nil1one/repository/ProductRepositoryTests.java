package com.nil1one.repository;

import com.nil1one.dataobject.ESProductDO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.annotation.Resource;

@SpringBootTest
public class ProductRepositoryTests {
    @Resource
    ProductRepository productRepository;

    @Test
    public void create() {
        for (int i = 0; i < 10; i++) {
            ESProductDO product = new ESProductDO();
            product.setId(i);
            product.setName("嘉华鲜花饼经典玫瑰饼10枚家庭装云南特产零食小吃传统糕点心饼干" + i);
            product.setSellPoint("累计热销超一亿枚 30天短保 享鲜直达");
            product.setDescription("嘉华鲜花饼经典玫瑰饼10枚家庭装云南特产零食小吃传统糕点心饼干");
            product.setCid(0);
            product.setCategoryName("鲜花饼");
            productRepository.save(product);
        }
    }

    @Test
    public void read() {
        Iterable<ESProductDO> users = productRepository.findAll();
        users.forEach(System.out::println);
    }

    @Test
    public void update() {
        ESProductDO product = new ESProductDO();
        product.setId(1);
        product.setCid(100);
        productRepository.save(product);
    }

    @Test
    public void delete() {
        ESProductDO product = new ESProductDO();
        product.setId(1);
        productRepository.delete(product);
    }
}
