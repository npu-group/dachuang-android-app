package com.itau.jingdong.global;

public class Global {
	/** url*/
	public static final String REQUEST_URL = "http://1.zlei1103.applinzi.com/loginAction.php";
	public static final String REGISTER_URL = "http://1.zlei1103.applinzi.com/registerAction.php";
	public static final String SHOPPING_CAR_URL = "http://120.24.97.225/dachuang/cart-master/cart.html";
	
	/** 常用消息*/
	public static final int LOGIN_SUCCESS 	= 10001;
	public static final int LOGIN_ERROR		= 10002;
	public static final int REGISTER_SUCCESS= 10003;
	public static final int REGISTER_ERROR	= 10004;
	
	/** 错误类型 */
	public static final int REQUEST_SUCCESS = 1000;
	public static final int UID_OR_PWD_ERROR = 1001;
}
