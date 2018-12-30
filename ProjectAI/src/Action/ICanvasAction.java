package Action;

import java.text.DecimalFormat;
import java.util.ArrayList;


import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import DNDAaction.MyDragActionListener;
import DataTrasfer.MyTransfer;
import GraphPart.GraphContent;
import GraphPart.Oval;
import LaTex.LaTexGeneratorAction;

public abstract class ICanvasAction  extends Canvas{


	Action action;
	Composite parent;
	int max;


	String latexCode;
	final double PIXEL_MEASUREMNT= 0.026458;
	final double CM_MEASUREMNT= 37.7957517575025;


	
	public ICanvasAction(Composite parent, int style,Action a) {
		super(parent, style);
		this.parent=parent;
		this.action=a;
		
	}
	
	
	
	public abstract void draw();
	
	public void resizeParent() {
		if(action.isShownCond()) {
			int x1=action.getLengthPrec()+action.getLengthEff()+action.getWidthRect()+4;
			int y1=action.getHeightRect()+40;
			parent.setSize(x1,y1);
			
		}else {
			int x1=action.getStandardLengthPrec()+action.getStandardLengthEff()+action.getWidthRect()+4;
			int y1=action.getHeightRect()+40;
			parent.setSize(x1,y1);
		}
	}
	
	public void addOval(String name,String cond,int x, int y) {
		Oval oval=new Oval(this,name,cond);
		oval.setLocation(x, y);
		oval.drawOval();
		if(parent.getParent() instanceof GraphContent) {
			GraphContent graphContent=(GraphContent) parent.getParent();
			graphContent.getOvalCounter().add(oval);
		}
	}
	

	
	
	
	
	public void clearDisplay() {
		if (this != null) {
			this.dispose();
		}
	}

	public Action getAction() {
		return this.action;
	}
	






	

	

	
	
}

