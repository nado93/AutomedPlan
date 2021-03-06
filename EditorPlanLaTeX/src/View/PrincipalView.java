package View;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import Menu.MenuPrincipalView;
/**
 * It is the principal view, which contains all other views.
 * @author nadir
 * */
public class PrincipalView {

	private Shell shell;
	private SashForm sashForm;
	private SashForm sashForm2;
	private DomainView domainView;
	private ConsoleLaTeXView consoleView;
	PlanView planView;
	PdfView pdfView;

	private File directory;

	public PrincipalView(Shell shell) {
		this.shell = shell;

	}

	public void draw() {
		
		createContent();
		createMenuWindow();
		createDirector();
	}

	public void createMenuWindow() {

		MenuPrincipalView menuBar=new MenuPrincipalView(shell, SWT.BAR);
		menuBar.fillMenu(domainView, planView);
		
	}

	public void createContent() {

		ScrolledComposite firstScroll = new ScrolledComposite(shell, SWT.V_SCROLL | SWT.H_SCROLL);
		firstScroll.setLayout(new GridLayout(1, false));
		firstScroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		sashForm = new SashForm(firstScroll, SWT.HORIZONTAL);

		firstScroll.setContent(sashForm);
		firstScroll.setExpandHorizontal(true);
		firstScroll.setExpandVertical(true);

		domainView = new DomainView(sashForm);
		domainView.createContent();
		domainView.setPrincipalView(this);

		sashForm2 = new SashForm(sashForm, SWT.VERTICAL);
		sashForm.setWeights(new int[] { 1, 3 });

		planView = new PlanView(sashForm2, SWT.PUSH);
		planView.setLayout();
		planView.createContent(domainView);
		
		CTabFolder folder=new CTabFolder(sashForm2, SWT.ALL);
		
		CTabItem itemConsole = new CTabItem(folder, SWT.NONE);
		consoleView = new ConsoleLaTeXView(folder, SWT.SCROLL_LINE);
		consoleView.setLayout();
		consoleView.createContent(domainView, planView);
		itemConsole.setText("LaTeX Code");
		itemConsole.setControl(consoleView);
		folder.setSelection(itemConsole);
		
		
		CTabItem itemGlobal = new CTabItem(folder, SWT.NONE);
		GlobalOptionView globalOptionView=new GlobalOptionView(folder, SWT.NONE);
		globalOptionView.setLayout();
		globalOptionView.createContent();
		itemGlobal.setText("Global Options");
		itemGlobal.setControl(globalOptionView);
		globalOptionView.setDomainView(domainView);
		globalOptionView.setPlanView(planView);
		
		
		domainView.setGlobalOptionView(globalOptionView);
		
		CTabItem pdfItem = new CTabItem(folder, SWT.NONE);
		pdfItem.setText("PDF View");
		pdfView=new PdfView(folder, SWT.ALL);
		pdfItem.setControl(pdfView);
		
		//TODO allign the position of action
		
//		CTabItem positionItem=new CTabItem(folder, SWT.NONE);
//		positionItem.setText("Position View");
//		PositionInPlanView positionInPlanView=new PositionInPlanView(folder, SWT.ALL);
//		positionInPlanView.setContentPlan(planView.getPlan());
//		positionInPlanView.setLayout();
//		positionInPlanView.createContent();
//		positionItem.setControl(positionInPlanView);
		
		
			
		folder.setSelection(itemConsole);
		planView.setPdfPreView(consoleView);
		planView.setPdfView(pdfView);

		shell.setMaximized(false);
	}


	public void createDirector() {
		String filepath = System.getProperty("user.home");
		directory = new File(filepath + "/TDP");
		File dirDomain = new File(filepath + "/TDP" + "/dirDomain");
		File dirLatex = new File(filepath + "/TDP" + "/dirLatex");
		File dirLog = new File(filepath + "/TDP" + "/dirLog");


		// if the directory does not exist, create it
		if (!directory.exists()) {
			boolean result = false;

			try { 
				directory.mkdir();
				result = true;
			} catch (SecurityException se) {
				se.printStackTrace();			}
			if (result) {
			}
		}

		if (!dirDomain.exists()) {
			boolean result = false;

			try {
				dirDomain.mkdir();
				result = true;
			} catch (SecurityException se) {
				se.printStackTrace();
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
				se.printStackTrace();			}
			if (result) {
			}
		}

		if (!dirLog.exists()) {
			boolean result = false;

			try {
				dirLog.mkdir();
				result = true;
			} catch (SecurityException se) {
				se.printStackTrace();			}
			if (result) {
			}
		}

		
	}

	public PlanView getPlanView() {
		return planView;
	}

	public ConsoleLaTeXView getConsoleView() {
		return consoleView;
	}






}
