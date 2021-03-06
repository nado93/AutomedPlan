package LaTex;
/**
 * Generate the LateX code for the plan-actions,causal link and ordering constrain.
 * @author nadir
 * */
import java.text.NumberFormat;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import Action.Action;
import Action.ICanvas;
import Action.Node;
import PlanPart.LinkCanvas;
import PlanPart.OrderConstrain;
import PlanPart.Oval;
import PlanPart.PlanContent;
import so_goalState.GoalStateCanvas;
import so_goalState.IStateCanvas;
import so_goalState.InitialStateCanvas;

public class LaTexGeneratorNode {

	final double PIXEL_MEASUREMNT= 0.026458;
	final double CM_MEASUREMNT= 37.7957517575025;
	PlanContent planContent;
	
	
	public LaTexGeneratorNode(PlanContent planContent) {
		this.planContent=planContent;
	}
	
	public String getLatexActionCodePlan(Action action,Node node) {
		StringBuilder sb = new StringBuilder();
		sb.append("\\action");
		sb.append("{"+node.getID()+"}");//numerare le azioni
		sb.append("{"+getNameAction(action));
		if(actionHasVariable(action))  {
			sb.append("="+getVariable(action.getName()));
		}
		sb.append(","+"\n");
		sb.append("  body="+"{");
		sb.append(isPrimitive(action));
		sb.append(isFillColor(action));
		sb.append(isBorderWhite(action));
		sb.append(isRound(action));
		sb.append(isFett(action));
		//we need to take care of init/goal
		if (planContent.getInitialStateCanvas() != null) {
//			if (planContent.getInitialStateCanvas().getState().isText()) {
//				sb.append(" below right=" + getPositionToInit(node) + "}" + "\n" + "}" + "\n");
//			} else {
		sb.append("at={" + getPosition(node) + "}}" + "\n" + "}" + "\n");
			
		} else {
			sb.append("at={" + getPosition(node) + "}}" + "\n" + "}" + "\n");

		}
		return sb.toString();
	}
	
	public String getLatexLinkCodePlan(LinkCanvas link) {
		StringBuilder sb = new StringBuilder();
		sb.append("\\link");
		
		sb.append("{"+isStateorAction(link.getOval1())+"/"+isPreOrEff(link.getOval1())+"}");
		sb.append("{"+isStateorAction(link.getOval2())+"/"+isPreOrEff(link.getOval2())+"}");
		sb.append("{edge"+getBend(link)+"}"+"\n"+"\n");
		return sb.toString();
	}
	
	
	public String getLatexOrderingCodePlan(OrderConstrain order) {
		StringBuilder sb = new StringBuilder();
		if(!order.getCond1().isDisposed() && !order.getCond2().isDisposed()) {
			sb.append("\\ordering");
			sb.append("{[yshift=.65cm,xshift=.2cm]"+order.getCond1().getID()+".east}");
			sb.append("{[yshift=.65cm,xshift=-.2cm]"+order.getCond2().getID()+".west}"+"\n"+"\n");
		}else {
			MessageBox messageBox = new MessageBox(planContent.getShell(),
					SWT.ICON_WARNING |  SWT.OK);

			messageBox.setText("Warning");
			messageBox.setMessage("Some ordering constrains missing one/both action's order."+"\n"+
			"Plese remove it.");
			messageBox.open();
		}
		
		return sb.toString();
	}
	
	private String isFillColor(Action a) {
		StringBuilder sb=new StringBuilder();
		if(a.isFillColor()) {
			sb.append("fill="+a.getColorString().toLowerCase()+",");
		}
		return sb.toString();
	}
	
	private String isRound(Action a) {
		StringBuilder sb=new StringBuilder();
		if(!(a.isBorderIsSquare())) {
			if(a.isPrimitive()) {
				sb.append("rounded corners,");
			}
		}
		return sb.toString();

		
	}
	
	private String isBorderWhite(Action a) {
		StringBuilder sb=new StringBuilder();
		if(!(a.Isborder())) {
			sb.append("draw=white,");
		}
		return sb.toString();

	}
	
	private String isFett(Action a) {
		StringBuilder sb=new StringBuilder();
		if(a.isFett()) {
			if(a.isAbstract()) {
				sb.append("thick,");
			}
		}
		return sb.toString();

		
	}
	
	
	private String isPrimitive(Action a) {
		StringBuilder sb=new StringBuilder();
		if(a.isDefaultAction()) {
			if(a.isPrimitive()) {
				sb.append("primitive"+",");
			}else {
				sb.append("abstract"+",");
			}
		}
		return sb.toString();
	}
	
