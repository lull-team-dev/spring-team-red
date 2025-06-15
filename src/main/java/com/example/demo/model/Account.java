package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;


@Component
@SessionScope
public class Account {

		
		private Integer id;	
		private String lastName; //苗字
		private String firstName; //名前
		
		public Account() {
		}
		
		public Account(Integer id, String lastName,String firstName) {
			this.id = id;
			this.lastName = lastName;
			this.firstName = firstName;
			
		}
		
		public Account(String lastName,String firstName) {
			this.lastName = lastName;
			this.firstName = firstName;
		}
		
		public String getLastName() {
			return lastName;
		}
		
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
		
		public String getFirstName() {
			return firstName;
		}
		
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		
		
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

}
