package command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Action.GlobalValue;
import View.DomainView;

public class SaveDomainCommand  implements ICommand{

	private DomainView domainView;
	File dirLog;
	File directory;
	File fileLog;
	
	@Override
	public boolean canExecute(Object var1, Object var2) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDomainView(DomainView d) {
		this.domainView=d;
	}
	
	@Override
	public void execute(Object var1, Object var2) {
		
		String name = null;
		if(var1 instanceof String) {
			name=(String)var1;
		}
		
		
		if(var2 instanceof DomainView) {
			domainView=(DomainView)var2;
		}
		
		createFileLog(name);
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(prepareGlobalValueArray());
		data.add(domainView.getTreeAction().getActionList());
		if (domainView.getInitialStateCanvas() != null) {
			data.add(domainView.getInitialStateCanvas().getState());
		} else {
			data.add(null);
		}
		if (domainView.getGoalStateCanvas() != null) {
			data.add(domainView.getGoalStateCanvas().getState());
		} else {
			data.add(null);
		}
		
		
		WriteObjectToFile(data);
		
	}

	public ArrayList<Object> prepareGlobalValueArray(){
		ArrayList<Object> arrayList=new ArrayList<>();
		
		arrayList.add(GlobalValue.isHeightOfAction);
		arrayList.add(GlobalValue.heightOfAction);
		
		
		arrayList.add(GlobalValue.isWidthOfAction);
		arrayList.add(GlobalValue.widthOfAction);
		
	
		
		arrayList.add(GlobalValue.isLengthsOfEmptyTasks);
		arrayList.add(GlobalValue.lengthsOfEmptyTasks);
		
		arrayList.add(GlobalValue.isLengthsOfPrecs);
		arrayList.add(GlobalValue.lengthsOfPrecs);
		
		arrayList.add(GlobalValue.isLengthsOfEffs);
		arrayList.add(GlobalValue.lengthsOfEffs);
		
		arrayList.add(GlobalValue.isLengthsOfConds);
		arrayList.add(GlobalValue.lengthsOfConds);
		return arrayList;
	}



	public void createFileLog(String name) {
		String filepath = dirLog.getAbsolutePath();
		fileLog = new File(filepath, name);
		
		if (!fileLog.exists()) {
			try {
				fileLog.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			
		}

	}
	
	public void createDirectorLog() {
		String filepath = System.getProperty("user.home");
		directory = new File(filepath + "/TDP");
		dirLog = new File(filepath + "/TDP" + "/dirLog");

		// if the directory does n exist, create it
		if (!directory.exists()) {
			System.out.println("creating directory: " + directory.getAbsolutePath());
			boolean result = false;

			try {
				directory.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		if (!dirLog.exists()) {
			System.out.println("creating directory: " + dirLog.getAbsolutePath());
			boolean result = false;

			try {
				dirLog.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

	}
	
	public void WriteObjectToFile(Object serObj) {

		try {
			FileOutputStream fileOut = new FileOutputStream(fileLog.getAbsolutePath());
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(serObj);
			objectOut.close();
			System.out.println("The Object  was succesfully written to a file");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}