package com.psl.idea;

public class Constants {
	public static final String API_KEY = "ideaportalapikey";
	
	private static final long ONE_SECOND = 60000L;
	
	public static final long TOKEN_VALIDAITY = 2*60*ONE_SECOND;
	
	public static final long CONFIRMATION_TOKEN_VALIDAITY = 10*ONE_SECOND;
	
	public static final String[] ALLOWED_ORIGINS = {"*", "http://localhost:4200",
			"http://localhost:8080",
			"http://localhost:8086",
			"https://hopeful-leavitt-8a8a98.netlify.app",
			"https://psl-ideaportal.herokuapp.com"};

	private Constants() {
	}
	
}
