package com.todo;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start(){
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name":
				System.out.println("제목순으로 정렬하였습니다.");
				TodoUtil.listAll(l,"title",1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬하였습니다.");
				TodoUtil.listAll(l,"title",0);
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬하였습니다.");
				TodoUtil.listAll(l,"due_date",1);
				break;
			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬하였습니다.");
				TodoUtil.listAll(l,"due_date",0);
				break;

			case "help":
				Menu.displaymenu();
				break;
			
			case "find":
				String keyword= sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;
				
			
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
			
			case "find_cate":
				String cate=sc.nextLine().trim();
				TodoUtil.findCateList(l,cate);
				break;
				
			case "exit":
				quit = true;
				break;
				
			case "comp":
				int num=sc.nextInt();
				TodoUtil.completeItem(l,num);
				break;
				
			case "ls_comp":
				TodoUtil.findComp(l,1);
				break;
			
			case "ls_late":
				TodoUtil.findLate(l,1);
				break;
			
			case "late_check":
				TodoUtil.checkAll(l);
				break;
				
			case "delete_mul":
				TodoUtil.deleteItemMul(l);
				break;
			
			case "postpone":
				int id1=sc.nextInt();
				TodoUtil.postponeList(l, id1);
				break;
				
			case "comp_mul":
				
				String line=sc.nextLine();
				StringTokenizer st =new StringTokenizer(line, " ");
				while(st.hasMoreTokens()) {
					int id=Integer.parseInt(st.nextToken());
						TodoUtil.completeItem(l,id);
						}
				break;
					
			default:
				System.out.println("정확한 명령어를 입력하세요. (도움말 - help)");
				break;
			}
			
			
		} while (!quit);
	}
}
