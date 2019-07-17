package com.DAO.FeeReport;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.bean.Accountant;

public class AccountantDAO {
	
	SessionFactory fact=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Accountant.class).buildSessionFactory();
	
	public boolean addAccountant(String name,String password,String email,Long contactno)
	{
		Session se=fact.getCurrentSession();
		Accountant ac=new Accountant();
		try {
			se.beginTransaction();
			Accountant ar=new Accountant(ac.getId(),name,password,email,contactno);
			se.save(ar);
			se.getTransaction().commit();
			return true;
		}
		finally {
			se.close();
		
		}
	}
		public List<Accountant> viewAccountant()
		{
			Session se=fact.getCurrentSession();
			List<Accountant> lr=se.createQuery("from Accountant").getResultList();
			return lr;
		}
		
		
		
	}


