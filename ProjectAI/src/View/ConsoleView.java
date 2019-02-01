package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import PlanPart.PlanContent;

public class ConsoleView extends Group{

	ConsoleViewDomain consoleViewDomain;
	ConsoleViewPlan consoleViewPlan;
	
	public ConsoleView(Composite parent, int style) {
		super(parent, style);
		setText("Console");
		// TODO Auto-generated constructor stub
	}
	
	
	public void setLayout() {
		setLayout(new GridLayout(2, true));

	}
	
	public void createContent(DomainView domainView,PlanView planView) {
		consoleViewDomain=new ConsoleViewDomain(this, SWT.ALL);
		consoleViewDomain.setLayout();
		consoleViewDomain.createContent(domainView);
		
		consoleViewPlan=new ConsoleViewPlan(this, SWT.ALL);
		consoleViewPlan.setLayout();
		consoleViewPlan.createContent(planView);
		
		
	}
	
	
	
	
	public ConsoleViewDomain getConsoleViewDomain() {
		return consoleViewDomain;
	}


	public ConsoleViewPlan getConsoleViewPlan() {
		return consoleViewPlan;
	}


	@Override
	protected void checkSubclass() {
		
	}
}