	private String isStateorAction(Oval o) {
		StringBuilder sb = new StringBuilder();
		if(o.getNode()!= null) {
			sb.append(o.getNode().getID());
		}else if(o.getStateCanvas()!=null) {
			if(o.getStateCanvas() instanceof InitialStateCanvas) {
				if(o.getStateCanvas().getState().isText()) {
					sb.append("init");
				}else {
					sb.append("init");
				}
			}else if(o.getStateCanvas() instanceof GoalStateCanvas)  {
					sb.append("goal");
			
			}
		}
		
		
		return sb.toString();


	}
	
	/*tell if it is a prec or eff and the number*/
	public String isPreOrEff(Oval o){
		
		StringBuilder sb = new StringBuilder();
		String cond=o.getCond();

		/*if is a action*/
		if(o.getNode()!= null) {
			Action a=o.getNode().getAction();
			for(int i=0;i<a.getPrec().size();i++) {
				if(a.getPrec().get(i).equals(cond)) {
					sb.append("pre/");
					int num=i+1;
					sb.append(num);
					return sb.toString();
				}
			}
			for(int i=0;i<a.getEffect().size();i++) {
				if(a.getEffect().get(i).equals(cond)) {
					sb.append("eff/");
					int num=i+1;
					sb.append(num);
					return sb.toString();
				}
			}
			
			
		/*if it is a start/goal state*/
		}else if(o.getStateCanvas()!= null) {
			IStateCanvas stateCanv=o.getStateCanvas();
			if(stateCanv instanceof InitialStateCanvas) {
				for(int i=0;i<stateCanv.getState().getConds().size();i++) {
					if(stateCanv.getState().getConds().get(i).equals(cond)) {
						sb.append("eff/");
						int num=i+1;
						sb.append(num);
						return sb.toString();
					}
				}
				return sb.toString();
			}else if(stateCanv instanceof GoalStateCanvas) {
				
				for(int i=0;i<stateCanv.getState().getConds().size();i++) {
					if(stateCanv.getState().getConds().get(i).equals(cond)) {
						sb.append("pre/");
						int num=i+1;
						sb.append(num);
						return sb.toString();
					}
				}
			}
		}
		
		return sb.toString();


	}
	
	public String getNameAction(Action action) {
		String string=action.getName();
		String name[]=string.split("\\(");	
		StringBuilder sb=new StringBuilder();
		sb.append(name[0]);
		if(!action.isShownCond()) {
			sb.append("-noop");
		}
		
		return sb.toString();
	}
	

	
	
	public String getBend(LinkCanvas link) {
		StringBuilder sb=new StringBuilder();		
		sb.append("[bend ");
		
		//ask if is left o right
		if(link.isRight()) {
			sb.append("right,");
		}else {
			sb.append("left,");
		}
		//ask the inclination of curve
		
		sb.append("->]");
		
		
		return sb.toString();

		
	}
	
	public String getVariable(String string) {
		String name[]=string.split("\\(");
		String variable[]=name[1].split("\\)");
		variable=variable[0].split(",");
		int num=variable.length;
	
		
		String testo="";
		for(int i=0;i<num;i++) {
			testo +="{$"+variable[i]+"$}";
		}
		
		
		StringBuilder sb=new StringBuilder();
		sb.append(testo);

		if(num==1) {
			sb.append("{}");
		}
		
		return sb.toString();
	}
	
	
	
	private String getPosition(ICanvas node) {
		StringBuilder sb=new StringBuilder();	

		int value =0;
		if(planContent.getInitialStateCanvas()!=null) {
			sb.append("("+convertInCm(node.getParent().getLocation().x-planContent.getLocation().x+planContent.getInitialStateCanvas().getClientArea().width)+",");
			double test1=(planContent.getInitialStateCanvas().getClientArea().height);
			int test3=(int) (planContent.getInitialStateCanvas().getParent().getLocation().y);
		    test3=(int) (test1+test3);

			test3=2*test3/5;
			value=(int) (test3-node.getParent().getLocation().y);
			
			
			
			
		}else {
			sb.append("("+convertInCm((int) (node.getParent().getLocation().x-node.getAction().getLengthPrec()))+",");

			int test=planContent.getClientArea().height;
			value=(int) (test-node.getParent().getLocation().y );

			
		}
		 
		value=(int) (value+(node.getAction().getHeightRect()/2));
		
		sb.append(convertInCm(value)+")");

		return sb.toString();
	}
	
	public String convertInCm(int x) {

	    
		NumberFormat nf_out = NumberFormat.getNumberInstance(Locale.UK);
		nf_out.setMaximumFractionDigits(2);
		String result = nf_out.format(x*PIXEL_MEASUREMNT);
	     
	    return result;
	    
	}
	
	
	private boolean actionHasVariable(Action a) {
		boolean result=false;
		String name=a.getName();
		if(name.contains("(")&& name.contains(")")) {
			result=true;
		}
		
		return result;
	
	}
	
	
	
}
