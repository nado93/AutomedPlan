package DNDAaction;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import Action.Action;
import Action.Node;
import PlanPart.PlanContent;
import View.PlanView;
import View.PrincipalView;
import View.TreeActioDomainView;
import dataTrasfer.MyType;
import dialog.option.InitializationVariableDialog;
/**
 * extended class of DropTargetAdapter,which is used to drop an action into plan view.
 * @see MyDragActionListener
 * @author nadir
 * */
public class MyDropActionListener extends DropTargetAdapter {
	
	private DropTarget target;
	@SuppressWarnings("unused")
	private Composite parentComposite;
	private TreeActioDomainView treeAction;
	private ArrayList<Action> actionList;
	private PlanContent graphContent;
	private Node node;

	/**
	 * @param parentComposite - the composite that holds all pictures
	 * @param target          - the drop target
	 */
	public MyDropActionListener(Composite parentComposite, DropTarget target, TreeActioDomainView treeAction) {
		this.parentComposite=parentComposite;
		this.target = target;
		this.treeAction = treeAction;
	}

	public void dragEnter(DropTargetEvent event) {
		if (event.detail == DND.DROP_DEFAULT) {
			event.detail = DND.DROP_COPY;
		}
	}

	public void dragOperationChanged(DropTargetEvent event) {
		if (event.detail == DND.DROP_DEFAULT) {
			event.detail = DND.DROP_COPY;
		}
	}

	@Override
	public void drop(DropTargetEvent event) {

		if (target.getControl() instanceof PlanContent) {

			graphContent = (PlanContent) target.getControl();

			if (event.data != null) {
				MyType[] myTypes = (MyType[]) event.data;
				if (myTypes != null) {
					actionByteToObject(event);
				}
			}
			PrincipalView view = treeAction.getActionView().getDomainView().getPrincipalView();
			view.getConsoleView().getConsoleViewPlan().updateView();
		}

	}

	private void actionByteToObject(DropTargetEvent event) {
		
		MyType[] myTypes = (MyType[]) event.data;
		actionList = treeAction.getActionList();
		int j = 0;
		while (j < actionList.size()) {
			Action action = null;
			if (myTypes[0].getName().equals(actionList.get(j).getName())) {
				if (myTypes[0].getPrec().equals(actionList.get(j).getPrec())
						&& myTypes[0].getEff().equals(actionList.get(j).getEffect())) {
					action = actionList.get(j);

					action = new Action(actionList.get(j).getName(), actionList.get(j).getPrec(),
							actionList.get(j).getEffect());
					action.copyAttribute(actionList.get(j));

				}
				
			}

			if (action != null) {
				if (actionHasVariable(action)) {
					InitializationVariableDialog dialog = new InitializationVariableDialog(
							graphContent.getShell(),
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE);
					dialog.setAction(action);
					dialog.setPlan(graphContent);
					dialog.createContent();
					dialog.pack();
				}
				if (graphContent.getParent() instanceof PlanView) {
					PlanView planView = (PlanView) graphContent.getParent();
					if (planView.isShowConditionSelecte()) {

						action.setIsShownCond(true);
					}
				}

				Composite comp = new Composite(graphContent, SWT.ALL);
				comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
				comp.setLayout(new FillLayout());
				comp.setLocation(comp.toControl(event.x, event.y));
				node = new Node(comp, SWT.NONE, action);
				node.draw();
				node.pack();
				comp.pack();
				graphContent.getActionInPlan().add(node);
				setNodeID();
				graphContent.addMoveListener(comp);
			}

			j++;
		}
	}
	
	
	private void setNodeID() {
		int t = 1;
		String ID = getNameAction(node.getAction().getName()) + "-" + t;
		for (int i = 0; i < graphContent.getActionInPlan().size(); i++) {
			if (graphContent.getActionInPlan().get(i).getID() != null) {
				if (graphContent.getActionInPlan().get(i).getID().equals(ID)) {
					t++;
					ID = getNameAction(node.getAction().getName()) + "-" + t;
					i = 0;
				}
			}

		}
		node.setID(ID);
	}

	private String getNameAction(String string) {
		String name[] = string.split("\\(");
		StringBuilder sb = new StringBuilder();
		sb.append(name[0]);
		return sb.toString();
	}

	private boolean actionHasVariable(Action a) {

		String nameAction = a.getName();

		if (nameAction.contains("(") || nameAction.contains(")")) {
			String name[] = a.getName().split("\\(");
			String variable[] = name[1].split("\\)");
			variable = variable[0].split(",");
			for (int i = 0; i < variable.length; i++) {
				if (variable[i].contains("?")) {
					return true;

				}
			}
		}
		return false;

	}
	
	
}
