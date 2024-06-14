package com.zip_project.service.crud;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zip_project.db.model.JsonLineList;
import com.zip_project.db.repository.JsonLineListDaoRepository;

@Service
public class JsonLineListService {

	private final JsonLineListDaoRepository jsonLineListDao;

	public JsonLineListService(JsonLineListDaoRepository jsonLineListDao) {
		this.jsonLineListDao = jsonLineListDao;
	}

	public String insertJsonLineList(JsonLineList jsonLineList) {
		jsonLineListDao.save(jsonLineList);
		return "JSONLINE LIST SAVED";
	}

	public String updateJsonLineList(JsonLineList jsonLineList) {
		jsonLineListDao.save(jsonLineList);
		return "JSONLINE LIST UPDATED";
	}

	public String removeJsonLineListById(Long id) {
		jsonLineListDao.deleteById(id);
		return "JSONLINE LIST REMOVED";
	}

	public String removeAll() {
		jsonLineListDao.deleteAll();
		return "JSONLINE LIST REMOVED";
	}

	public JsonLineList findJsonLineListById(Long id) {
		return jsonLineListDao.findById(id).orElse(null);
	}

	public List<JsonLineList> findAll() {
		return (List<JsonLineList>) jsonLineListDao.findAll();
	}
}
