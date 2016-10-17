package boot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import gui.GUIview;
import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import presenter.PropertiesHandler;
import view.MyView;

/**
 * RUN class manager all the program you have to choose between GUI or CLI view
 * 
 * @author bar brownshtein
 *
 */
public class Run {
	public static void main(String[] args) throws Exception {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		MyModel model = null;
		MyView view = null;
		Properties properties = null;
		GUIview mgv = null;

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
		model = new MyModel(properties.getMaxNumOfThreads(), properties);

		switch (properties.getRuntimeEnv()) {
		// GUI view
		case 0:
			mgv = new GUIview(in, out, properties);
			Presenter presenter = new Presenter(model, mgv, 2, properties);

			mgv.addObserver(presenter);
			model.addObserver(presenter);
			mgv.start();
			break;
		// CLI view
		case 1:
			try {
				view = new MyView(in, out);
				Presenter presenter1 = new Presenter(model, view, 2, properties);
				view.addObserver(presenter1);
				model.addObserver(presenter1);
				view.start();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		}
	}
}