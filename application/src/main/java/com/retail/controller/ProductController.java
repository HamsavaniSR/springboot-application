package com.retail.controller;



import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.Resource;
import  org.springframework.hateoas.mvc.ControllerLinkBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;  // important for linkTo
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.retail.exception.ProductNotFoundException;
import com.retail.model.ProductDetails;
import com.retail.repository.ProductRepository;
import com.retail.service.Sender;

@RestController
@RequestMapping("/products")
@EnableMongoRepositories(basePackages = "com.retail")
public class ProductController {

	@Autowired
	ProductRepository repo; 
	@Autowired
	Sender sender;
	
	@RequestMapping(value="/" , method = RequestMethod.GET )
	public List<ProductDetails> getAllProducts() {
		System.out.println("getAllProducts");
		return repo.findAll();
		
	}
		
/*	@RequestMapping(value="/admin/{id}" , method = RequestMethod.GET)
	@Cacheable(value="productdetailsCache",key="#id")
	public Resource<ProductDetails> getSingleProductById(@PathVariable(value = "id") String id)  {
		System.out.println("getSingleProductById");
		ProductDetails product =   repo.findProductById(Integer.parseInt(id));
		if(product == null)
			throw new ProductNotFoundException("Product not found with this id =="+id);
		Resource<ProductDetails> resource = new Resource<ProductDetails>(product);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllProducts());
		resource.add(linkTo.withRel("all-products"));
		return resource;
	}
*/	
	@RequestMapping(value="/admin/{id}" , method = RequestMethod.GET)
	@Cacheable(value="productdetailsCache",key="#id")
	public ProductDetails getSingleProductById(@PathVariable(value = "id") String id)  {
		System.out.println("getSingleProductById");
		ProductDetails product =   repo.findProductById(Integer.parseInt(id));
		if(product == null)
			throw new ProductNotFoundException("Product not found with this id =="+id);
		return product;
	}
	
	@RequestMapping(value="/admin/{id}" , method = RequestMethod.DELETE)
	@CacheEvict(value="productdetailsCache",key="#id")
	public void deleteProductById(@PathVariable(value = "id") String id)  {
		System.out.println("deleteProductById");
		   repo.deleteById(Integer.parseInt(id));
	}
	
/*	@RequestMapping(value="/admin",method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<ProductDetails> saveProduct(@RequestBody ProductDetails productDetails) {
		System.out.println("saveProduct");
		ProductDetails savedProduct=repo.save(productDetails);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId()).toUri();
		//System.out.println(location+" "+savedProduct.getId());
		//System.out.println(ResponseEntity.created(location).build());
		return ResponseEntity.created(location).build();
		
	}*/
	
