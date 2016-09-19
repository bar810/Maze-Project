package boot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
		
		
		//cli view
		MyView view = new MyView(in, out);
		MyModel model = new MyModel(50,properties);
		Presenter presenter = new Presenter(model, view,50,properties);
		model.addObserver(presenter);
		view.addObserver(presenter);
		view.start();
	}
}