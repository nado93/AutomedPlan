package dialog.state;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import View.PrincipalView;
import so_goalState.GoalState;
import so_goalState.GoalStateCanvas;
import so_goalState.IState;
/**
 * Dialog which allows to create  the goal state.
 * @author nadir
 * */
public class CreateGoalDialog extends IDialogNewState{
	
//	ArrayList<String> listPrec=this.getCond();

	Composite compCanvas;
	ArrayList<String> listEff;
	Combo CombOption;
	GoalStateCanvas goalStateCanvas;
	IState goalState;
	
	public CreateGoalDialog(Composite compCanvas) {
		super(compCanvas.getShell(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE);
		this.compCanvas=compCanvas;
		listEff=this.getCond();
		
		
	}

	@Override
	public void createContent() {
		super.createContent();
		label.setText("Create a new goal state");
		pack();
		
	}
	
	@Override
	public Listener getOkbtnListener() {
		Listener btn=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(goalStateCanvas == null) {
					goalState=new GoalState(listEff);
					
					goalStateCanvas=new GoalStateCanvas(compCanvas,SWT.ALL,goalState);
					goalStateCanvas.addDNDListener();
					goalStateCanvas.getState().generateLatexCodeDomain();
					goalStateCanvas.getState().getLatexCodeDomain();
				}
				if(listEff != null) {
					goalState.updateConds(listEff);
					goalStateCanvas.draw();
					goalStateCanvas.getContainerState().setLocation(30, 30);
					setVisible(false);
				
				}
				PrincipalView principalView=goalStateCanvas.getContainerState().getiStateView().getDomainView().getPrincipalView();
				principalView.getConsoleView().getConsoleViewDomain().updateView();
			}
		};
		
		return btn;
	}


	
}