package Presentation;

import Connection.ConnectionFactory;

public class MainClass {

	public static void main(String[] args) {
		
		View view = new View();
		Controller controller = new Controller(view);
	}

}
