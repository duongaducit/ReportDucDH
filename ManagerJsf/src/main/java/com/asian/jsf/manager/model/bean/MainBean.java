package com.asian.jsf.manager.model.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.sql.Date;
import java.util.List;

import javafx.stage.FileChooser;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

import com.asian.jsf.manager.entities.TaskJob;
import com.asian.jsf.manager.service.impl.ServiceDatabaseImpl;

@ManagedBean
@RequestScoped
public class MainBean implements Serializable {

	private String sortBy = "Non";

	private ArrayList<TaskJob> listTask;

	private String filterByStatus = "All";
	private String filterByPublic = "All";

	private String accountLogin;
	private String passwordLogin;
	private String keyword = null;

	/*
	 * properties add
	 */
	private String idTask;
	private String nameTask;
	private Date timeUpdate = new Date(new java.util.Date().getTime());
	private String statusTask;
	private boolean publicTask;
	/*
	 * properties import
	 */
	@ManagedProperty("#{serviceBean}")
	private ServiceDatabaseImpl service;

	/*
	 * get and set
	 */

	public String getFilterByStatus() {
		return filterByStatus;
	}

	public void setFilterByStatus(String filterByStatus) {
		this.filterByStatus = filterByStatus;
	}

	public String getFilterByPublic() {
		return filterByPublic;
	}

	public void setFilterByPublic(String filterByPublic) {
		this.filterByPublic = filterByPublic;
	}

	public void setService(ServiceDatabaseImpl service) {
		this.service = service;
	}

	public String getIdTask() {
		return idTask;
	}

	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}

	public String getNameTask() {
		return nameTask;
	}

	public void setNameTask(String nameTask) {
		this.nameTask = nameTask;
	}

	public Date getTimeUpdate() {
		return timeUpdate;
	}

	public void setTimeUpdate(Date timeUpdate) {
		this.timeUpdate = timeUpdate;
	}

	public String getStatusTask() {
		return statusTask;
	}

	public void setStatusTask(String statusTask) {
		this.statusTask = statusTask;
	}

	public boolean isPublicTask() {
		return publicTask;
	}

	public void setPublicTask(boolean publicTask) {
		this.publicTask = publicTask;
	}

	private ArrayList<String> listStatus;

	public ArrayList<String> getListStatus() {
		return listStatus;
	}

	public void setListStatus(ArrayList<String> listStatus) {
		this.listStatus = listStatus;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public ArrayList<TaskJob> getListTask() {
		return listTask;
	}

	public void setListTask(ArrayList<TaskJob> listTask) {
		this.listTask = listTask;
	}

	public String getAccountLogin() {
		return accountLogin;
	}

	public void setAccountLogin(String accountLogin) {
		this.accountLogin = accountLogin;
	}

	public String getPasswordLogin() {
		return passwordLogin;
	}

	public void setPasswordLogin(String passwordLogin) {
		this.passwordLogin = passwordLogin;
	}

	/*
	 * check Login
	 */

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void reset() {
		this.idTask = "";
		this.nameTask = "";
		this.statusTask = "Done!";
		this.publicTask = false;
	}

	public String doLogin() {
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = null;
		boolean loggedIn = accountLogin != null && accountLogin.equals("admin")
				&& passwordLogin != null && passwordLogin.equals("12345678");

		if (loggedIn) {
			this.setListTask(service.getListTaskJob());
			System.out.println("this is welcome");
			return "/Welcome.xhtml";
		} else {
			System.out.println("this is login");
			return "/Login.xhtml";
		}
	}

	/*
	 * delete Task chossed
	 */
	public String deleteListTask(TaskJob taskJob) {
		this.listTask = service.deleteTaskJob(taskJob);
		return "/Welcome.xhtml";
	}

	/*
	 * edit Task chossed
	 */
	public String editListTask(TaskJob taskJob) {
		this.idTask = taskJob.getIdTask();
		this.nameTask = taskJob.getNameTask();
		this.timeUpdate = timeUpdate;
		this.statusTask = taskJob.getStatusTask();
		this.publicTask = taskJob.isPublicTask();
		this.deleteListTask(taskJob);
		return "/Welcome.xhtml";
	}

	/*
	 * add taskjob
	 */
	public String addTask() {
		System.out.println(timeUpdate);
		TaskJob addTask = new TaskJob(idTask, nameTask, timeUpdate, statusTask,
				publicTask);
		this.listTask = service.addTaskJob(addTask);
		this.reset();
		return "Welcome.xhtml";
	}

	/*
	 * sort by key = SORT_BY
	 */
	public String sortTaskBy() {
		if (sortBy.equals("Name")) {
			service.sortByName(this.listTask);
		} else {
			if (sortBy.equals(("Id Task"))) {
				service.sortByID(this.listTask);
			} else {
				this.setListTask(service.getListTaskJob());
			}
		}
		return "Welcome.xhtml";
	}

	/*
	 * search by name
	 */
	public String searchTask() {
		this.listTask = service.getListTaskJob();
		if ("".equals(keyword) || keyword == null) {
			this.setListTask(service.getListTaskJob());
		} else {
			this.setListTask(service.getListTaskJob(keyword));
		}
		return "/Welcome.xhtml";
	}

	/*
	 * filter by status and public
	 */
	public String filter() {
		this.listTask = service
				.filter(this.filterByStatus, this.filterByPublic);
		return "/Welcome.xhtml";
	}

	/*
	 * import from file excel
	 */

	/*
	 * export to file excel
	 */
	public String exportF() throws IOException, RowsExceededException,
			WriteException {
		
		service.exportExcel(this.listTask);
		return "/Welcome.xhtml";
	}
}
