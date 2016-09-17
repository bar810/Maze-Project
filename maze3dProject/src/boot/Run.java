package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import model.MyModel;
import presenter.Presenter;
import view.MyView;


/*TODO:
save and load with zip part B
i save all tthe data in exit method but in the constrctor he cant load beacuse the list is empty
need to check if the solution save methos is ok
*/

public class Run {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		MyView view = new MyView(in, out);
		MyModel model = new MyModel(50);
		Presenter presenter = new Presenter(model, view,50);
		model.addObserver(presenter);
		view.addObserver(presenter);
		view.start();
	}
}