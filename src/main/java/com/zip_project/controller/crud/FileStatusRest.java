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

import com.zip_project.model.FileStatus;
import com.zip_project.service.crud.FileStatusService;

@RestController
@RequestMapping("/status")
@ResponseStatus(HttpStatus.OK)
public class FileStatusRest {
	private final FileStatusService fileStatusService;

	public FileStatusRest(FileStatusService fileStatusService) {
		this.fileStatusService = fileStatusService;
	}

	@GetMapping("/test")
	public String extractStatusTest() {
		return "test connection";
	}

	@PostMapping("/insert")
	@ResponseStatus(HttpStatus.CREATED)
	public String insertExtractStatus(FileStatus extractStatus) {
		String result = null;
		try {
			result = fileStatusService.insertFileStatus(extractStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@PutMapping("/update")
	@ResponseStatus(HttpStatus.CREATED)
	public String updateExtractStatus(FileStatus extractStatus) {
		String result = null;
		try {
			result = fileStatusService.updateFileStatus(extractStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@DeleteMapping("remove")
	@ResponseStatus(HttpStatus.OK)
	public String deleteExtractStatusById(@RequestParam Long id) {
		String result = null;
		try {
			result = fileStatusService.removeFileStatusById(id);
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
			result = fileStatusService.removeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/findbyid")
	@ResponseStatus(HttpStatus.FOUND)
	public FileStatus findExtractStatusById(@RequestParam Long id) {
		FileStatus result = null;
		try {
			result = fileStatusService.findFileStatusById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@GetMapping("/findall")
	@ResponseStatus(HttpStatus.FOUND)
	public List<FileStatus> findAllExtractStatus() {
		List<FileStatus> result = null;
		try {
			result = fileStatusService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
