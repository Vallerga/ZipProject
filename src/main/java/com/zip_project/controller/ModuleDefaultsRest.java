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

import com.zip_project.model.ModuleDefaults;
import com.zip_project.service.crud.ModuleDefaultService;

@RestController
@RequestMapping("/md")
@ResponseStatus(HttpStatus.OK)
public class ModuleDefaultsRest {

	@Autowired
	ModuleDefaultService mdService;
	
	@GetMapping("/test")
	public String extractFileTest() {
		return "test connection";
	}

	@PostMapping("/insert")
	@ResponseStatus(HttpStatus.CREATED)
	public String insertModuleDefault(ModuleDefaults md) {
		String result = null;
		try {
			result = mdService.insertModuleDefault(md);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@PutMapping("/insert")
	@ResponseStatus(HttpStatus.CREATED)
	public String updateModuleDefault(ModuleDefaults md) {
		String result = null;
		try {
			result = mdService.updateModuleDefault(md);
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
			result = mdService.removeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/findbyid")
	@ResponseStatus(HttpStatus.FOUND)
	public ModuleDefaults findModuleDefaultById(
			@RequestParam Long id) {
		ModuleDefaults result = null;
		try {
			result = mdService.findModuleDefaultById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/findall")
	@ResponseStatus(HttpStatus.FOUND)
	public List<ModuleDefaults> findAllModuleDefaults() {
		List<ModuleDefaults> result = null;
		try {
			result = mdService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
