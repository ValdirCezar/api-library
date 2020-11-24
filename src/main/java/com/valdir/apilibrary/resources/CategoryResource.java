package com.valdir.apilibrary.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.valdir.apilibrary.domain.Category;
import com.valdir.apilibrary.dtos.CategoryDTO;
import com.valdir.apilibrary.services.CategoryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@ApiOperation(value = "return a list of CategoryDTO")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Succes method return")
	})
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<Category> list = service.findAll();
		List<CategoryDTO> listDTO = list.stream().map(obj -> service.fromDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@ApiOperation(value = "return a unique Category by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success method return")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Integer id) {
		Category obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "return a new CategoryDTO")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "created")
	})
	@PostMapping
	public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO objDTO) {
		CategoryDTO newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}

}
