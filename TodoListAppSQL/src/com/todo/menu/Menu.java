package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<ToDoList ���� ��ɾ� ����>");
        System.out.println("add - �׸� �߰�");
        System.out.println("del - �׸� ����");
        System.out.println("del_mul <Id>,,- �׸� ���� ����");
        System.out.println("edit - �׸� ����");
        System.out.println("ls - ��ü ���");
        System.out.println("ls_cate - ī�װ� ���");
        System.out.println("find_cate <�˻���>- ī�װ� �˻�");
        System.out.println("find <�˻���> - �׸� �˻�");
        System.out.println("ls_name - ����� ����");
        System.out.println("ls_name_desc - ���񿪼� ����");
        System.out.println("ls_date - ��¥�� ����");
        System.out.println("ls_date_desc - �ֽż� ����");
        System.out.println("comp <Id> - �Ϸ��Ű��");
        System.out.println("comp_mul <Id>,,- ������ �Ϸ��Ű��");
        System.out.println("late_check - ���������� �������� Ȯ��");
        System.out.println("ls_comp - �Ϸ�� �͸� ��Ÿ����");
        System.out.println("ls_late - ������ �͸� ��Ÿ����");
        System.out.println("exit - ����");
    }
    public static void prompt() {
    	System.out.print("\nCommand > ");
    }
}
