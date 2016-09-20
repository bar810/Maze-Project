package boot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import gui.mazeWindow;
import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import presenter.PropertiesHandler;
import view.MyView;

/*TODO:
			
*/
public class Run {
	public static void main(String[] args) throws Exception {
		
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		MyModel model = null;
		MyView view = null;
		Properties properties = null;
		
		try {
			properties = PropertiesHandler.getInstance();
		} catch (FileNotFoundException e2) {
			System.out.println("Could not find properties file, using default set");
			properties = new Properties();
			try {
				PropertiesHandler.write(properties, "properties.xml");
			} catch (Exception e) {
				System.out.println("Could not save default properties file, please check manually");
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}	
		model = new MyModel(properties.getMaxNumOfThreads(),properties);
		
		switch (properties.getRuntimeEnv()) {
		case 0://using for GUI view in the future
			mazeWindow win = new mazeWindow();
			win.start();
			break;
			
		case 1://CLI view
			try {
				view = new MyView(in, out);
				Presenter presenter = new Presenter(model, view,2,properties);
				view.addObserver(presenter);
				model.addObserver(presenter);
				view.start();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		}
	}
}