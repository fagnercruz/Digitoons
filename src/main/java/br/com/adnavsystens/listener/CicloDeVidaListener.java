package br.com.adnavsystens.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class CicloDeVidaListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
	
	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("Iniciando fase: " + event.getPhaseId());
		
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		System.out.println("Finalizando fase: " + event.getPhaseId());
	}



}
