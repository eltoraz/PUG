package eltoraz.pug.server;

import eltoraz.pug.server.MySQLconnect;
import eltoraz.pug.*;
import java.util.Vector;

public class Main {
	public static void main(String[] args) throws Exception {
		MySQLconnect pug_db = new MySQLconnect();
		pug_db.connectToDataBase();
		Vector<Game> games = pug_db.getAllGames();
		pug_db.sendGames(games);
		pug_db.close();
	}


}