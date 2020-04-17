package com.haagahelia.GymApp.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class EditRoleForm {

		private long id;
			
		@NotEmpty
		@Pattern(regexp = "^(USER|ADMIN)$")
		private String role = "USER";
		
		private String username = "";

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

}
