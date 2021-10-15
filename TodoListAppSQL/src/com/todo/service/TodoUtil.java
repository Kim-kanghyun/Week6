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
		
		System.out.print("[�׸� �߰�]\n"
				+ "���� > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}

		System.out.println("ī�װ� > ");
		category=sc.next();
		sc.nextLine();
		System.out.print("���� > ");
		desc = sc.nextLine().trim();
		System.out.println("�������� > ");
		due_date=sc.next();
		
		TodoItem t= new TodoItem(title, desc, category,due_date);
		if(list.addItem(t)>0)
			System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("[�׸� ����]\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int index = sc.nextInt();
		if(l.deleteItem(index)>0)
			System.out.println("�����Ǿ����ϴ�.");
	}
	
	public static void deleteItemMul(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		String line=sc.nextLine();
		StringTokenizer st =new StringTokenizer(line, " ");
		while(st.hasMoreTokens()) {
			int index=Integer.parseInt(st.nextToken());
			if(l.deleteItem(index)>0)
				System.out.println("�����Ǿ����ϴ�.");
				}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[�׸� ����]\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int index= sc.nextInt();

		System.out.println("�� ���� > ");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}
		System.out.println("�� ī�װ� > ");
		String new_category = sc.next();
		sc.nextLine();
		System.out.println("�� ���� > ");
		String new_desc = sc.nextLine().trim();
		System.out.println("�� �������� > ");
		String new_due_date = sc.next().trim();
		
		TodoItem t= new TodoItem(new_title,new_desc,new_category,new_due_date);
		t.setId(index);
		if(l.updateItem(t)>0)
			System.out.println("�����Ǿ����ϴ�.");

	}

	public static void listAll(TodoList l) {
		System.out.println("[��ü ���, �� "+l.getCount()+"��]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void checkAll(TodoList l) {
		if(l.lateChecker()>0)
			System.out.println("Ȯ���Ͽ����ϴ�.");
	}
	
	
	public static void findCateList(TodoList l, String cate) {
		int count=0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}
	
	public static void listCateAll(TodoList l) {
		int count=0;
		for(String item : l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.printf("\n�� %d���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.\n",count);
	}
	
	public static void postponeList(TodoList l,int id) {
		if(l.postponeItem(id)>0)
			System.out.printf("%d���� ������ �Ϸ� �̷���ϴ�.\n",id);
	}
	
	
	
	
	public static void findList(TodoList l,String keyword) {
		int count=0;
		for(TodoItem myitem : l.getList(keyword)) {
			System.out.println(myitem.toString());
			count++;
		}
		System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}
	
	public static void findComp(TodoList l,int comp) {
		int count=0;
		for(TodoItem myitem : l.getList1(comp)) {
			System.out.println(myitem.toString());
			count++;
		}
		System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}
	
	public static void findLate(TodoList l,int late) {
		int count=0;
		for(TodoItem myitem : l.getList2(late)) {
			System.out.println(myitem.toString());
			count++;
		}
		System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}
	
	
	public static void completeItem(TodoList l,int num) {
		if(l.completeItem(num)>0)
			System.out.println("�Ϸ� üũ�Ͽ����ϴ�.");
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
			System.out.println(count+"���� �׸��� �о����ϴ�.");
		}
		catch(FileNotFoundException e){
			System.out.println(filename+"������ �����ϴ�.");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void listAll(TodoList l,String orderby,int ordering) {
		System.out.printf("[��ü ���, �� %d��]\n",l.getCount());
		for(TodoItem item: l.getOrderedList(orderby,ordering)) {
			System.out.println(item.toString());
		}
	}
	
}
