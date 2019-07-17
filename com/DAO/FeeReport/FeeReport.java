package com.DAO.FeeReport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class FeeReport {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	int roll = 1;
	public static void main(String[] args) throws IOException, Exception {

		FeeReport obj = new FeeReport();
		System.out.println("Enter The Choice");
		System.out.println("1:Admin Login");
		System.out.println("2:Accountant Login");
		int a = Integer.parseInt(br.readLine());
		System.out.print("Enter The Name:");
		String name = br.readLine();
		// System.out.println();
		System.out.print("Enter the password:");
		String pass = br.readLine();
		obj.checkUser(a,name,pass);

	}

	private void checkUser(int a,String name,String password) throws Exception, IOException {

		System.out.println("Enter The Choice");
		System.out.println("1:Admin Login");
		System.out.println("2:Accountant Login");
		boolean start=true;
		//int a = Integer.parseInt(br.readLine());
		while(start==true)
		switch (a)

		{
		case 1:
			System.out.println("Enter The Admin Credentials");
			//System.out.print("Enter The Name:");
			//String name = br.readLine();
			// System.out.println();
			//System.out.print("Enter the password:");
			//String password = br.readLine();
			if (name.equals("admin") && password.equals("admin123")) {
				System.out.println("Logged in Successfully");
				adminSection();
			}
			else
			{
				start=true;
				a=1;
				break;
			}
			break;
		case 2:
			System.out.println("Enter The Accountant Credentials");
			System.out.print("Name:");
			String name2 = br.readLine();
			System.out.print("Password:");
			String password2 = br.readLine();
			Connection conn1 = DBConnnect.dbConnect();
			String query = "select * from Accountant where name=? and password=?";
			PreparedStatement ps = conn1.prepareStatement(query);
			ps.setString(1, name2);
			ps.setString(2, password2);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("Logged In Successfully");
				accountantSection();

			} else {
				System.out.println("Invalid User Credentials");
				a=2;
				start=true;
				break;
				
			}
		case 3:System.out.println("Exit");
		System.exit(0);
		

		}

	}

	private void accountantSection() throws Exception, IOException {

		boolean end = true;
		boolean phnchk=true;
		Long phone=0l;
		String emailstu="";
		while (end == true) {
			System.out.println("Accountant Section");
			System.out.println("Enter The Choice");
			System.out.println("1:Add Student");
			System.out.println("2:View Student");
			System.out.println("3:Edit Student");
			System.out.println("4:Due Fee");
			System.out.println("5:Logout");
			int choice = Integer.parseInt(br.readLine());
			switch (choice) {
			case 1:
				boolean emailchk=true;
				boolean paidchk=true;
				int paidfees=0;
				System.out.println("Add Student");
				System.out.print("Name:");
				String namestu = br.readLine();
				while(emailchk==true)
				{
				System.out.print("Email:");
				emailstu = br.readLine();
				if(Pattern.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$", emailstu))
				{
					emailchk=false;
					break;
				}
				else
				{
					emailchk=true;
					System.out.println("Invalid Email ");
					System.out.println("Reenter The Email");
				}
				}
				
				System.out.print("Course:");
				String course = br.readLine();
				System.out.print("Fee:");
				int fee = Integer.parseInt(br.readLine());
				while(paidchk==true)
				{
				System.out.print("Paid:");
				paidfees = Integer.parseInt(br.readLine());
					if(paidfees>fee||paidfees<0)
					{
						paidchk=true;
						System.out.println("Invalid fees paid");
					}
					else
					{
						paidchk=false;
						break;
					}
				
				
				}
				int due = fee - paidfees;
				System.out.println("Due:" + due);
				System.out.print("Address:");
				String address = br.readLine();
				System.out.print("City:");
				String city = br.readLine();
				System.out.print("State:");
				String state = br.readLine();
				System.out.println("Country:");
				String country = br.readLine();
				while(phnchk==true)
				{
					System.out.println("Contact No:");
					phone = Long.parseLong(br.readLine());
					String nu=Long.toString(phone);
					if(Pattern.matches("^[2-9]\\d{2}\\d{3}\\d{4}$", nu))
					{
						phnchk=false;
						break;
					}
					else
					{
						phnchk=true;
						System.out.println("Invalid Phone Number");
						System.out.println("Reenter The Phone Number");
					}
					
				}
				;

				Connection conn = DBConnnect.dbConnect();
				String query = "insert into Student(rollno,name,email,course,fee,paid,due,address,city,state,country,contactno) values (?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setInt(1, roll);
				ps.setString(2, namestu);
				ps.setString(3, emailstu);
				ps.setString(4, course);
				ps.setInt(5, fee);
				ps.setInt(6, paidfees);
				ps.setInt(7, due);
				ps.setString(8, address);
				ps.setString(9, city);
				ps.setString(10, state);
				ps.setString(11, country);
				ps.setLong(12, phone);
				roll++;
				int i = ps.executeUpdate();
				if (i != 0) {
					System.out.println("Account Added Successfully");
					end = true;
					break;
				} else {
					System.out.println("Account Not Added");
					end=true;
					break;
				}
			case 2:
				System.out.println("View Student");
				Connection con = DBConnnect.dbConnect();
				String query1 = "select * from Student";
				boolean continu = true;
				PreparedStatement ps1 = con.prepareStatement(query1);
				ResultSet rs = ps1.executeQuery();
				System.out.println(
						"Roll No    Name       Email       Course        Fee        Paid        Due         Address          City       State        Country          ContactNo");
				while (rs.next()) {
					int roll1 = rs.getInt(1);
					String name12 = rs.getString(2);
					String email = rs.getString(3);
					String cours = rs.getString(4);
					int fee1 = rs.getInt(5);
					int paid = rs.getInt(6);
					int due1 = rs.getInt(7);
					String add = rs.getString(8);
					String city1 = rs.getString(9);
					String state1 = rs.getString(10);
					String country1 = rs.getString(11);
					Long phone1 = rs.getLong(12);
					System.out.println(roll1 + "    " + name12 + "    " + email + "    " + cours + "    " + fee1
							+ "    " + paid + "    " + due1 + "    " + add + "    " + city1 + "    " + state1 + "    "
							+ country1 + "    " + phone1);
				}
				end = true;
				break;
			case 3:
				String continu1 = "true";
				System.out.println("Edit Student");
				System.out.println("Enter The Roll Number to be Updated");
				int rollno1 = Integer.parseInt(br.readLine());
				Connection conn1 = DBConnnect.dbConnect();
				String query12 = "select * from Student where rollno=?";
				PreparedStatement p = conn1.prepareStatement(query12);
				p.setInt(1, rollno1);
				int feesini=0,paidfee=0;
				ResultSet rs1 = p.executeQuery();
				boolean chki=true;
				int fee1=0,num=0;
				while (rs1.next()) {
					int roll1 = rs1.getInt(1);
					String name12 = rs1.getString(2);
					String email = rs1.getString(3);
					String cours = rs1.getString(4);
					 fee1 = rs1.getInt(5);
					int paid = rs1.getInt(6);
					int due1 = rs1.getInt(7);
					String add = rs1.getString(8);
					String city1 = rs1.getString(9);
					String state1 = rs1.getString(10);
					String country1 = rs1.getString(11);
					Long phone1 = rs1.getLong(12);
					System.out.println(roll1 + "    " + name12 + "    " + email + "    " + cours + "    " + fee1
							+ "    " + paid + "    " + due1 + "    " + add + "    " + city1 + "    " + state1 + "    "
							+ country1 + "    " + phone1);
					feesini = rs1.getInt(5);
					paidfee = rs1.getInt(6);
				}
				while (continu1.equalsIgnoreCase("true")) {
					PreparedStatement pr=null;
					System.out.println("Enter the field to be updated");
					String check = br.readLine();
					if (check.equalsIgnoreCase("name")) {
						System.out.println("Enter The Value to Be Updated");
						String nm = br.readLine();
						String query2 = " update Student set name=? where rollno=?";
						pr = conn1.prepareStatement(query2);
						pr.setString(1, nm);
						pr.setInt(2, rollno1);
						int i2 = pr.executeUpdate();
						if (i2 != 0) {
							System.out.println("Record Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();

						} else {
							System.out.println("Record Not Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();
						}
					} else if (check.equalsIgnoreCase("email")) {
						System.out.println("Enter The Value to Be Updated");
						String nm1 = br.readLine();
						String query22 = "update Student set email=? where rollno=?";
						pr = conn1.prepareStatement(query22);
						pr.setString(1, nm1);
						pr.setInt(2, rollno1);
						int i3 = pr.executeUpdate();
						if (i3 != 0) {
							System.out.println("Record Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();

						} else {
							System.out.println("Record Not Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();
						}
					} else if (check.equalsIgnoreCase("course")) {
						System.out.println("Enter The Value to Be Updated");
						String nm3 = br.readLine();
						String query23 = "update Student set course=? where rollno=?";
						pr = conn1.prepareStatement(query23);
						pr.setString(1, nm3);
						pr.setInt(2, rollno1);
						int i23 = pr.executeUpdate();
						if (i23 != 0) {
							System.out.println("Record Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();

						} else {
							System.out.println("Record Not Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();
						}
					} else if (check.equalsIgnoreCase("fee")) {
						
						System.out.println("Enter The Value to Be Updated");
						 int nm4 = Integer.parseInt(br.readLine());
						
						String query24 = "update Student set fee=?,due=? where rollno=?";
						pr = conn1.prepareStatement(query24);
						int newdue = nm4 - paidfee;
						pr.setInt(1, nm4);
						pr.setInt(2, newdue);
						pr.setInt(3, rollno1);

						int i24 = pr.executeUpdate();
						if (i24 != 0) {
							System.out.println("Record Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();

						} else {
							System.out.println("Record Not Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();
						}

					} else if (check.equalsIgnoreCase("paid")) {
						while(chki==true)
						{
						System.out.println("Enter The Value to Be Updated");
						 num = Integer.parseInt(br.readLine());
						if(num>fee1||num<0)
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
						String query24 = "update Student set paid=?,due=? where rollno=?";
						pr = conn1.prepareStatement(query24);
						int newdue = feesini - num;
						pr.setInt(1, num);
						pr.setInt(2, newdue);
						pr.setInt(3, rollno1);
						int i24 = pr.executeUpdate();
						if (i24 != 0) {
							System.out.println("Record Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();

						} else {
							System.out.println("Record Not Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();
						}

					} else if (check.equalsIgnoreCase("Address")) {
						System.out.println("Enter The Value to Be Updated");
						String nm4 = br.readLine();
						String query24 = "update Student set address=? where rollno=?";
						pr = conn1.prepareStatement(query24);
						pr.setString(1, nm4);
						pr.setInt(2, rollno1);
						int i24 = pr.executeUpdate();
						if (i24 != 0) {
							System.out.println("Record Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();

						} else {
							System.out.println("Record Not Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();
						}

					} else if (check.equalsIgnoreCase("city")) {
						System.out.println("Enter The Value to Be Updated");
						String nm4 = br.readLine();
						String query24 = "update Student set city=? where rollno=?";
						pr = conn1.prepareStatement(query24);
						pr.setString(1, nm4);
						pr.setInt(2, rollno1);
						int i24 = pr.executeUpdate();
						if (i24 != 0) {
							System.out.println("Record Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();

						} else {
							System.out.println("Record Not Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();
						}

					} else if (check.equalsIgnoreCase("state")) {
						System.out.println("Enter The Value to Be Updated");
						String nm4 = br.readLine();
						String query24 = "update Student set state=? where rollno=?";
						pr = conn1.prepareStatement(query24);
						pr.setString(1, nm4);
						pr.setInt(2, rollno1);
						int i24 = pr.executeUpdate();
						if (i24 != 0) {
							System.out.println("Record Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();

						} else {
							System.out.println("Record Not Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();
						}

					} else if (check.equalsIgnoreCase("country")) {
						System.out.println("Enter The Value to Be Updated");
						String nm4 = br.readLine();
						String query24 = "update Student set country=? where rollno=?";
						pr = conn1.prepareStatement(query24);
						pr.setString(1, nm4);
						pr.setInt(2, rollno1);
						int i24 = pr.executeUpdate();
						if (i24 != 0) {
							System.out.println("Record Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();

						} else {
							System.out.println("Record Not Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();
						}

					} else
					// if(check.equalsIgnoreCase("contactno"))
					{
						System.out.println("Enter The Value to Be Updated");
						Long nm4 = Long.parseLong(br.readLine());
						String query24 = "update Student set fee=? where rollno=?";
						pr = conn1.prepareStatement(query24);
						pr.setLong(1, nm4);
						pr.setInt(2, rollno1);
						int i24 = pr.executeUpdate();
						if (i24 != 0) {
							System.out.println("Record Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();

						} else {
							System.out.println("Record Not Updated Successfully");
							System.out.println("Do You Want To Continue(true/false)");
							continu1 = br.readLine();
						}

					}
				}
				end=true;
				break;
			case 4:
				System.out.println("Due Fees");
				System.out.println("Enter The Roll Number to be Updated");
				int rollno12 = Integer.parseInt(br.readLine());
				Connection conn12 = DBConnnect.dbConnect();
				String query123 = " select due from Student where rollno=?";
				PreparedStatement pe = conn12.prepareStatement(query123);
				pe.setInt(1, rollno12);
				ResultSet rp = pe.executeQuery();
				while(rp.next())
					
				{
				System.out.println("Due Fees:" + rp.getInt(1));
				}
				break;
			case 5:System.out.println("Logging out");
					end=false;
					break;
			
			
			}
		}

	}

	private void adminSection() throws Exception, IOException {

		String end = "true";

		while (end == "true") {
			System.out.println("Admin Section");
			System.out.println("Enter the choice");
			System.out.println("1:Add Accountant");
			System.out.println("2:View Accountant");
			System.out.println("3:Logout");
			int check = Integer.parseInt(br.readLine());

			switch (check) {
			case 1:
				boolean emailchk=true;
				boolean phnchk=true;
				String email="";
				Long number=0l;
				PreparedStatement ps = null;
				System.out.println("Add Accountant");
				System.out.print("Enter The Name:");
				String name = br.readLine();
				System.out.print("Enter The Password:");
				String password = br.readLine();
				while(emailchk==true)
				{
					System.out.print("Enter The Email:");
					 email = br.readLine();
				if(Pattern.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$", email))
				{
					emailchk=false;
					break;
				}
				else
				{
					emailchk=true;
					System.out.println("Invalid Email ");
					System.out.println("Reenter The Email");
				}
				}
				while(phnchk==true)
				{
					System.out.print("Enter the Contact Details:");
					number = Long.parseLong(br.readLine());
					String nu=Long.toString(number);
					if(Pattern.matches("^[2-9]\\d{2}\\d{3}\\d{4}$", nu))
					{
						phnchk=false;
						break;
					}
					else
					{
						phnchk=true;
						System.out.println("Invalid Phone Number");
						System.out.println("Reenter The Phone Number");
					}
					
				}
			
				Connection con = DBConnnect.dbConnect();
				String query = "insert into Accountant(name,password,email,phone) values (?,?,?,?)";
				ps = con.prepareStatement(query);
				ps.setString(1, name);
				ps.setString(2, password);
				ps.setString(3, email);
				ps.setLong(4, number);
				int i = ps.executeUpdate();
				if (i != 0) {
					System.out.println("Accontant Added");
					end = "true";
					break;
				} else {
					System.out.println("Accountant not added");
					break;

				}
			case 2:
				System.out.println("View Accountant");
				PreparedStatement stat = null;
				Connection conn = DBConnnect.dbConnect();
				String d = "select * from Accountant";
				stat = conn.prepareStatement(d);
				ResultSet rs = stat.executeQuery();
				int u = 1;
				System.out.println("Id    Name      Password            Email           Contact No");
				while (rs.next())

				{
					String name1 = rs.getString("name");
					String pass = rs.getString("password");
					String email1 = rs.getString("email");
					Long num = rs.getLong("phone");
					System.out.println(u + "    " + name1 + "    " + pass + "    " + email1 + "    " + num);
					u++;
				}
				end = "true";

				break;

			case 3:
				System.out.println("Logging out");
				end = "false";
				break;

			}

		}
	}

}
