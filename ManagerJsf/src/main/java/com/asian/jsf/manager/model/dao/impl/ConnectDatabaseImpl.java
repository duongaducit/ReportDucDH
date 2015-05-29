package com.asian.jsf.manager.model.dao.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.convert.Converter;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.asian.jsf.manager.entities.TaskJob;
import com.asian.jsf.manager.model.dao.ConnectDatabase;

@ManagedBean(name = "daoBean")
public class ConnectDatabaseImpl implements ConnectDatabase, Serializable {

	private Connection con;
	private PreparedStatement pr;
	private ResultSet rs;

	
	public void initConnectDatabaseImpl(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/TaskJob";
			String user = "rootroot";
			String password = "123456789";
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.getCause();
		}

		catch (ClassNotFoundException eg) {
			eg.getCause();
		}
	}

	public ArrayList<TaskJob> getListTaskJob() {
		// TODO Auto-generated method stub
		ArrayList<TaskJob> listTaskJob = new ArrayList<TaskJob>();

		initConnectDatabaseImpl();
		try {
			pr = con.prepareStatement("select * from TaskJob");
			rs = pr.executeQuery();
			SimpleDateFormat fm = new SimpleDateFormat("dd/mm/yyyy hh:mm");
			while (rs.next()) {
				TaskJob taskJob = new TaskJob();
				taskJob.setIdTask(rs.getString(1));
				taskJob.setNameTask(rs.getString(2));
				taskJob.setTimeUpdate(rs.getDate(3));
				taskJob.setStatusTask(rs.getString(4));
				taskJob.setPublicTask(rs.getInt(5) == 1);
				listTaskJob.add(taskJob);
			}
		} catch (SQLException e) {
			e.getCause();
		} finally {
			try {
				pr.close();
				con.close();
			} catch (SQLException ee) {

			}
		}
		return listTaskJob;
	}

	public ArrayList<TaskJob> addTaskJob(TaskJob taskJob) {
		// TODO Auto-generated method stub
		initConnectDatabaseImpl();
		try {
			pr = con.prepareStatement("insert into TaskJob value(?,?,?,?,?)");
			pr.setString(1, taskJob.getIdTask());
			pr.setString(2, taskJob.getNameTask());
			pr.setString(4, taskJob.getStatusTask());
			pr.setString(3, taskJob.getTimeUpdate().toString());
			if (taskJob.isPublicTask()) {
				pr.setInt(5, 1);
			} else {
				pr.setInt(5, 0);
			}
			pr.executeUpdate();
		} catch (SQLException e) {
			e.getCause();
		} finally {
			try {
				pr.close();
				con.close();
			} catch (SQLException ee) {

			}
		}
		ArrayList<TaskJob> list = new ArrayList<TaskJob>();
		list = this.getListTaskJob();
		return list;
	}

	public ArrayList<TaskJob> deleteTaskJob(TaskJob taskJob) {
		// TODO Auto-generated method stub
		initConnectDatabaseImpl();
		try {
			pr = con.prepareStatement("delete from TaskJob where idTask = ?");
			pr.setString(1, taskJob.getIdTask());
			pr.executeUpdate();
		} catch (SQLException e) {
			e.getCause();
		} finally {
			try {
				pr.close();
				con.close();
			} catch (SQLException ee) {

			}
		}
		ArrayList<TaskJob> list = new ArrayList<TaskJob>();
		list = this.getListTaskJob();
		return list;
	}

