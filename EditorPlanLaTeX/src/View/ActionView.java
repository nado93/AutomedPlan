package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
/**
 * View belongs to domain view where the action is drawn.
 * @author nadir
 * */
public class ActionView extends Composite {
	
	Composite containerAction;
	DomainView domainView;

	public ActionView(Composite parent, int style) {
		super(parent, style);
		this.setLayout();
	}

	public void setLayout() {
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 2;
		this.setLayoutData(gridData);
	}
	
	public void creareContent() {
		containerAction = new Composite(this, SWT.ALL);
		containerAction.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		containerAction.setLayout(new FillLayout());
		containerAction.setLocation(50, 100);
		
	}

	public Composite getContainerAction() {
		return containerAction;
	}

	
	public void setContainerAction(Composite containerAction) {
		this.containerAction = containerAction;
	}

	public void setDomainView(DomainView domainView) {
		this.domainView = domainView;
	}

	public DomainView getDomainView() {
		return domainView;
	}
	

	
	
}
