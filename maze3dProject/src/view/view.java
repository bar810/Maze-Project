package view;
import presenter.Properties;


public interface view {

	public void start()throws Exception;
	public void Print (String str);
	public void displayMessage(String msg);
	public void setProperties(Properties p);
	public void getInformation(String info);
	public void getMaze(String name);
}
