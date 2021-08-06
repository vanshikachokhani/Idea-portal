package com.psl.idea.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.psl.idea.models.Category;
import com.psl.idea.repository.CategoryRepository;

@WebMvcTest(controllers=CategoryService.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {
	
	@MockBean
	CategoryRepository categoryRepository;
	
	@Autowired
	CategoryService categoryService;
	
	Category category = new Category(1, "WebApp");
	
	@Test
	public void getAllCategoriesTest() {
		List<Category> categories = new ArrayList<>();
		categories.add(category);
		
		when(categoryRepository.findAll()).thenReturn(categories);
		
		assertEquals(categories, categoryService.getAllCategories());
		
		verify(categoryRepository).findAll();
	}

}
