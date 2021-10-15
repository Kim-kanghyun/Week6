package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int id;
    private int comp;
    private int late;
    private int postpone;
    

	public int getPostpone() {
		return postpone;
	}

	public void setPostpone(int postpone) {
		this.postpone = postpone;
	}

	public int getLate() {
		return late;
	}

	public void setLate(int late) {
		this.late = late;
	}

	public int getComp() {
		return comp;
	}

	public void setComp(int comp) {
		this.comp = comp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	
	public TodoItem(String title, String desc, String category, String due_date) {
		super();
		this.title = title;
		this.desc = desc;
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
	    this.current_date=f.format(new Date());
		this.category = category;
		this.due_date = due_date;
	}


	
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    public boolean is_completed(int comp) {
    	if(comp==1)
    		return true;
    	else
    		return false;
    }
    
    public boolean is_late(int late) {
    	if(late==1)
    		return true;
    	else
    		return false;
    }
    
	@Override
	public String toString() {
		String str;
		if(is_completed(comp)) {
			str= id+". [" + category + "] "+title+"[V] - "+desc+" - "+due_date+" - "+ current_date;
		}
		else if(is_late(late)){
			str= id+". [" + category + "] "+title+"[지각] - "+desc+" - "+due_date+" - "+ current_date;
		}
		else {
			str= id+". [" + category + "] "+title+" - "+desc+" - "+due_date+" - "+ current_date;
		}
		if(postpone>0) {
			str+=(" "+postpone+"회 연기하였습니다.");
		}
		return str;
	}
	
    
}
