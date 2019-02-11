package PlanPart;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import Action.Node;
import LaTex.LaTexGeneratorAction;
import LaTex.LaTexGeneratorNode;

public class OrderCondition extends Canvas{
 
	PlanContent canvasContainer;
	Node nod1=null;
	Node nod2=null;
	Composite c1;
	Composite c2;
	Composite parent;
	String latexCode;

	
	public OrderCondition(Composite parent) {
		super(parent, SWT.ALL);
		this.canvasContainer=(PlanContent)parent.getParent();
		this.parent=parent;

		//parent.pack();
	}
	
	public void addlistener(Label l1,Label l2) {

		for(int i=0;i<canvasContainer.getChildren().length;i++) {
			Composite comp=(Composite)canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addOrdCond(l1,l2,comp));
			
		}
	}
	
	public void removelistener(Label l1,Label l2,Button btn) {

		for(int i=0;i<canvasContainer.getChildren().length;i++) {
			Composite comp=(Composite)canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addOrdCond(l1,l2,comp));
			comp.getChildren()[0].removeListener(SWT.MouseDoubleClick, addOrdCond(l1,l2,comp));
		}
	}
	
	public Listener addOrdCond(Label l1,Label l2, Composite comp) {
		Listener l;
		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (l1.getText().contains("ordering")) {
					if (nod1 == null) {
						for (int i = 0; i < canvasContainer.getActionInPlan().size(); i++) {

							canvasContainer.getActionInPlan().get(i).getClientArea();

							if ((canvasContainer.getActionInPlan().get(i).getClientArea().contains(event.x, event.y))) {
								c1 = comp;

								nod1 = canvasContainer.getActionInPlan().get(i);
								l2.setText(nod1.getAction().getName() + "<" + ".....");
								l2.pack();

							}
						}
					} else if (nod2 == null) {
						for (int i = 0; i < canvasContainer.getActionInPlan().size(); i++) {

							canvasContainer.getActionInPlan().get(i).getClientArea();

							if ((canvasContainer.getActionInPlan().get(i).getClientArea().contains(event.x, event.y))) {
								if(canvasContainer.getActionInPlan().get(i)!=nod1) {
									c2 = comp;
									nod2 = canvasContainer.getActionInPlan().get(i);
									l2.setText(nod1.getAction().getName() + "<" + nod2.getAction().getName());
									l2.pack();
								}
							

							}
						}
					}
				}

			}
		};

		return l;
	}
	
	public void drawOrder() {

		Point p = new Point(nod1.getBounds().x + nod1.getBounds().width, nod1.getBounds().y - 20);
		Point p1 = c1.getParent().toControl(c1.toDisplay(p.x, p.y));

		p = new Point(nod2.getBounds().x, nod2.getBounds().y - 20);
		Point p2 = c2.getParent().toControl(c2.toDisplay(p.x, p.y));
		
		setLocation(p1.x,p1.y+30);
		setSize(p2.x-p1.x,30);
		Contrain c=new Contrain(this, SWT.ALL);
		c.draw();
		

	}

	public Node getCond1() {
		return nod1;
	}

	public Node getCond2() {
		return nod2;
	}
	
	public void generateLatexCode() {
		LaTexGeneratorNode generator=new LaTexGeneratorNode();
		latexCode=generator.getLatexOrderCodePlan(this);
	
		
	}

	public String getLatexCode() {
		return latexCode;
	}
	
}
