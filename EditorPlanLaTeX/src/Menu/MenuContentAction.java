package Menu;


import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import Action.Action;
import Action.ICanvas;
import Action.Node;
import PlanPart.LinkCanvas;
import PlanPart.OrderConstrain;
import PlanPart.Oval;
import PlanPart.PlanContent;
import View.DomainView;
import dialog.action.EditLayoutAction;
import dialog.action.SetSizeActionDialog;
import dialog.option.InitializationVariableDialog;
/**
 * Menu which is used to manage an action: <br>  <br>
 * @author nadir
 * */
public class MenuContentAction implements MenuDetectListener {

	ICanvas canvas;


	public MenuContentAction(ICanvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void menuDetected(MenuDetectEvent e) {
		Menu m = new Menu(canvas);
		canvas.setMenu(m);
		addRemoveActionOption(m);
		addSetVariableOption(m);
		addShowCondOption(m);
		addShowNameOption(m);
		addSetLayoutOption(m);
		addSetSizeOption(m);
	}
	
	private boolean actionHasVariable(Action a) {
		if(canvas.getParent().getParent() instanceof PlanContent) {
			PlanContent planContent=(PlanContent)canvas.getParent().getParent();
			DomainView domain=planContent.getPlanview().getDomainView();
			String n[] = a.getName().split("\\(");
			String name2=n[0];
			for(Action action:domain.getTreeAction().getActionList()) {
				String name3[] = action.getName().split("\\(");
				if(name2.equals(name3[0])) {
					String nameAction = action.getName();
					if (nameAction.contains("(") || nameAction.contains(")")) {
						String name[] = action.getName().split("\\(");
						String variable[] = name[1].split("\\)");
						variable = variable[0].split(",");
						for (int i = 0; i < variable.length; i++) {
							if (variable[i].contains("?")) {
								return true;

							}
						}
					}
				}
			}
			return false;

		}
		
		return false;

	}
	
	private void addSetSizeOption(Menu m) {
		
		if (!(canvas.getParent().getParent() instanceof PlanContent)) {

		MenuItem setSize = new MenuItem(m, SWT.CASCADE);
		setSize.setText("Set Size");
		setSize.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				SetSizeActionDialog dialog=new SetSizeActionDialog(canvas.getShell(),
						SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER |SWT.RESIZE ,canvas);
				dialog.createContent();
				updateViewDomain();

			}
		});
		}
	}
	
	
	
	private void addSetLayoutOption(Menu m) {
		if (!(canvas.getParent().getParent() instanceof PlanContent)) {

			
			
			MenuItem editLayout = new MenuItem(m, SWT.ALL);
			editLayout.setText("Edit Layout");
			editLayout.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					EditLayoutAction dialog;
					dialog=new EditLayoutAction(m.getShell(), 
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER|SWT.RESIZE);
					dialog.setCanvas(canvas,canvas.getAction());
					dialog.createContent();
					updateViewDomain();
					updateViewPlan();

				}
			});
		}
	}
	
	private void addShowNameOption(Menu m) {

		if (!(canvas.getParent().getParent() instanceof PlanContent)) {

			MenuItem showN = new MenuItem(m, SWT.ALL);
			showN.setText("Show/Hide Name");
			showN.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					canvas.getAction().negateIsShownName();
					canvas.redraw();

				}
			});
		}
	}
	
	private void addShowCondOption(Menu m) {
		if (!(canvas.getParent().getParent() instanceof PlanContent)) {
			MenuItem showC = new MenuItem(m, SWT.ALL);
			showC.setText("Show/Hide Cond.");
			showC.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					canvas.getAction().negateIsShownCond();
					canvas.redraw();

				}
			});

			
		}
	}
	
	
	private void addSetVariableOption(Menu m) {
		if ((canvas.getParent().getParent() instanceof PlanContent)) {
			
			PlanContent planContent=(PlanContent) canvas.getParent().getParent();
			if(actionHasVariable(canvas.getAction())) {
				MenuItem setvariable = new MenuItem(m, SWT.ALL);
				setvariable.setText("Set Variables");
				setvariable.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						
						if (actionHasVariable(canvas.getAction())) {
							InitializationVariableDialog dialog = new InitializationVariableDialog(
									canvas.getShell(), 
									SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE);
							dialog.setAction(canvas.getAction());
							dialog.setPlan(planContent);
							dialog.createContent();
							dialog.pack();
						}
						canvas.redraw();
						updateViewPlan();
					}
				});
			}
		
		}
	}
	
	
	
	private void addRemoveActionOption(Menu m) {
		if (canvas.getParent().getParent() instanceof PlanContent) {
			MenuItem c = new MenuItem(m, SWT.ALL);
			c.setText("Remove Action");
			
			c.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					if (canvas instanceof Node) {
						if (canvas.getParent().getParent() instanceof PlanContent) {
							PlanContent plan = (PlanContent) canvas.getParent().getParent();
							plan.getActionInPlan().remove(canvas);
							canvas.getParent().setVisible(false);
						
							Action a = canvas.getAction();
							ArrayList<LinkCanvas> links = plan.getLink();
							ArrayList<LinkCanvas> linksToDelete = new ArrayList<>();
							for (LinkCanvas link : links) {
								if (link.getOval1().getNode() != null) {
									if (link.getOval1().getNode().getAction().getName().equals(a.getName())) {
										link.setOval1(null);
										link.setOval2(null);
										linksToDelete.add(link);
									}
								}
								if(link.getOval2()!= null) {
									if (link.getOval2().getNode() != null) {
										if (link.getOval2().getNode().getAction().getName().equals(a.getName())) {

											link.setOval1(null);
											link.setOval2(null);
											linksToDelete.add(link);
										}
									}
									
								}
							
								
								
							
							}
							links.removeAll(linksToDelete);

							for (Oval oval : canvas.getOvalList()) {
								plan.getOvalCounter().getListOval().remove(oval);
								oval.dispose();

							}
							canvas.setOvalList(new ArrayList<>());
							
							
							ArrayList<OrderConstrain> orderConstrains = plan.getOrds();
							ArrayList<OrderConstrain> orderConstrainsToDelete = new ArrayList<>();
							for (OrderConstrain orderConstrain : orderConstrains) {
								if (orderConstrain.getCond1().getAction().getName().equals(a.getName())
										|| orderConstrain.getCond2().getAction().getName().equals(a.getName())) {

									orderConstrainsToDelete.add(orderConstrain);
									orderConstrain.getParent().dispose();
								}
							}

							MessageBox messageBox = new MessageBox(canvas.getShell(), SWT.ICON_WARNING | SWT.OK);

							messageBox.setText("Message");
							messageBox.setMessage("Removed Action");
							messageBox.open();
							updateViewPlan();
							canvas.getParent().setVisible(false);
							return;
						}
					}

					MessageBox messageBox = new MessageBox(canvas.getShell(), SWT.ICON_WARNING | SWT.OK);

					messageBox.setText("Remove Action");
					messageBox.setMessage("Removed Action");
					updateViewPlan();
					messageBox.open();
					canvas.clearDisplay();
					
				}
			});
		}
		
	}
	
	
	private void updateViewPlan() {
		if(canvas.getParent().getParent() instanceof PlanContent) {
			PlanContent planContent=(PlanContent)canvas.getParent().getParent();
			planContent.getPlanview().getConsoleView().getConsoleViewPlan().updateView();
		}
	}
	
	
	private void updateViewDomain() {
		if(canvas.getParent().getParent() instanceof PlanContent) {
			PlanContent planContent=(PlanContent)canvas.getParent().getParent();
			planContent.getPlanview().getConsoleView().getConsoleViewDomain().updateView();
		}
	}
	
	
	public boolean isNumeric(String str)  
	{  
	  try  
	  {  
	     Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}
