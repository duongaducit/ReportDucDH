package com.asian.jsf.manager.model.dao;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.asian.jsf.manager.entities.TaskJob;

public interface ConnectDatabase {

	/*
	 * get list task job
	 */
	public ArrayList<TaskJob> getListTaskJob() throws ParseException;
	/*
	 * get list with keyword
	 */
	public ArrayList<TaskJob> getListTaskJob(String keyword);
	/*
	 * init list combobox
	 */
	public ArrayList<String> initListBox();
	/*
	 * add a task job to list
	 */
	public ArrayList<TaskJob> addTaskJob(TaskJob taskJob);

	/*
	 * delete a task with idTaskJob from list
	 */
	public ArrayList<TaskJob> deleteTaskJob(TaskJob taskJob);

	/*
	 * edit a task from new task
	 */
//	public ArrayList<TaskJob> editTaskJob(TaskJob taskJob);

	/*
	 * sort list task by name
	 */
	public ArrayList<TaskJob> sortByName(ArrayList<TaskJob> listTaskJob);

	/*
	 * sort list task by time
	 */
	public ArrayList<TaskJob> sortByID(ArrayList<TaskJob> listTaskJob);
	/*
	 * filter
	 */
	public ArrayList<TaskJob> filter(String byStatus,String byPublic);
	/*
	 * export to excel
	 */
	public void exportExcel(ArrayList<TaskJob> listTask) throws IOException, RowsExceededException, WriteException;
}
