package Action;


import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import DNDAaction.MyDragActionListener;
import Menu.MenuContentAction;
import dataTrasfer.MyTransfer;
/**
 * Represents the graphic part of an action, which is created during the domain definition phase.
 * @author nadir
 *
 */
public class ActionDomainCanvas  extends ICanvas{

	int style;
	PaintListener p;
    private int initialFontSize = -1;
    private  Font  font;
	public static float scale = 1;


	
	public ActionDomainCanvas(Composite parent, int style, Action a) {
		super(parent, style, a);
		this.action = a;
		this.style=style;
	}
	
	public void draw() {
		this.addPaintListener(createPaintListener());
		this.redraw();
		this.addMenuDetectListener(new MenuContentAction(this));
		resizeParent();
		this.addMouseWheelListener(getMouseListener());
		
	}
	
	
	public void addDNDListener() {
		DragSource source =new DragSource(this, DND.DROP_NONE);
	    source.setTransfer(new Transfer[] { MyTransfer.getInstance() });
	    source.addDragListener(new MyDragActionListener(source));
	}
	
    private  int getTextPosition(int avergWidth) {
  	  int i = 5;
  	  int stringLenght=action.getName().length()*avergWidth+6;
  	  if(stringLenght>action.getWidthRect()) {
     		  action.setWidthRect(stringLenght);
  		  return i;
  	  }else {
  		  i=(int) ((action.getWidthRect()-stringLenght)/2);
  		  return i;
  	  }
  	  
    }
     @Override
	public void resizeParent() {
		if (action.isShownCond()) {
			double x1 = action.getLengthPrec()*scale + action.getLengthEff()*scale + (action.getWidthRect()*scale)+2;
			if(action.getPrec().size()==0 && action.getEffect().size()==0) {
				x1=x1+5;
			}
			double y1 = action.getHeightRect()*scale + 40;
			parent.setSize((int)x1,(int) y1);

		} else {
			double x1 ;
			x1=action.getStandardLengthPrec()*scale + action.getStandardLengthEff()*scale + action.getWidthRect()*scale+2;
			if(action.getPrec().size()==0 && action.getEffect().size()==0) {
				x1=x1+5;
			}
			double y1 = action.getHeightRect()*scale + 40;
			parent.setSize((int)x1, (int)y1);
		}
	}
     
     
	private MouseWheelListener getMouseListener() {
		
		MouseWheelListener listener=new MouseWheelListener() {
			
			@Override
			public void mouseScrolled(MouseEvent e) {
				if (e.count > 0)
	                scale += .2f;
	            else
	                scale -= .2f;

	            if(scale>1.2) {
	            	scale=1.2f;
	            }
	            if(scale<0.6) {
	            	scale=0.6f;
	            }
	            scale = Math.max(scale, 0);

	            redraw();
				
			}
		};
		
		return listener;
	}
	
	public PaintListener createPaintListener() {
		

		 p = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				action.resize();
		
				Font tempFont = new Font(getDisplay(), "Arabic Transparent", 6, SWT.NORMAL);
	            FontData data = tempFont.getFontData()[0];
	            if (initialFontSize == -1)
	                initialFontSize = tempFont.getFontData()[0].getHeight();
	            else
	            {
	                if(font != null && !font.isDisposed())
	                    font.dispose();

	                data.setHeight((int)(initialFontSize * scale));

	                font = new Font(getDisplay(), data);

	                e.gc.setFont(font);
	            }

				e.gc.setFont(font);
			
				Color colorNull=e.gc.getBackground();
				e.gc.setFont(font);

				int y = 20;
				
				if(action.isDefaultAction()) {
					if(action.isPrimitive) {
						action.setIsFett(GlobalValue.borderIsFatPr);
						action.setIsborder(GlobalValue.formIsBlackPr);
						action.setBorderIsSquare(GlobalValue.cornerIsSquarePr);
						action.setColorString(GlobalValue.colorP);
					}else {
						action.setIsFett(GlobalValue.borderIsFatAbst);
						action.setIsborder(GlobalValue.formIsBlackAbst);
						action.setBorderIsSquare(GlobalValue.cornerIsSquareAbst);
						action.setColorString(GlobalValue.colorAbst);
					}
				}

				
				int posY=(int) ((10+action.getHeightRect()/action.getNumPrec())/2)+y; 
				int incr=(int) (action.getHeightRect()/action.getNumPrec());

				for (int i = 0; i < action.getNumPrec(); i++) {

					if (action.isShownCond()) {
						String string = action.getPrec().get(i);
						e.gc.drawLine(0, (int) (posY*scale), (int) (action.getLengthPrec()*scale), (int) (posY*scale));
						e.gc.drawString(string, 2, (int) ((posY- 10)*scale), false);

					} else {
						e.gc.drawLine(0, (int) (posY*scale), (int) ((int) action.getStandardLengthPrec()*scale), (int) (posY*scale));
					}

					posY = posY + incr;
				}

				/* Drawing rectangle w/o name */
				Rectangle rect;
				if(action.isFett()) {
					e.gc.setLineWidth(3);
				}else {
					e.gc.setLineWidth(0);

				}
				if (action.isShownCond()) {
					rect = new Rectangle((int)(action.getLengthPrec()*scale),y,(int) ( action.getWidthRect()*scale), (int) (action.getHeightRect()*scale));
				} else {
					rect = new Rectangle((int)(action.getStandardLengthPrec()*scale), y, (int) (action.getWidthRect()*scale),(int) (action.getHeightRect()*scale));	
				}


				if (action.Isborder()) {
					if(action.isFillColor()) {
						e.gc.setBackground(getColorSWT());
						if(!action.isBorderIsSquare()) {
							e.gc.fillRoundRectangle(rect.x, rect.y, rect.width, rect.height, 10, 10);
							e.gc.drawRoundRectangle(rect.x, rect.y, rect.width, rect.height, 10, 10);

						}else {
							e.gc.fillRectangle(rect);
							e.gc.drawRectangle(rect);	

						}
					}else {
						if(!action.isBorderIsSquare()) {
							e.gc.drawRoundRectangle(rect.x, rect.y, rect.width, rect.height, 10, 10);

						}else {
							e.gc.drawRectangle(rect);	
						}
					}
					

				}
				
				
				e.gc.setLineWidth(0);

				int widthSize = (int)e.gc.getFontMetrics().getAverageCharWidth();

				int val=(int) (getTextPosition(widthSize)+rect.x);

				
				if (action.isShownName()) {
					e.gc.drawString(action.getName(), (int) (val*scale), rect.y + rect.height / 3);
				}

				e.gc.setBackground(colorNull);
				
				
				
				posY=(int) (10+(action.getHeightRect())/action.getNumEff())/2+y; 
				incr=(int) ((action.getHeightRect())/action.getNumEff());	
				resizeParent();

				for (int i = 0; i < action.getEffect().size(); i++) {
					int x = rect.x + rect.width;

					if (action.isShownCond()) {
						String string = action.getEffect().get(i);
						e.gc.drawLine(x, (int) (posY*scale), (int) (x + action.getLengthEff()*scale), (int) (posY*scale));
						e.gc.drawString(string, (int) (x + 2), (int) ((posY- 10)*scale) , false);

					} else {

						e.gc.drawLine(x,  (int) (posY*scale), (int) (x + action.getStandardLengthEff()*scale),  (int) (posY*scale));

					}

					posY = posY + incr;

				}

				resizeParent();
				e.gc.dispose();
			}
		};
		
		return p;
	}
     
	
}


