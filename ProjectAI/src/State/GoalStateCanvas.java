package State;


import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Composite;

import PlanPart.PlanContent;



public class GoalStateCanvas extends IStateCanvas {

	

	
	public GoalStateCanvas(Composite parent, int style, IState state) {
		super(parent, style, state);
		name="goal";
	}

	
	@Override
	public void draw() {
		super.draw();
		 
		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
			
				int avergWidth = (int) e.gc.getFontMetrics().getAverageCharacterWidth();
				lengthCond=getLenght(state.getConds())*avergWidth+10;
				int numCond = state.getConds().size();

				int startX = parent.getClientArea().width;
				int startY = 0;

				lenIn=numCond*30;
				if(isText) {
					int val=getTextPosition(avergWidth);

					e.gc.drawRectangle(startX-20, startY, startX, startY + lenIn);	  
					Transform t=new Transform(getDisplay());
					t.rotate(90);
					
					e.gc.setTransform(t);
					
					e.gc.drawString(text, val, -startX);
					
					
					t.rotate(-90);
					e.gc.setTransform(t);
					
					startX=parent.getClientArea().width-20;
				}else {
					e.gc.setLineWidth(6);
					e.gc.drawLine(startX-2, startY, startX-2, startY + lenIn);
					e.gc.setLineWidth(1);
				}
				
				
				int posY = startY + 20;
				for (int i = 0; i < numCond; i++) {
					String string = state.getConds().get(i);

					if(shownCond) {
						e.gc.drawLine(startX, posY, startX - lengthCond, posY);
						e.gc.drawString(string, startX+3 - lengthCond, posY - 20, false);
						if(parent.getParent() instanceof PlanContent) {
							addOval(state,string,1, posY-2);
						}
					}else {
						e.gc.drawLine(startX, posY, startX - standardLength, posY);
						if(parent.getParent() instanceof PlanContent) {
							addOval(state,string,1, posY-2);
						}
					}
					posY = posY + 30;

				}
			
				resizeParent();

			}
		});

		
	}

	 public  int getTextPosition(int avergWidth) {
   	  int i = 5;
   	  int stringLenght=text.length()*avergWidth+6;
   	  if(stringLenght>lenIn) {
   		  lenIn=stringLenght;
   		  return i;
   	  }else {
   		  i=(lenIn-stringLenght)/2;
   		  return i;
   	  }
   	  
   }
	
}
