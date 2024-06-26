package com.zip_project.service.crud;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zip_project.db.model.ApiList;
import com.zip_project.db.repository.ApiListDaoRepository;

@Service
public class ApiListService {

	private final ApiListDaoRepository apiListDao;

	public ApiListService(ApiListDaoRepository apiListDao) {
		this.apiListDao = apiListDao;
	}

	public String insertApiList(ApiList apiList) {
		apiListDao.save(apiList);
		return "API LIST SAVED";
	}

	public String updateApiList(ApiList apiList) {
		apiListDao.save(apiList);
		return "API LIST UPDATED";
	}

	public String removeApiListById(Long id) {
		apiListDao.deleteById(id);
		return "API LIST REMOVED";
	}

	public String removeAll() {
		apiListDao.deleteAll();
		return "ALL API LIST REMOVED";
	}

	public ApiList findApiListById(Long id) {
		return apiListDao.findById(id).orElse(null);
	}

	public List<ApiList> findAll() {
		return (List<ApiList>) apiListDao.findAll();
	}

	public List<ApiList> getApiListsByModuleDefaultsId(Long idModuleDefaults) {
		return apiListDao
				.findByModuleDefaults_IdModuleDefaults(idModuleDefaults);
	}
}
