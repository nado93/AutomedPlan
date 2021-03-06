package dialog.option;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import Action.Node;
import PlanPart.PlanContent;
import dialog.IDialog;
import so_goalState.IStateCanvas;
/**
 * dialog allows to set the position of the different components of the plan
 * @author nadir
 * */
public class PostionActionDialog extends IDialog{
	
	ArrayList<Node> nodes;
	ArrayList<Button> buttonList;
	ArrayList<Node> nodeList;
	ArrayList<Node> selectedNode;
	ArrayList<IStateCanvas> stateCanvas;
	Composite compList;
	PlanContent contentPlan;

	Combo combo;
	Button btnX;
	Button btnY;
	

	public PostionActionDialog(Shell shell, int style) {
		super(shell, style);
		buttonList=new ArrayList<>();
		nodeList=new ArrayList<>();
		selectedNode=new ArrayList<>();
		stateCanvas=new ArrayList<>();
	}

	@Override
	public void createContent() {
		label.setText("List of actions in plan");

		
		mainComposite.setLayout(new GridLayout(1, false));

		GridData gridData;
		gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
		
		for(Node node:nodes) {
			Button btn =new Button(mainComposite, SWT.CHECK);
			btn.setText(node.getAction().getName()+" ["+node.getID()+"]");
			btn.addListener(SWT.Selection, getbtnSelectionListener(btn));
			//btn.setLayoutData(gridData);
			buttonList.add(btn);
			nodeList.add(node);
			//btn.pack();

			
		}
	
		mainComposite.pack();
		
		combo=new Combo(mainComposite, SWT.READ_ONLY);
		combo.setLayoutData(gridData);
		combo.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));

		
		Composite comp=new Composite(mainComposite, SWT.ALL);
		comp.setLayout(new GridLayout(2, false));
		
		btnX=new  Button(comp, SWT.CHECK);
		btnX.setText("X");
		
		btnY=new  Button(comp, SWT.CHECK);
		btnY.setText("Y");
		
		btnX.setSelection(true);
		
		btnX.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(btnX.getSelection()) {
					btnY.setSelection(false);
				}else {
					btnY.setSelection(true);
				}
				
			}
		});
		
		
		btnY.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(btnY.getSelection()) {
					btnX.setSelection(false);
				}else {
					btnX.setSelection(true);
				}
				
			}
		});
		
		

		
		
	}

	
	public Listener getbtnSelectionListener(Button btn) {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(btn.getSelection()) {
					combo.add(btn.getText());
					combo.setSize(mainComposite.getSize().x,combo.getSize().y);
					
				}else {
					combo.remove(btn.getText());
				}
			
			}
		};
		
		return l;
	}
	
	@Override
	public Listener getOkbtnListener() {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				for(int i=0;i<buttonList.size();i++) {
					if(buttonList.get(i).getSelection()) {
						selectedNode.add(nodeList.get(i));
					}
				}
				updateTable();
				Node n1=getNodeCombo();
				updateCordinate(n1);
				dispose();
			}
		};
		
		return l;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public void setCompList(Composite compList) {
		this.compList = compList;
	}

	/*
	 * get the reference node
	 */
	private Node getNodeCombo() {
		for(Node node:selectedNode) {
			String compator=node.getAction().getName()+" ["+node.getID()+"]";
			if(compator.equals(combo.getText())) {
				return node;
			}
		}
		

		return null;
	}
	private void updateCordinate(Node n1) {
		if(btnX.getSelection()) {
			for(Node node:selectedNode) {
				node.getParent().setLocation(n1.getParent().getLocation().x, node.getParent().getLocation().y);
				
			}
		}else {
			for(Node node:selectedNode) {
				node.getParent().setLocation(node.getParent().getLocation().x, n1.getParent().getLocation().y);
				
			}
		}
		//contentPlan.getActionInPlan().get(0).getParent().setLocation(0,0);
		
	}

	private void updateTable() {

		Label lb=new Label(compList, SWT.BORDER);
		lb.setText("List: "+printArrayList(selectedNode));
		
		Label comparator=new Label(compList, SWT.BORDER);
		
		String x;
		if(btnX.getSelection()) {
			x="X";
		}else {
			
			x="Y";
		}
		comparator.setText("Reference: "+x+" cord. of "+combo.getText());
		compList.pack();
		compList.getParent().pack();
		
	}
	
	
	private String printArrayList(ArrayList<Node> node) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < node.size(); i++) {
			sb.append(node.get(i).getAction().getName());
			if (i < node.size() - 1) {
				sb.append(",");
			}

		}

		return sb.toString();
	}

	public void setContentPlan(PlanContent contentPlan) {
		this.contentPlan = contentPlan;
	}
	
	
	
}