//	public ArrayList<TaskJob> editTaskJob(TaskJob taskJob) {
//		// TODO Auto-generated method stub
//		initConnectDatabaseImpl();
//
//		try {
//			pr = con.prepareStatement("update TaskJob set nameTask = ?, timeUpdate = ?, statusTask = ?, publicTask = ? where idTask = ?");
//			pr.setString(1, taskJob.getNameTask());
//			pr.setString(2, taskJob.getTimeUpdate().toString());
//			pr.setString(3, taskJob.getStatusTask());
//			if (taskJob.isPublicTask()) {
//				pr.setInt(4, 1);
//			} else {
//				pr.setInt(4, 0);
//			}
//			pr.setString(5, taskJob.getIdTask());
//			pr.executeUpdate();
//		} catch (SQLException e) {
//			e.getCause();
//		} finally {
//			try {
//				pr.close();
//				con.close();
//			} catch (SQLException ee) {
//
//			}
//		}
//		ArrayList<TaskJob> list = new ArrayList<TaskJob>();
//		list = this.getListTaskJob();
//		return list;
//	}
//
	/*
	 * sort by name (non-Javadoc)
	 * 
	 * @see
	 * com.asian.jsf.manager.model.dao.ConnectDatabase#sortByName(java.util.
	 * ArrayList)
	 */

	public ArrayList<TaskJob> sortByName(ArrayList<TaskJob> listTaskJob) {
		// TODO Auto-generated method stub
		Collections.sort(listTaskJob, TaskJob.TASK_NAME_COMPARETO);
		return listTaskJob;
	}

	/*
	 * sort by id (non-Javadoc)
	 * 
	 * @see
	 * com.asian.jsf.manager.model.dao.ConnectDatabase#sortByID(java.util.ArrayList
	 * )
	 */

	public ArrayList<TaskJob> sortByID(ArrayList<TaskJob> listTaskJob) {
		// TODO Auto-generated method stub
		Collections.sort(listTaskJob, TaskJob.ID_TASK);
		return listTaskJob;
	}

	public ArrayList<String> initListBox() {
		// TODO Auto-generated method stub
		ArrayList<String> listBox = new ArrayList<String>();
		listBox.add("Done!");
		listBox.add("Doing...");
		listBox.add("Do not.");
		return listBox;
	}

	/*
	 * (non-Javadoc) search with keyword
	 * 
	 * @see
	 * com.asian.jsf.manager.model.dao.ConnectDatabase#getListTaskJob(java.lang
	 * .String, java.util.ArrayList)
	 */

	public ArrayList<TaskJob> getListTaskJob(String keyword) {
		ArrayList<TaskJob> listTaskJob = new ArrayList<TaskJob>();
		initConnectDatabaseImpl();
		try {
			pr = con.prepareStatement("select * from TaskJob WHERE nameTask like '%"
					+ keyword + "%'");
			rs = pr.executeQuery();
			SimpleDateFormat fm = new SimpleDateFormat("dd/mm/yyyy hh:mm");
			while (rs.next()) {
				TaskJob taskJob = new TaskJob();
				taskJob.setIdTask(rs.getString(1));
				taskJob.setNameTask(rs.getString(2));
				taskJob.setTimeUpdate(rs.getDate(3));
				taskJob.setStatusTask(rs.getString(4));
				taskJob.setPublicTask(rs.getInt(5) == 1);
				listTaskJob.add(taskJob);
			}
		} catch (SQLException e) {
			e.getCause();
		} finally {
			try {
				pr.close();
				con.close();
			} catch (SQLException ee) {

			}
		}

		return listTaskJob;
	}

	/*
	 * (non-Javadoc) filter by status and public
	 * 
	 * @see
	 * com.asian.jsf.manager.model.dao.ConnectDatabase#filter(java.lang.String,
	 * java.lang.String)
	 */

	public ArrayList<TaskJob> filter(String byStatus, String byPublic) {
		initConnectDatabaseImpl();
		if (byStatus.equals("All"))
			byStatus = "%";
		if (byPublic.equals("All")) {
			byPublic = "%";
		} else if (byPublic.equals("Yes")) {
			byPublic = "1";
		} else
			byPublic = "0";
		ArrayList<TaskJob> listTaskJob = new ArrayList<TaskJob>();
		try {
			pr = con.prepareStatement("select * from TaskJob WHERE statusTask like ? and publicTask like ?");
			pr.setString(1, byStatus);
			pr.setString(2, byPublic);

			rs = pr.executeQuery();
			SimpleDateFormat fm = new SimpleDateFormat("dd/mm/yyyy hh:mm");
			while (rs.next()) {
				TaskJob taskJob = new TaskJob();
				taskJob.setIdTask(rs.getString(1));
				taskJob.setNameTask(rs.getString(2));
				taskJob.setTimeUpdate(rs.getDate(3));
				taskJob.setStatusTask(rs.getString(4));
				taskJob.setPublicTask(rs.getInt(5) == 1);
				listTaskJob.add(taskJob);
			}
		} catch (SQLException e) {
			e.getCause();
		} finally {
			try {
				pr.close();
				con.close();
			} catch (SQLException ee) {

			}
		}
		return listTaskJob;
	}

	public void exportExcel(ArrayList<TaskJob> listTask) throws IOException, RowsExceededException, WriteException {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		if (listTask != null) {
			JFileChooser file = new JFileChooser();
			int values = file.showSaveDialog(null);
			if (values == JFileChooser.APPROVE_OPTION) {
				File selectedFile = file.getSelectedFile();

				WritableWorkbook workbook = Workbook
						.createWorkbook(selectedFile);
				WritableSheet sheet = workbook.createSheet("Report", 0);
				sheet.addCell(new Label(0, 0, "IdTask"));
				sheet.addCell(new Label(1, 0, "NameTask"));
				sheet.addCell(new Label(2, 0, "TimeUpdate"));
				sheet.addCell(new Label(3, 0, "StatusTask"));
				sheet.addCell(new Label(4, 0, "PublicTask"));
				for (int i = 0; i < listTask.size(); i++) {
					TaskJob task = listTask.get(i);
					sheet.addCell(new Label(0, i + 1, task.getIdTask()));
					sheet.addCell(new Label(1, i + 1, task.getNameTask()));
					sheet.addCell(new Label(2, i + 1, task.getTimeUpdate()
							.toString()));
					sheet.addCell(new Label(3, i + 1, task.getStatusTask()));
					if (task.isPublicTask()) {
						sheet.addCell(new Label(4, i + 1, "true"));
					} else {
						sheet.addCell(new Label(4, i + 1, "false"));
					}
				}
				workbook.write();
				workbook.close();
			}
		}
	}
}
