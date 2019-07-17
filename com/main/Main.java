package com.main;

import com.DAO.FeeReport.Validation;

public class Main {
	public static void main(String[] args)
	{
		Validation v=new Validation();
		if(v.validate("admin", "admin123")==true)
		{
			System.out.println("Connected");
		}
		else
		{
			System.out.println("Not Connected");
		}
		
	}

}
