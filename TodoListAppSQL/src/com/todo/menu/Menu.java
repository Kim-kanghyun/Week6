package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<ToDoList 관리 명령어 사용법>");
        System.out.println("add - 항목 추가");
        System.out.println("del - 항목 삭제");
        System.out.println("del_mul <Id>,,- 항목 다중 삭제");
        System.out.println("edit - 항목 수정");
        System.out.println("ls - 전체 목록");
        System.out.println("ls_cate - 카테고리 목록");
        System.out.println("find_cate <검색어>- 카테고리 검색");
        System.out.println("find <검색어> - 항목 검색");
        System.out.println("ls_name - 제목순 정렬");
        System.out.println("ls_name_desc - 제목역순 정렬");
        System.out.println("ls_date - 날짜순 정렬");
        System.out.println("ls_date_desc - 최신순 정렬");
        System.out.println("comp <Id> - 완료시키기");
        System.out.println("comp_mul <Id>,,- 여러개 완료시키기");
        System.out.println("late_check - 마감기한이 지났는지 확인");
        System.out.println("ls_comp - 완료된 것만 나타내기");
        System.out.println("ls_late - 지각한 것만 나타내기");
        System.out.println("exit - 종료");
    }
    public static void prompt() {
    	System.out.print("\nCommand > ");
    }
}
