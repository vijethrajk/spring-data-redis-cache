package com.vrk.app.controller;

import com.vrk.app.dto.ProductDTO;
import com.vrk.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ProductDTO save(@RequestBody ProductDTO productDTO) {
        return productService.saveOrUpdate(productDTO);
    }

    @GetMapping("/all")
    public List<ProductDTO> getProducts() {
        return productService.findAll();
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody Map<String, Long> map) {
        try {
            return new ResponseEntity<String>(productService.remove(map.get("id")), HttpStatus.OK);
        } catch (Exception e) {
            //log e
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
        try {
            ProductDTO dto = productService.findById(id);
            return new ResponseEntity<ProductDTO>(dto, HttpStatus.OK);
        } catch (Exception e) {
            //log e
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
    }
}
