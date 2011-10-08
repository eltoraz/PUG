package eltoraz.pug.server;

import eltoraz.pug.server.MySQLconnect;

public class Main {
	public static void main(String[] args) throws Exception {
		MySQLconnect dao = new MySQLconnect();
		dao.readDataBase();
	}


}