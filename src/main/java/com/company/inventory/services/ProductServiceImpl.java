package com.company.inventory.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.dao.IProductDao;
import com.company.inventory.model.Category;
import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;

@Service

public class ProductServiceImpl implements IProductService {
	
	//inyectar dependencias al constructor a objeto categoryDao
	private ICategoryDao categoryDao;
	private IProductDao productDao;
	//generando el constructor
	public ProductServiceImpl(ICategoryDao categoryDao,IProductDao productDao) {
		super();
		this.categoryDao = categoryDao;
		this.productDao= productDao;
	}
	
	@Override
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		//metodo SAVE --guardar de toda la logica del negocio
		ProductResponseRest response = new ProductResponseRest();
		//en esta lista va almacenar la respuesta de lo q guardemos ProductResponseRest
		List<Product> list = new ArrayList<>();
		
		try {
			
			//buscar categoria para setearl al objeto producto 
			Optional <Category> category = categoryDao.findById(categoryId);
			
			if ( category.isPresent()) {
				//aqui se obtiene la categoria para el metodo guardar EN TRY CATCH
				product.setCategory(category.get());
			} else {
				response.setMetadata("respuesta nok","-1", "Categoria no encontrada asociada al producto");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			//guardado del producto
			Product productSaved = productDao.save(product);
			
			if (productSaved != null) {
				list.add(productSaved);
				response.getProduct().setProducts(list);
				response.setMetadata("respuesta ok","00","Producto guardado encontrada asociada al producto");

			} else {
				response.setMetadata("respuesta nok","-1", "Producto no encontrado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta nok","-1", "Error al guardar producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);

	
	}

}
