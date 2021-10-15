package com.todo.service;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;


import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n"
				+ "제목 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}

		System.out.println("카테고리 > ");
		category=sc.next();
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		System.out.println("마감일자 > ");
		due_date=sc.next();
		
		TodoItem t= new TodoItem(title, desc, category,due_date);
		if(list.addItem(t)>0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("[항목 삭제]\n"
				+ "삭제할 항목의 번호를 입력하시오 > ");
		int index = sc.nextInt();
		if(l.deleteItem(index)>0)
			System.out.println("삭제되었습니다.");
	}
	
	public static void deleteItemMul(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		String line=sc.nextLine();
		StringTokenizer st =new StringTokenizer(line, " ");
		while(st.hasMoreTokens()) {
			int index=Integer.parseInt(st.nextToken());
			if(l.deleteItem(index)>0)
				System.out.println("삭제되었습니다.");
				}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목 수정]\n"
				+ "수정할 항목의 번호를 입력하시오 > ");
		int index= sc.nextInt();

		System.out.println("새 제목 > ");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		System.out.println("새 카테고리 > ");
		String new_category = sc.next();
		sc.nextLine();
		System.out.println("새 내용 > ");
		String new_desc = sc.nextLine().trim();
		System.out.println("새 마감일자 > ");
		String new_due_date = sc.next().trim();
		
		TodoItem t= new TodoItem(new_title,new_desc,new_category,new_due_date);
		t.setId(index);
		if(l.updateItem(t)>0)
			System.out.println("수정되었습니다.");

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 "+l.getCount()+"개]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void checkAll(TodoList l) {
		if(l.lateChecker()>0)
			System.out.println("확인하였습니다.");
	}
	
	
	public static void findCateList(TodoList l, String cate) {
		int count=0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void listCateAll(TodoList l) {
		int count=0;
		for(String item : l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n",count);
	}
	
	public static void postponeList(TodoList l,int id) {
		if(l.postponeItem(id)>0)
			System.out.printf("%d번의 일정을 하루 미뤘습니다.\n",id);
	}
	
	
	
	
	public static void findList(TodoList l,String keyword) {
		int count=0;
		for(TodoItem myitem : l.getList(keyword)) {
			System.out.println(myitem.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void findComp(TodoList l,int comp) {
		int count=0;
		for(TodoItem myitem : l.getList1(comp)) {
			System.out.println(myitem.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void findLate(TodoList l,int late) {
		int count=0;
		for(TodoItem myitem : l.getList2(late)) {
			System.out.println(myitem.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	
	public static void completeItem(TodoList l,int num) {
		if(l.completeItem(num)>0)
			System.out.println("완료 체크하였습니다.");
	}
	
	public static void loadList(TodoList l,String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String str;
			int count=0;
			while ((str = reader.readLine()) != null) {
				StringTokenizer st=new StringTokenizer(str,"##");
				String title, desc,current_date,category,due_date;
				title = st.nextToken();
				category=st.nextToken();
				desc= st.nextToken();
				due_date=st.nextToken();
				current_date=st.nextToken();
				TodoItem t = new TodoItem(title, desc,category,due_date);
				t.setCurrent_date(current_date);
				l.addItem(t);
				count++;
				}
			reader.close();
			System.out.println(count+"개의 항목을 읽었습니다.");
		}
		catch(FileNotFoundException e){
			System.out.println(filename+"파일이 없습니다.");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void listAll(TodoList l,String orderby,int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n",l.getCount());
		for(TodoItem item: l.getOrderedList(orderby,ordering)) {
			System.out.println(item.toString());
		}
	}
	
}
