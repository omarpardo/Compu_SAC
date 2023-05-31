package com.compusac.models.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.compusac.models.entity.Product;

public interface IProductService {

	public List<Product> findAll();

	public Product findById(Long id) throws NotFoundException;

	public Product create(Product product);

	public Product update(Product product, Long id);

	public void delete(Long id);

	// public List<Product> findProductName(String nombre);
	public List<Product> findByIdCategory(int id) throws NotFoundException; 
}
