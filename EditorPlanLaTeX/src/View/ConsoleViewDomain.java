package View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;

import Action.Action;
import LaTex.LatexGeneratorGlobalSize;
/**
 * View which represents the LaTeX code of the domain.
 * @author nadir
 * */
public class ConsoleViewDomain extends Group{

	
	File file;
	File dirLatex;
	Text textDomain;
	DomainView domainView;
	File directory;

	
	public ConsoleViewDomain(Composite parent, int style) {
		super(parent, style);
		setText("Domain");
		// TODO Auto-generated constructor stub
	}

	public void setLayout() {
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setLayout(new GridLayout(1, true));

	}
	
	public void createContent(DomainView domainView) {
		
		this.domainView=domainView;
		ToolBar toolBarDomain = new ToolBar(this, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
		textDomain = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		textDomain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		textDomain.pack();
		toolBarDomain.pack();

		
	}
	public void saveFile() {
		createDirector();
		for (File directory : dirLatex.listFiles()) {
			if (directory.isDirectory()) {
				file = new File(directory, "tikzlibrarydomain.code.tex");
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (file.isFile()) {
					updateView();
					WriteTextToFile(textDomain.getText());
				}
			}
		}

		
	}
	public void createDirector() {
		String filepath = System.getProperty("user.home");
		directory = new File(filepath + "/TDP");

		
		 dirLatex = new File(filepath + "/TDP" + "/dirLatex");
		
		 if (!directory.exists()) {
				boolean result = false;

				try {
					directory.mkdir();
					result = true;
				} catch (SecurityException se) {
					// handle it
				}
				if (result) {
				}
			}

		 
		 
		 
		if (!dirLatex.exists()) {
			boolean result = false;

			try {
				dirLatex.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
			}
		}
		
	}
	
	public void WriteTextToFile(String serObj) {

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.println(serObj);

		writer.close();
		
		
	}
	
	
	public void updateView() {
		textDomain.setText("");
		LatexGeneratorGlobalSize latexGeneratorGlobalSize=new LatexGeneratorGlobalSize();
		textDomain.insert(latexGeneratorGlobalSize.getLatex());
		if (domainView.getInitialStateCanvas() != null) {
			domainView.getInitialStateCanvas().getState().generateLatexCodeDomain();
			textDomain.insert(domainView.getInitialStateCanvas().getState().getLatexCodeDomain());
		}
		if (domainView.getGoalStateCanvas() != null) {
			domainView.getGoalStateCanvas().getState().generateLatexCodeDomain();
			textDomain.insert(domainView.getGoalStateCanvas().getState().getLatexCodeDomain());
		}

		ArrayList<Action> updateActionListDomain = domainView.getTreeAction().getActionList();
		for (int i = 0; i < updateActionListDomain.size(); i++) {
			updateActionListDomain.get(i).generateLatexCode();
			textDomain.insert(updateActionListDomain.get(i).getLatexCode());
		}
	}
	
	
	
	public Text getTextDomain() {
		return textDomain;
	}

	@Override
	protected void checkSubclass() {
		
	}
}
