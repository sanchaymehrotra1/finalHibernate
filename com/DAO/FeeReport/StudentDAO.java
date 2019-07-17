package com.DAO.FeeReport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.bean.Accountant;
import com.bean.Student;

public class StudentDAO {
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	
	SessionFactory fact=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Accountant.class).buildSessionFactory();
	Session se=fact.getCurrentSession();
	public boolean addStudent(String name,String email,String course,int fee,int paid,int due,String address,String city,String state,String country,Long contactno)
	
	{
		
		se.beginTransaction();
		Student stu=new Student(name,email,course,fee,paid,due,address,city,state,country,contactno);
		se.save(stu);
		se.getTransaction().commit();
		
		
		
		return true;
		
	}
	
	public List<Student> viewStudent()
	{
		List<Student> lr=se.createQuery("from Student").getResultList();
		return lr;
		
	}
	public boolean editStudent(int id,String field) throws IOException
	{
		boolean chki=true;
	
		Student st=se.get(Student.class, id);
		System.out.println(st);
		int fees=st.getFee();
		int paids=st.getPaid();
		if(field=="name")
			
		{
			System.out.println("Enter The Value to Be Updated");
			String nm = br.readLine();
			st.setName(nm);
		}
		 else if (field.equalsIgnoreCase("email")) {
				System.out.println("Enter The Value to Be Updated");
				String nm1 = br.readLine();
				st.setEmail(nm1);
			}
		 else if (field.equalsIgnoreCase("course")) {
				System.out.println("Enter The Value to Be Updated");
				String nm3 = br.readLine();
				st.setCourse(nm3);
		
		 }
		 else if (field.equalsIgnoreCase("fee")) {
				
				System.out.println("Enter The Value to Be Updated");
				 int nm4 = Integer.parseInt(br.readLine());
				 st.setFee(nm4);
				 st.setDue(nm4-paids);
		 }
		 else if (field.equalsIgnoreCase("paid")) {
			 int num=0;
				while(chki==true)
				{
				System.out.println("Enter The Value to Be Updated");
				 num = Integer.parseInt(br.readLine());
				if(num>fees||num<0)
				{
					chki=true;
					System.out.println("Reenter The Paid Fees");
				
				}
				else
				{
				chki=false;
				break;
				}
				}
				st.setPaid(num);
				st.setDue(fees-num);
				
		 }
		 else if (field.equalsIgnoreCase("Address")) {
				System.out.println("Enter The Value to Be Updated");
				String nm4 = br.readLine();
				st.setAddress(nm4);
		 }
		 else if (field.equalsIgnoreCase("city")) {
				System.out.println("Enter The Value to Be Updated");
				String nm4 = br.readLine();
				st.setCity(nm4);
		 }
		 else if (field.equalsIgnoreCase("state")) {
				System.out.println("Enter The Value to Be Updated");
				String nm4 = br.readLine();
				st.setState(nm4);
		 }
		 else if (field.equalsIgnoreCase("country")) {
				System.out.println("Enter The Value to Be Updated");
				String nm4 = br.readLine();
				st.setCountry(nm4);
		 }
		 else
				 if(field.equalsIgnoreCase("contactno"))
				{
					System.out.println("Enter The Value to Be Updated");
					Long nm4 = Long.parseLong(br.readLine());
					st.setContactno(nm4);
				}
		se.getTransaction().commit();
					
		return true;
		
	}
	public int getDues(int roll)
	{
		Student st=se.get(Student.class, roll);
		return st.getDue();
		
	}

}
