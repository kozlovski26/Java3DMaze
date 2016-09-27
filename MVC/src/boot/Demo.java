package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import controller.MyController;
import model.Model;
import model.MyModel;
import view.MyView;
import view.View;
/**
 * Demo
 * the class with the main of the MVC
 * @author User
 *
 */
public class Demo {
/**
 * the main start the cli
 * @param args
 */
	public static void main(String[] args) {
	
		
		
		MyController controller = new MyController();
		Model model = new MyModel(controller);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		View view = new MyView(controller, in, out);
		controller.setModelAndView(model, view);
		view.start();

	
		
	}

}
