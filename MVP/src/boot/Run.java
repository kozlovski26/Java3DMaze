package boot;



import model.MyModel;
import presenter.Presenter;
import properties.Properties;
import properties.PropertiesLoader;
import view.MazeWindow;
import view.MyView;



public class Run {

	public static void main(String[] args) {
		
		Properties properties = PropertiesLoader.getInstance().getProperties();
		MazeWindow view = new MazeWindow(2000,2000);
		MyModel model = new MyModel(properties);
		Presenter presenter = new Presenter(model, view);
		
		view.addObserver(presenter);
		model.addObserver(presenter);
		
		view.run();	
		
	}
}