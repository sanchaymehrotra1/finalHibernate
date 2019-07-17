package com.DAO.FeeReport;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.bean.Accountant;
import com.bean.Admin;

public class Validation {
	
	public boolean validate(String username,String password)
	{
		Admin a=new Admin();
		SessionFactory fact=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Admin.class).buildSessionFactory();
		Session se=fact.getCurrentSession();
		try {
			se.beginTransaction();
			Admin ad=se.get(Admin.class,a.getUsername());
			if(ad.getUsername()=="admin"&&ad.getPassword()=="admin123")
			{
				return true;
			}
			else
			{
				return false;
			}
			
		}
		finally {
			se.close();
		}
	}
	public boolean AccountantValidation(String username,String password)
	{
		
		Accountant ac=new Accountant();
		SessionFactory fact=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Accountant.class).buildSessionFactory();
		Session se=fact.getCurrentSession();
		try {
			se.beginTransaction();
			ac=se.get(Accountant.class,ac.getName());
			if(username.equalsIgnoreCase(ac.getName())&&password.equalsIgnoreCase(ac.getPassword()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		finally {
			se.close();
		}
		
		
	}

}
