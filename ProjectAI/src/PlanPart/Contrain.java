package PlanPart;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class Contrain extends Canvas{

	public static Composite parent;
	private static float scale = 1;

	
	public Contrain(Composite parent, int style) {
		super(parent, style);
		this.parent=parent;
	}
	//have to be improved, working just with constant, should be dynamically
	public void draw() {
		
		addPaintListener(getListener());
		 this.addListener(SWT.MouseWheel, new Listener()
		    {
		        @Override
		        public void handleEvent(Event event)
		        {
		            if (event.count > 0)
		                scale += .2f;
		            else
		                scale -= .2f;

		            scale = Math.max(scale, 0);

		            redraw();
		        }
		    });
	}
	
	
	public static PaintListener getListener() {
		PaintListener p;
		

		p = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				System.out.println(parent.getBounds().width);
				System.out.println(parent.getBounds().height);

				
			    e.gc.drawArc(0, 25,80, 35, 0, 180);
			    drawArrow(e.gc, 60, 0, 82, 44, 8, Math.toRadians(45));
			    
				drawArrowE(e.gc, 80, 10, 40, 10, 10, Math.toRadians(45));

			    
			}

		};

		return p;

	}
	
	public static  void drawArrow(GC gc, int x1, int y1, int x2, int y2, double arrowLength, double arrowAngle) {
	    double theta = Math.atan2(y2 - y1, x2 - x1);
	    double offset = (arrowLength - 2) * Math.cos(arrowAngle);


	    Path path = new Path(gc.getDevice());
	    path.moveTo((float)(x2 - arrowLength * Math.cos(theta - arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta - arrowAngle)));
	    path.lineTo((float)x2, (float)y2);
	    path.lineTo((float)(x2 - arrowLength * Math.cos(theta + arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta + arrowAngle)));
	    path.close();
	    gc.fillPath(path);
	    gc.drawPath(path);

	    path.dispose();
	}
	
	public static  void drawArrowE(GC gc, int x1, int y1, int x2, int y2, double arrowLength, double arrowAngle) {
	    double theta = Math.atan2(y2 - y1, x2 - x1);
	    double offset = (arrowLength - 2) * Math.cos(arrowAngle);


	    Path path = new Path(gc.getDevice());
	    path.moveTo((float)(x2 - arrowLength * Math.cos(theta - arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta - arrowAngle)));
	    path.lineTo((float)x2, (float)y2);
	    path.lineTo((float)(x2 - arrowLength * Math.cos(theta + arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta + arrowAngle)));
	 
	 
	    gc.drawPath(path);

	    path.dispose();
	}



	
	
}