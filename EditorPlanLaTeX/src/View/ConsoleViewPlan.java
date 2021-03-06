package View;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import Action.Node;
import LaTex.LaTexGeneratorPlan;
import LaTex.LaTexGeneratorStatePlan;
import PlanPart.PlanContent;
import PlanPart.LinkCanvas;
import PlanPart.OrderConstrain;
/**
 * View which represents separately the LaTeX code of the plan.
 * @author nadir
 * */
public class ConsoleViewPlan extends Group {
	
	File directory;
	PlanContent contentAction;
	PlanView planView;
	File dirPlan;
	File file;
	Text textPlan;
	

	public ConsoleViewPlan(Composite parent, int style) {
		super(parent, style);
		setText("Plan");
	}

	public void setLayout() {
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setLayout(new GridLayout(1, true));

	}
	
	public void createContent(PlanView planView) {
		
		this.planView=planView;
		ToolBar toolBarPlan = new ToolBar(this, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
		textPlan = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		textPlan.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		textPlan.pack();
		toolBarPlan.pack();
					
	}
	public void updateView() {
		textPlan.setText("");

		LaTexGeneratorPlan laTexGeneratorPlan=new LaTexGeneratorPlan();
		textPlan.insert(laTexGeneratorPlan.getLatexIntro());
		
		PlanContent contentAction = (PlanContent)planView.getSelection().getControl();
		
		LaTexGeneratorStatePlan generatorStatePlan=new LaTexGeneratorStatePlan();
		textPlan.insert(generatorStatePlan.getLatexPlanCode(contentAction));
		ArrayList<Node> updateNodeList = contentAction.getActionInPlan();
		for (int i = 0; i < updateNodeList.size(); i++) {
			updateNodeList.get(i).generateLatexCode(contentAction);
			textPlan.insert(updateNodeList.get(i).getLatexCode());
		}

		ArrayList<LinkCanvas> updateLinkList = contentAction.getLink();
		for (int i = 0; i < updateLinkList.size(); i++) {
			updateLinkList.get(i).generateLatexCode();
			textPlan.insert(updateLinkList.get(i).getLatexCode());
		}

		ArrayList<OrderConstrain> updateOrder = contentAction.getOrds();
		for (int i = 0; i < updateOrder.size(); i++) {
			updateOrder.get(i).generateLatexCode();
			textPlan.insert(updateOrder.get(i).getLatexCode());
		}
		textPlan.insert(laTexGeneratorPlan.getLatexEnd());
	}
	


	public Text getTextPlan() {
		return textPlan;
	}

	public File getDirPlan() {
		return dirPlan;
	}

	public File getFile() {
		return file;
	}

	@Override
	protected void checkSubclass() {
		
	}
}
