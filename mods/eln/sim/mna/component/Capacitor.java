package mods.eln.sim.mna.component;

import mods.eln.sim.mna.SubSystem;
import mods.eln.sim.mna.misc.ISubSystemProcessI;
import mods.eln.sim.mna.state.State;

public class Capacitor extends Bipole  implements ISubSystemProcessI {

	
	public Capacitor() {
	}
	
	public Capacitor(State aPin,State bPin) {
		connectTo(aPin, bPin);
	}
		
	
	
	@Override
	public double getCurrent() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setC(double c){
		this.c = c;
		dirty();
	}
	
	private double c = 0;
	double cdt;
	
	@Override
	public void applyTo(SubSystem s) {
		cdt = c/s.getDt();
		
		s.addToA(aPin,aPin,cdt);
		s.addToA(aPin,bPin,-cdt);
		s.addToA(bPin,bPin,cdt);
		s.addToA(bPin,aPin,-cdt);		
	}
	
	@Override
	public void simProcessI(SubSystem s) {
		double add = (s.getXSafe(aPin)-s.getXSafe(bPin))*cdt;
		s.addToI(aPin, add);
		s.addToI(bPin, -add);
	}
	
	@Override
	public void quitSubSystem() {
		subSystem.removeProcess(this);
		super.quitSubSystem();
	}


	@Override
	public void addedTo(SubSystem s) {
		super.addedTo(s);
		s.addProcess(this);
	}



}
