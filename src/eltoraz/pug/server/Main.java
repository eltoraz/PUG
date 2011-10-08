package eltoraz.pug.server;

import eltoraz.pug.server.MySQLconnect;

public class Main {
	public static void main(String[] args) throws Exception {
		MySQLconnect pug_db = new MySQLconnect();
		pug_db.connectToDataBase();
		//pug_db.getAllGames();
		pug_db.close();
	}


}