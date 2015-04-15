package com.example.test2.util;

public class User {

	public final static String NAME= "name";
	public final static String DEPART = "depart";
	private String name;
	private String depart;
	private int id_DB=-1;
	
	public User() {
		
	}
	
	public User(String name, String depart) {
		this.name = name;
		this.depart = depart;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public int getId_DB() {
		return id_DB;
	}
	public void setId_DB(int id_DB) {
		this.id_DB = id_DB;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", depart=" + depart + ", id_DB=" + id_DB
				+ "]";
	}
			
	
}
