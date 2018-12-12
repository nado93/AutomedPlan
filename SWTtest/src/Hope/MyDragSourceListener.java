package Hope;

import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.widgets.Composite;
 

public class MyDragSourceListener extends DragSourceAdapter {
	
	private Composite parentComposite;
    private DragSource source;
	
    public MyDragSourceListener(Composite parentComposite, DragSource source) {
        this.parentComposite = parentComposite;
        this.source = source;
    }
    
   
    
    @Override
    public void dragSetData(DragSourceEvent event) {
    	if(source.getControl() instanceof Node) {
    		Node canvas=(Node)source.getControl();
    		String string=canvas.getStringa();
    		event.data=string;
    	}

    }
    
}