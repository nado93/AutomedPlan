package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * View which represents separately the LaTeX code of the domain and the plan.
 * @see ConsoleViewDomain
 * @see ConsoleViewPlan
 * @author nadir
 * */
public class ConsoleLaTeXView extends Composite{

	ConsoleViewDomain consoleViewDomain;
	ConsoleViewPlan consoleViewPlan;
	
	public ConsoleLaTeXView(Composite parent, int style) {
		super(parent, style);
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
