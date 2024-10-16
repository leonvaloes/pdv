package com.example.demo.Controllers;

import com.example.demo.DTOS.ProductRecordDto;
import com.example.demo.Model.Products;
import com.example.demo.Repository.ProductsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsRepository productsRepository;
    @PostMapping
    public ResponseEntity<Products> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var products = new Products();
        BeanUtils.copyProperties(productRecordDto, products);

        return ResponseEntity.status(HttpStatus.CREATED).body(productsRepository.save(products));
    }
    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "name", required = false) String name) {

        if (id != null) {
            Optional<Products> product = productsRepository.findById(id);
            return product.map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        Map<String, String> response = new HashMap<>();
                        response.put("error", "Produto com id " + id + " não encontrado");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body((Products) response);
                    });
        }

        if (name != null) {
            Optional<Products> product = productsRepository.findByName(name);
            return product.map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        Map<String, String> response = new HashMap<>();
                        response.put("error", "Produto com nome " + name + " não encontrado");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body((Products) response);
                    });
        }
        
        List<Products> allProducts = productsRepository.findAll();
        return ResponseEntity.ok(allProducts);
    }

    public ResponseEntity<Products> getProductByName(@PathVariable String name) {
        Optional<Products> product = productsRepository.findByName(name);

        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
