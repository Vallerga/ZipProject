package com.zip_project.service.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zip_project.model.ModuleDefaults;
import com.zip_project.repository.ModuleDefaultDaoRepository;

@Service
public class ModuleDefaultService {
	@Autowired
	ModuleDefaultDaoRepository mdDao;

	public String insertModuleDefault(ModuleDefaults md) {
		mdDao.save(md);
		return "MODULE DEFAULT SAVED";
	}

	public String removeModuleDefaultById(Long id) {
		mdDao.deleteById(id);
		return "MODULE DEFAULT REMOVED";
	}

	public String removeAll() {
		mdDao.deleteAll();
		return "ALL MODULE DEFAULT REMOVED";
	}

	public String updateModuleDefault(ModuleDefaults md) {
		mdDao.save(md);
		return "MODULE DEFAULT UPDATED";
	}

	public ModuleDefaults findModuleDefaultById(Long id) {
		return mdDao.findById(id).orElse(null);
	}

	public List<ModuleDefaults> findAll() {
		return (List<ModuleDefaults>) mdDao.findAll();
	}
}
