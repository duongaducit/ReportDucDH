
package com.asian.jsf.manager.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.asian.jsf.manager.entities.TaskJob;
import com.asian.jsf.manager.model.dao.impl.*;
import com.asian.jsf.manager.service.ServiceDatabse;

@ManagedBean(name = "serviceBean")
public class ServiceDatabaseImpl implements ServiceDatabse, Serializable {
	 
	@ManagedProperty("#{daoBeean}")
	private ConnectDatabaseImpl daoService;

	public ArrayList<TaskJob> getListTaskJob(){
		// TODO Auto-generated method stub
		return daoService.getListTaskJob();
	}

	public ArrayList<TaskJob> addTaskJob(TaskJob taskJob) {
		// TODO Auto-generated method stub
		return daoService.addTaskJob(taskJob);
	}

	public ArrayList<TaskJob> deleteTaskJob(TaskJob taskJob) {
		// TODO Auto-generated method stub
		return daoService.deleteTaskJob(taskJob);
	}

//	public ArrayList<TaskJob> editTaskJob(TaskJob taskJob) {
//		// TODO Auto-generated method stub
//		return daoService.editTaskJob(taskJob);
//	}

	public ArrayList<TaskJob> sortByName(ArrayList<TaskJob> listTaskJob) {
		// TODO Auto-generated method stub
		return daoService.sortByName(listTaskJob);
	}

	public ArrayList<TaskJob> sortByID(ArrayList<TaskJob> listTaskJob) {
		// TODO Auto-generated method stub
		return daoService.sortByID(listTaskJob);
	}

	public ArrayList<String> initListBox() {
		// TODO Auto-generated method stub
		return daoService.initListBox();
	}

	public ArrayList<TaskJob> getListTaskJob(String keyword) {
		// TODO Auto-generated method stub
		return daoService.getListTaskJob(keyword);
	}

	public ConnectDatabaseImpl getDaoService() {
		return daoService;
	}

	public void setDaoService(ConnectDatabaseImpl daoService) {
		this.daoService = daoService;
	}

	public ArrayList<TaskJob> filter(String byStatus, String byPublic) {
		// TODO Auto-generated method stub
		return daoService.filter(byStatus, byPublic);
	}

	public void exportExcel(ArrayList<TaskJob> listTask) throws RowsExceededException, WriteException, IOException {
		// TODO Auto-generated method stub
		daoService.exportExcel(listTask);
	}

}
