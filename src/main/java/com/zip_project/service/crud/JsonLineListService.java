package com.zip_project.service.crud;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zip_project.db.model.FileStatus;
import com.zip_project.db.model.JsonLineList;
import com.zip_project.db.model.ModuleDefaults;
import com.zip_project.db.repository.JsonLineListDaoRepository;
import com.zip_project.db.repository.ModuleDefaultDaoRepository;
import com.zip_project.service.exception.DatabaseOperationException;

@Service
public class JsonLineListService {

	private final JsonLineListDaoRepository jsonLineListDao;
	private final ModuleDefaultDaoRepository moduleDefaultDao;

	public JsonLineListService(JsonLineListDaoRepository jsonLineListDao,
			ModuleDefaultDaoRepository moduleDefaultDao) {
		this.jsonLineListDao = jsonLineListDao;
		this.moduleDefaultDao = moduleDefaultDao;
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

	public JsonLineList getJsonLineListByModuleDefaultsId(
			Long moduleDefaultsId) {
		// find ModuleDefaults by ID
		ModuleDefaults moduleDefaults = moduleDefaultDao
				.findById(moduleDefaultsId)
				.orElseThrow(() -> new DatabaseOperationException(
						"ModuleDefaults not found"));

		// get associated FileStatus
		FileStatus fileStatus = moduleDefaults.getFileStatus();
		if (fileStatus == null) {
			throw new DatabaseOperationException(
					"FileStatus not found for ModuleDefaults with ID: "
							+ moduleDefaultsId);
		}

		// get associated JsonLineList for FileStatus
		JsonLineList jsonLineList = fileStatus.getJsonLineList();
		if (jsonLineList == null) {
			throw new DatabaseOperationException(
					"JsonLineList not found for FileStatus with ID: "
							+ fileStatus.getIdFileStatus());
		}

		return jsonLineList;
	}
}
