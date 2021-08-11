package com.stackroute.keepnote.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.repository.CategoryRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class CategoryServiceImpl implements CategoryService {

	/*
	 * Autowiring should be implemented for the CategoryRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	/*
	 * This method should be used to save a new category.Call the corresponding
	 * method of Respository interface.
	 */
	public Category createCategory(Category category) throws CategoryNotCreatedException {

		Category createCategorySuccess = this.categoryRepository.insert(category);
		if(createCategorySuccess != null) {
			return createCategorySuccess;
		}
		else {
			throw new CategoryNotCreatedException("Category not created");
		}
	}

	/*
	 * This method should be used to delete an existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteCategory(String categoryId) throws NoSuchElementException {
		try {
			Category categoryDetails = this.categoryRepository.findById(categoryId).get();
			if(categoryDetails != null) {
				this.categoryRepository.deleteById(categoryId);
				return true;
			}
			else {
				throw new NoSuchElementException();
			}
		}
		catch(Exception e) {
			throw new NoSuchElementException();
		}
	}

	/*
	 * This method should be used to update a existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public Category updateCategory(Category category, String categoryId) {

		Optional<Category> categoryDetailsforUpdate = this.categoryRepository.findById(categoryId);
		if(categoryDetailsforUpdate.isPresent()) {
			Category categoryDetails = categoryDetailsforUpdate.get();
			
			if(categoryDetails.getId().equals(categoryId)) {
				category.setId(categoryId);
				categoryDetails = category;
				this.categoryRepository.save(category);
				return categoryDetails;
			}
			
			return categoryDetails;
			
		}
		else {
			return null;
		}
	}

	/*
	 * This method should be used to get a category by categoryId.Call the
	 * corresponding method of Respository interface.
	 */
	public Category getCategoryById(String categoryId) throws CategoryNotFoundException {
		try {
			Optional<Category> categoryDetails = this.categoryRepository.findById(categoryId);
			if(categoryDetails.isPresent()) {
				return categoryDetails.get();
			}
			else {
				throw new NoSuchElementException();
			}
		}
		catch(Exception e) {
			throw new CategoryNotFoundException("Category not found");
		}
		
	}

	/*
	 * This method should be used to get a category by userId.Call the corresponding
	 * method of Respository interface.
	 */
	public List<Category> getAllCategoryByUserId(String userId) {

		return this.categoryRepository.findAllCategoryByCategoryCreatedBy(userId);
		
	}
}