	@RequestMapping(value="/admin",method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
	public void saveProduct(@RequestBody ProductDetails productDetails) {
		System.out.println("saveProduct");
		ProductDetails savedProduct=repo.save(productDetails);
		//URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId()).toUri();
		//System.out.println(location+" "+savedProduct.getId());
		//System.out.println(ResponseEntity.created(location).build());
		//return ResponseEntity.created(location).build();
		
	}
	
	
/*	@RequestMapping(value="/admin/{id}",method = RequestMethod.PUT , consumes = MediaType.APPLICATION_JSON_VALUE )
	@CachePut(value="productdetailsCache",key="#id")
	public ResponseEntity<ProductDetails> updateProduct(@PathVariable(value = "id") String id,@RequestBody ProductDetails productDetails) {
		System.out.println("updateProduct");
		ProductDetails product =   repo.findProductById(Integer.parseInt(id));
		if(product == null)
			throw new ProductNotFoundException("Product not found with this id =="+id);
		else
		{
			if(productDetails.getId()==null) {
				productDetails.setId(product.getId());
			}
			if(productDetails.getProduct_description()==null) {
				productDetails.setProduct_description(product.getProduct_description());
			}
			if(productDetails.getCurrency_code()==null) {
				productDetails.setCurrency_code(product.getCurrency_code());
			}
			if(productDetails.getCurrent_price()==null) {
				productDetails.setCurrent_price(product.getCurrent_price());
			}
		}
		ProductDetails savedProduct=repo.save(productDetails);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId()).toUri();
		//System.out.println(location+" "+savedProduct.getId());
		//System.out.println(ResponseEntity.created(location).build());
		return ResponseEntity.created(location).build();
	}*/	
	
	
	@RequestMapping(value="/admin/{id}",method = RequestMethod.PUT , consumes = MediaType.APPLICATION_JSON_VALUE )
	@CachePut(value="productdetailsCache",key="#id")
	public ProductDetails updateProduct(@PathVariable(value = "id") String id,@RequestBody ProductDetails productDetails) {
		System.out.println("updateProduct");
		ProductDetails product =   repo.findProductById(Integer.parseInt(id));
		if(product == null)
			throw new ProductNotFoundException("Product not found with this id =="+id);
		else
		{
			if(productDetails.getId()==null) {
				productDetails.setId(product.getId());
			}
			if(productDetails.getProduct_description()==null) {
				productDetails.setProduct_description(product.getProduct_description());
			}
			if(productDetails.getCurrency_code()==null) {
				productDetails.setCurrency_code(product.getCurrency_code());
			}
			if(productDetails.getCurrent_price()==null) {
				productDetails.setCurrent_price(product.getCurrent_price());
			}
		}
		ProductDetails savedProduct=repo.save(productDetails);
		//URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId()).toUri();
		//System.out.println(location+" "+savedProduct.getId());
		//System.out.println(ResponseEntity.created(location).build());
		return savedProduct;
	}
	
	//kafka queue implemented
	@RequestMapping(value="/admin/bulk",method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
	public void saveManyProduct(@RequestBody List<ProductDetails> productDetailsList) {
		System.out.println("saveManyProduct");
		productDetailsList.forEach(productDetails -> this.sender.send(productDetails));

	}
	
	
	@RequestMapping(value="/user/{id}" , method = RequestMethod.GET)
	@Cacheable(value="productdetailsCache",key="#id")
	public ProductDetails getUserSingleProductById(@PathVariable(value = "id") String id)  {
		System.out.println("getUserSingleProductById");
		ProductDetails product =   repo.findProductById(Integer.parseInt(id));
		if(product == null)
			throw new ProductNotFoundException("Product not found with this id =="+id);
		return product;
	}
	
	//kafka queue implemented
	
	@RequestMapping(value="/user/bulk",method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
	public void saveUserManyProduct(@RequestBody List<ProductDetails> productDetailsList) {
		System.out.println("saveUserManyProduct");
		productDetailsList.forEach(productDetails -> this.sender.send(productDetails));

	}
	
	@RequestMapping(value="/user",method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
	public void saveUserProduct(@RequestBody ProductDetails productDetails) {
		System.out.println("saveProduct");
		repo.save(productDetails);
	}
	
	@RequestMapping(value="/user/{id}" , method = RequestMethod.DELETE)
	@CacheEvict(value="productdetailsCache",key="#id")
	public void deleteUserProductById(@PathVariable(value = "id") String id)  {
		System.out.println("deleteUserProductById");
		   repo.deleteById(Integer.parseInt(id));
	}
	
	@RequestMapping(value="/user/{id}",method = RequestMethod.PUT , consumes = MediaType.APPLICATION_JSON_VALUE )
	@CachePut(value="productdetailsCache",key="#id")
	public ProductDetails updateUserProduct(@PathVariable(value = "id") String id,@RequestBody ProductDetails productDetails) {
		System.out.println("updateUserProduct");
		ProductDetails product =   repo.findProductById(Integer.parseInt(id));
		if(product == null)
			throw new ProductNotFoundException("Product not found with this id =="+id);
		else
		{
			if(productDetails.getId()==null) {
				productDetails.setId(product.getId());
			}
			if(productDetails.getProduct_description()==null) {
				productDetails.setProduct_description(product.getProduct_description());
			}
			if(productDetails.getCurrency_code()==null) {
				productDetails.setCurrency_code(product.getCurrency_code());
			}
			if(productDetails.getCurrent_price()==null) {
				productDetails.setCurrent_price(product.getCurrent_price());
			}
		}
		ProductDetails savedProduct=repo.save(productDetails);
		return savedProduct;
	}


}
