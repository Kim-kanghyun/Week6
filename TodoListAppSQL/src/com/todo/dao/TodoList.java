package com.todo.dao;

import java.io.*;
import java.util.*;
import java.sql.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	Connection conn;
	public TodoList() {
		this.conn=DbConnect.getConnection();
	}

	public int addItem(TodoItem t) {
		String sql="insert into list (title, memo, category, current_date, due_date)"
				+ " values (?,?,?,?,?);";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getCurrent_date());
			pstmt.setString(5,t.getDue_date());
			count=pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql="delete from list where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count=pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql="update list set title=?, memo=?, category=?, current_date=?, due_date=?"
				+ " where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getId());
			count= pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int postponeItem(int id) {
		ArrayList<TodoItem>list=new ArrayList<TodoItem>();
		Statement stmt;
		PreparedStatement pstmt;
		int count=0;
		String sql1="SELECT due_date,postpone FROM list"
					+ " where id = "+id+";";
		String sql2="update list set due_date=?, postpone=?"
				+ " where id = ?;";
			try {
				stmt=conn.createStatement();
				ResultSet rs=stmt.executeQuery(sql1);
				rs.next();
				String due_date=rs.getString("due_date");
				int postnum=rs.getInt("postpone");
				postnum++;
				int new_date=Integer.parseInt(due_date.substring(0,4))*10000
						+Integer.parseInt(due_date.substring(5,7))*100
						+Integer.parseInt(due_date.substring(8,10))+1;
				String n_date=Integer.toString(new_date/10000)+"/"+Integer.toString(new_date/100-((new_date/10000)*100))+"/"+Integer.toString(new_date%100);
				pstmt=conn.prepareStatement(sql2);
				pstmt.setString(1, n_date);
				pstmt.setInt(2, postnum);
				pstmt.setInt(3, id);
				count= pstmt.executeUpdate();
				pstmt.close();
				stmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return count;
		}
	
	
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem>list=new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt=conn.createStatement();
			String sql="SELECT * FROM list";
			ResultSet rs =stmt.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date =rs.getString("current_date");
				int comp=rs.getInt("is_completed");
				int late=rs.getInt("late");
				int postpone=rs.getInt("postpone");
				TodoItem t =new TodoItem(title,description,category,due_date);
				t.setId(id);
				t.setComp(comp);
				t.setCurrent_date(current_date);
				t.setLate(late);
				t.setPostpone(postpone);
				list.add(t);
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
		
		public int getCount() {
			Statement stmt;
			int count=0;
			try {
				stmt=conn.createStatement();
				String sql="SELECT count(id) FROM list;";
				ResultSet rs =stmt.executeQuery(sql);
				rs.next();
				count=rs.getInt("count(id)");
				stmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return count;
	}

	public int lateChecker() {
		ArrayList<TodoItem>list=new ArrayList<TodoItem>();
		Statement stmt;
		PreparedStatement pstmt;
		int count=0;
		try {
			stmt=conn.createStatement();
			String sql1="SELECT * FROM list;";
			String sql2="update list set late=?"
					+ " where id = ?;";
			ResultSet rs =stmt.executeQuery(sql1);
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date =rs.getString("current_date");
				TodoItem t =new TodoItem(title,description,category,due_date);
				int d_date=Integer.parseInt(due_date.substring(0,4))*10000
						+Integer.parseInt(due_date.substring(5,7))*100
						+Integer.parseInt(due_date.substring(8,10));
				int c_date=Integer.parseInt(current_date.substring(0,4))*10000
						+Integer.parseInt(current_date.substring(5,7))*100
						+Integer.parseInt(current_date.substring(8,10));
				if(c_date>d_date) {
					pstmt=conn.prepareStatement(sql2);
					pstmt.setInt(1, 1);
					pstmt.setInt(2, id);
					count= pstmt.executeUpdate();
					pstmt.close();
				}
				else {
					pstmt=conn.prepareStatement(sql2);
					pstmt.setInt(1, 0);
					pstmt.setInt(2, id);
					count= pstmt.executeUpdate();
					pstmt.close();
				}
				list.add(t);
			}
				
		} catch(SQLException e) {
				e.printStackTrace();
			}
			return count;
			
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby,int ordering){
		ArrayList<TodoItem> list=new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt=conn.createStatement();
			String sql="SELECT * FROM list ORDER BY "+orderby;
			if(ordering==0)
				sql+=" desc";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date =rs.getString("current_date");
				TodoItem t =new TodoItem(title,description,category,due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int completeItem(int num) {
		String sql="update list set is_completed=?"
				+ " where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, num);
			count= pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	public Boolean isDuplicate(String title) {
		Statement stmt;
		try {
			stmt=conn.createStatement();
			String sql="SELECT DISTINCT title FROM list";
				ResultSet rs=stmt.executeQuery(sql);
				while(rs.next()) {
					if(title.equals(rs.getString("title"))) {
						return true;
					}	
				}
				stmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		
		return false;
	}
	
	public void importData(String filename) {
		try {
			BufferedReader br=new BufferedReader(new FileReader(filename));
			String line;
			String sql="insert into list (title, memo, category, current_date, due_date)"+" values (?,?,?,?,?);";
			int records =0;
			while((line=br.readLine())!=null) {
				StringTokenizer st =new StringTokenizer(line, "##");
				String title=st.nextToken();
				String category = st.nextToken();
				String description=st.nextToken();
				String due_date=st.nextToken();
				String current_date=st.nextToken();
				PreparedStatement pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				int count =pstmt.executeUpdate();
				if(count>0)records++;
				pstmt.close();
			}
			System.out.println(records+" records read!!");
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list =new ArrayList<String>();
		Statement stmt;
		try {
			stmt=conn.createStatement();
			String sql="SELECT DISTINCT category FROM list";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				String category=rs.getString("category");
				list.add(category);
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list =new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword ="%"+keyword+"%";
		try {
			String sql= "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date =rs.getString("current_date");
				int comp=rs.getInt("is_completed");
				int late=rs.getInt("late");
				TodoItem t =new TodoItem(title,description,category,due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setComp(comp);
				t.setLate(late);
				list.add(t);
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list=new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql="SELECT * FROM list WHERE category = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs= pstmt.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date =rs.getString("current_date");
				int comp=rs.getInt("is_completed");
				int late=rs.getInt("late");
				TodoItem t =new TodoItem(title,description,category,due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setComp(comp);
				t.setLate(late);
				list.add(t);
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public ArrayList<TodoItem> getList1(int num) {
		ArrayList<TodoItem> list =new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql= "SELECT * FROM list WHERE is_completed= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date =rs.getString("current_date");
				int comp=rs.getInt("is_completed");
				int late=rs.getInt("late");
				TodoItem t =new TodoItem(title,description,category,due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setComp(comp);
				t.setLate(late);
				list.add(t);
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public ArrayList<TodoItem> getList2(int num) {
		ArrayList<TodoItem> list =new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql= "SELECT * FROM list WHERE late= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date =rs.getString("current_date");
				int comp=rs.getInt("is_completed");
				int late=rs.getInt("late");
				if(comp!=1&&late==1) {
					TodoItem t =new TodoItem(title,description,category,due_date);
					t.setId(id);
					t.setCurrent_date(current_date);
					t.setComp(comp);
					t.setLate(late);
					list.add(t);
				}
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
