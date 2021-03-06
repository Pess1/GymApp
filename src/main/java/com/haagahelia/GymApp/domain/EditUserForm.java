package com.haagahelia.GymApp.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//Form for editing user details
public class EditUserForm {
		
		private long id;
		
		@NotEmpty
		@Size(min=4, max=20)
		private String username = "";
		
		@NotEmpty
		@Size(min = 4, max = 40)
		private String password = "";
		
		private String newPassword = "";
		
		@NotEmpty
		@Pattern(regexp = "^(USER|ADMIN)$")
		private String role = "USER";
		
		@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
		private String email = "";
		
		@Max(300)
		@Min(50)
		private int height;
		
		@Max(400)
		@Min(30)
		private double weight = 0.00;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}
		
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getNewPassword() {
			return newPassword;
		}

		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}

}
