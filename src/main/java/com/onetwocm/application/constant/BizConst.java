package com.onetwocm.application.constant;

public class BizConst {

	/**
	 * System constant
	 * <br>security
	 * <br>config
	 * <br>url
	 * <br>etc..
	 *
	 * @author seochan
	 */
	
	public static class Security {
		
		public static final String loginUrl = "/login";
		public static final String logoutUrl = "/logout";
		public static final String adminUrl = "/admin";
		public static final String mypageUrl = "/mypage";
		
		public static final String[] ANT_PATTERNS_FOR_PERMIT_TO_MANAGER = {
	            "/admin/**"
	    };
		
		public static final String[] ANT_PATTERNS_FOR_WEB_STATIC_PERMIT_ALL = {
				"/static/**"
		};

		public static final String[] ANT_PATTERNS_FOR_PERMIT_TO_USER = {
		        "/mypage/**"
		};
		
		public static final String[] ANT_PATTERNS_FOR_API_PERMIT_TO_USER = {
		        "/api/**"
		};
	}
 
//	public static void main(String[] args) {
//		System.out.println(MemberRole.ADMIN);
//	}
}
