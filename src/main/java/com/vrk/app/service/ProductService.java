package com.vrk.app.service;

import com.vrk.app.dto.ProductDTO;
import com.vrk.app.entity.Product;
import com.vrk.app.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Cacheable("all-products")
    public List<ProductDTO> findAll() {
        log.info("fetching all records from db");
        return productRepository.findAll().stream().map(e->{
            ProductDTO productDTO=new ProductDTO();
            BeanUtils.copyProperties(e,productDTO);
            return productDTO;
        }).collect(Collectors.toList());
    }

    @Cacheable("product")
    public ProductDTO findById(long id) throws Exception {
        log.info("fetching  record from db for id : {}",id);
       Product product= productRepository.findById(id).orElseThrow(()->{
            return new Exception("Product Not Found!");
        });
        ProductDTO productDTO=new ProductDTO();
        BeanUtils.copyProperties(product,productDTO);
        return productDTO;

    }

    @Caching(evict = {
            @CacheEvict(value = "all-products",allEntries = true),
            @CacheEvict(value = "product",allEntries = true)
    })
    public ProductDTO saveOrUpdate(ProductDTO dto) {
        log.info("Saving into db");
        Product product = new Product();
        if (dto.getId()!=null){
            product.setId(dto.getId());
        }

        BeanUtils.copyProperties(dto, product); // can user builder to construct entity obj
        product = productRepository.save(product);
        BeanUtils.copyProperties(product, dto);
        return dto;
    }

    @Caching(evict = {
            @CacheEvict(value = "all-products",allEntries = true),
            @CacheEvict(value = "product",allEntries = true)
    })
    public String remove(Long id) throws Exception {
        log.info("Removing product id {} from db",id);
        Product product= productRepository.findById(id).orElseThrow(()->{
            return new Exception("Product Not Found!");
        });
        productRepository.delete(product);
        return "Product ID :"+id+" removed";
    }
}
