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

import com.zip_project.db.model.JsonLineList;
import com.zip_project.service.crud.JsonLineListService;

@RestController
@RequestMapping("/jsonlinelist")
@ResponseStatus(HttpStatus.OK)
public class JsonLineListRest {
	private final JsonLineListService jsonLineListService;

	public JsonLineListRest(JsonLineListService jsonLineListService) {
		this.jsonLineListService = jsonLineListService;
	}

	@GetMapping("/test")
	public String apiListTest() {
		return "test connection";
	}

	@PostMapping("/insert")
	@ResponseStatus(HttpStatus.CREATED)
	public String insertJsonLineList(JsonLineList jsonLineList) {
		String result = null;
		try {
			result = jsonLineListService.insertJsonLineList(jsonLineList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@PutMapping("/update")
	@ResponseStatus(HttpStatus.CREATED)
	public String updateJsonLineList(JsonLineList jsonLineList) {
		String result = null;
		try {
			result = jsonLineListService.updateJsonLineList(jsonLineList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@DeleteMapping("remove")
	@ResponseStatus(HttpStatus.OK)
	public String deleteJsonLineListById(@RequestParam Long id) {
		String result = null;
		try {
			result = jsonLineListService.removeJsonLineListById(id);
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
			result = jsonLineListService.removeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/findbyid")
	@ResponseStatus(HttpStatus.FOUND)
	public JsonLineList findJsonLineListById(@RequestParam Long id) {
		JsonLineList result = null;
		try {
			result = jsonLineListService.findJsonLineListById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/findall")
	@ResponseStatus(HttpStatus.FOUND)
	public List<JsonLineList> findJsonLineLists() {
		List<JsonLineList> result = null;
		try {
			result = jsonLineListService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
