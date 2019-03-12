package Dialog;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import State.IState;
import State.InitialState;
import State.InitialStateCanvas;

public class CreateSoDialog extends IDialogNewState{
	

	Composite compCanvas;
	ArrayList<String> listPrec;
	InitialStateCanvas initialStateCanvas;
	IState initialState;
	
	public CreateSoDialog(Composite compCanvas) {
		super(compCanvas.getShell(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER);
		this.compCanvas=compCanvas;
		
		
	}

	@Override
	public void createContent() {
		super.createContent();
		this.getLabel().setText("Create a new initial state");
		
	}

	@Override
	public Listener getOkbtnListener() {
		Listener btn = new Listener() {

			@Override
			public void handleEvent(Event event) {
				listPrec=getCond();
				if (initialStateCanvas == null) {
					initialState = new InitialState(listPrec);
					initialStateCanvas = new InitialStateCanvas(compCanvas, SWT.ALL, initialState);
					initialStateCanvas.addDNDListener();
					initialStateCanvas.getState().generateLatexCodeDomain();
					initialStateCanvas.getState().getLatexCodeDomain();
				}
				if (listPrec != null) {
					initialState.updateConds(listPrec);
					if (listPrec.size() > 0) {
						initialStateCanvas.draw();
						//initialStateCanvas.pack();
						setVisible(false);
					}
				}

			}
		};

		return btn;
	}

	
}