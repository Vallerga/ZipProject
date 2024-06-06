package com.zip_project.controller.crud;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zip_project.model.ApiModel;
import com.zip_project.service.crud.ApiModelService;

@RestController
@RequestMapping("/apimodel")
@ResponseStatus(HttpStatus.OK)
public class ApiModelRest {

	private final ApiModelService apiModelService;

	public ApiModelRest(ApiModelService apiModelService) {
		this.apiModelService = apiModelService;
	}
	@GetMapping("/test")
	public String extractFileTest() {
		return "test connection";
	}

	@PostMapping("/insert")
	@ResponseStatus(HttpStatus.CREATED)
	public String insertApiModel(ApiModel apiModel) {
		String result = null;
		try {
			result = apiModelService.insertApiModel(apiModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@PutMapping("/insert")
	@ResponseStatus(HttpStatus.CREATED)
	public String updateApiModel(ApiModel apiModel) {
		String result = null;
		try {
			result = apiModelService.updateApiModel(apiModel);
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
			result = apiModelService.removeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/findbyid")
	@ResponseStatus(HttpStatus.FOUND)
	public ApiModel findApiModelById(@RequestParam Long id) {
		ApiModel result = null;
		try {
			result = apiModelService.findApiModelById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/findall")
	@ResponseStatus(HttpStatus.FOUND)
	public List<ApiModel> findAllApiModels() {
		List<ApiModel> result = null;
		try {
			result = apiModelService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
