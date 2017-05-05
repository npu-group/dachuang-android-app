package com.itau.jingdong.global;

public class Global {
	/** url*/
	public static final String REQUEST_URL = "http://120.24.97.225/dachuang/action/loginAction.php";
	public static final String REGISTER_URL = "http://120.24.97.225/dachuang/action/registerAction.php";
	public static final String SHOPPING_CAR_URL = "http://120.24.97.225/dachuang/cart-master/cart.html";
	public static final String ADD_TO_CART = "http://120.24.97.225/dachuang/action/addToCart.php";
	public static final String LIST_ITEM = "http://120.24.97.225/dachuang/action/data.php";

	/** 常用消息*/
	public static final int LOGIN_SUCCESS 	= 10001;
	public static final int LOGIN_ERROR		= 10002;
	public static final int REGISTER_SUCCESS= 10003;
	public static final int REGISTER_ERROR	= 10004;

	// 详情页图片加载
	public static final int DETAIL_IMAGE	= 10005;


	/** 错误类型 */
	public static final int REQUEST_SUCCESS = 1000;
	public static final int UID_OR_PWD_ERROR = 1001;

	public static final String DETAIL_ID = "000";

	public static String UID = "111";



}
