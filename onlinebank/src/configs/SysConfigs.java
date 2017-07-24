package configs;

public class SysConfigs {

	/* --- Names --- */
	public static final String name_App = "onlinebanknew";

	/* --- URL --- */
	public static final String url_Host = "http://localhost:8080/";
	public static final String url_AppPath = "onlinebanknew/";
	public static final String url_Assets = url_Host + url_AppPath + "resources/";

	/* --- Database --- */
	public static final String db_Driver = "com.mysql.jdbc.Driver";
	public static final String db_ConnectionString = "jdbc:mysql://localhost:3306/bank2";
	public static final String db_Username = "root";
	public static final String db_Password = "";

}
