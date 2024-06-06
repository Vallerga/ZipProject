package com.zip_project.service.crud;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zip_project.model.ApiModel;
import com.zip_project.repository.ApiModelDaoRepository;

@Service
public class ApiModelService {

	private final ApiModelDaoRepository apiModelDao;

	public ApiModelService(ApiModelDaoRepository apiModelDao) {
		this.apiModelDao = apiModelDao;
	}

	public String insertApiModel(ApiModel apiModel) {
		apiModelDao.save(apiModel);
		return "API MODEL SAVED";
	}

	public String removeApiModelById(Long id) {
		apiModelDao.deleteById(id);
		return "API MODEL REMOVED";
	}

	public String removeAll() {
		apiModelDao.deleteAll();
		return "ALL API MODEL REMOVED";
	}

	public String updateApiModel(ApiModel apiModel) {
		apiModelDao.save(apiModel);
		return "API MODEL UPDATED";
	}

	public ApiModel findApiModelById(Long id) {
		return apiModelDao.findById(id).orElse(null);
	}

	public List<ApiModel> findAll() {
		return (List<ApiModel>) apiModelDao.findAll();
	}
}
