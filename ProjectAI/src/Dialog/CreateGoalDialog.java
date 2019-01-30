package Dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import State.GoalState;
import State.GoalStateCanvas;
import State.IState;

public class CreateGoalDialog extends IDialogNewState{
	
//	ArrayList<String> listPrec=this.getCond();

	Composite compCanvas;
	ArrayList<String> listEff;
	Shell dialog=this.getDialog();
	Combo CombOption;
	GoalStateCanvas goalStateCanvas;
	IState goalState;
	
	public CreateGoalDialog(Composite compCanvas) {
		super(compCanvas.getShell(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER);
		this.compCanvas=compCanvas;
		listEff=this.getCond();
		
		
	}

	@Override
	public void createContent() {
		super.createContent();
		this.getLabel().setText("Create a new goal state");
		dialog.pack();
		
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
					goalStateCanvas.generateLatexCodeDomain();
					goalStateCanvas.getLatexCodeDomain();
				}
				if(listEff != null) {
					goalState.updateConds(listEff);
					if(listEff.size()>0) {
						goalStateCanvas.draw();
						goalStateCanvas.pack();
						dialog.setVisible(false);
					}
				}
			}
		};
		
		return btn;
	}


	
}