package com.abcenterprise.ppmtool.validator;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.abcenterprise.ppmtool.domain.user.User;

@Component
public class UserValidator  {

HashMap<String,String> ValidationErrorMap = new HashMap<>();
	
	public HashMap<String, String> getValidationErrorMap() {
		return ValidationErrorMap;
	}

	public void setValidationErrorMap(HashMap<String, String> validationErrorMap) {
		ValidationErrorMap = validationErrorMap;
	}

	public void ValidationErrorMap(){
		
	}
	
	public void validate(Object object) {
		User user = (User)object;
		ValidationErrorMap.clear();
		if(user.getPassword().length() <6) {
			ValidationErrorMap.put("password", "Password must be at least 6 characters");
		}
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			ValidationErrorMap.put("confirmPassword", "Password must match");	
			}
	}

}
