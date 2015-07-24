package com.android.iab.sqlitehelper;


/**
 * User_Details for holding user data
 *
 * @author Syed
 * @version 2015.
 * @since 1.0
 */
public class User_Details {
    
    //private variables
    int id;
    String name;
    String email;
    String company;
   
     
    // Empty constructor
    public User_Details(){
         
    }
    // constructor
    public User_Details(int id, String name, String email, String company){
        this.id = id;
        this.name = name;
        this.email = email;
        this.company =  company;
    }
     
    // constructor
    public User_Details(String name, String email){
        this.name = name;
        this.email = email;
    }
    
    // getting id  
	public int getId() {
		return id;
	}
	
	// setting id 
	public void setId(int id) {
		this.id = id;
	}
	
	// getting name
	public String getName() {
		return name;
	}
	
	// setting name
	public void setName(String name) {
		this.name = name;
	}
	
	// getting email
	public String getEmail() {
		return email;
	}
	
	// setting eamil
	public void setEmail(String email) {
		this.email = email;
	}
	
	// setting company
	public String getCompany() {
		return company;
	}
	
	// getting company
	public void setCompany(String company) {
		this.company = company;
	}
    
   
    
    
    
    
}