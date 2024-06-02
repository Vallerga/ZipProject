package com.zip_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.model.ApiList;
import com.zip_project.service.crud.ApiListService;

@RestController
@RequestMapping("/apiList")
@ResponseStatus(HttpStatus.OK)
public class ApiListRest {

	@Autowired
	ApiListService apiListService;

	@GetMapping("/test")
	public String extractFileTest() {
		return "test connection";
	}

	@PostMapping("/insert")
	@ResponseStatus(HttpStatus.CREATED)
	public String insertApiList(ApiList apiList) {
		String result = null;
		try {
			result = apiListService.insertApiList(apiList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@PutMapping("/insert")
	@ResponseStatus(HttpStatus.CREATED)
	public String updateApiList(ApiList apiList) {
		String result = null;
		try {
			result = apiListService.updateApiList(apiList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@DeleteMapping("removeall")
	@ResponseStatus(HttpStatus.OK)
	public String deleteAll() {
		String result = null;
		try {
			result = apiListService.removeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/findbyid")
	@ResponseStatus(HttpStatus.FOUND)
	public ApiList findApiListById(@RequestParam Long id) {
		ApiList result = null;
		try {
			result = apiListService.findApiListById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/findall")
	@ResponseStatus(HttpStatus.FOUND)
	public List<ApiList> findAllApiLists() {
		List<ApiList> result = null;
		try {
			result = apiListService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
