package com.carlogaratti.dna_playground;
import com.carlogaratti.dna_v1.core.Node;

public class StarterFSMPatternState {

	private static Node current;

	public static void main(String[] args) {
		
		/*
		 *         ●
        │
        ▼
  ┌───────────┐   submit    ┌────────────┐   approve   ╔═════════════╗
  │   Draft   │ ──────────► │  InReview  │ ──────────► ║  Published  ║
  └───────────┘             └────────────┘             ╚═════════════╝
        ▲                         │                    (stato finale)
        │                         │ reject
        │                         ▼
        │     revise        ┌────────────┐
        └────────────────── │  Rejected  │
                            └────────────┘
		 */

		// partecipants
		Node draft = new Node("Draft");
		Node inReview = new Node("InReview");
		Node published = new Node("Published");
		Node rejected = new Node("Rejected");

		// building
		draft.withLabel("submit").goTo(inReview);
		inReview.withLabel("approve").goTo(published);
		inReview.withLabel("reject").goTo(rejected);
		rejected.withLabel("revise").goTo(draft);

		// messaggi delle transizioni, salvati nel Bag del nodo di partenza
		draft.put("submit").asString("[Draft] Documento inviato in revisione.");
		inReview.put("approve").asString("[InReview] Documento approvato.");
		inReview.put("reject").asString("[InReview] Documento rifiutato.");
		rejected.put("revise").asString("[Rejected] Documento rimesso in bozza per la revisione.");

		current = draft;
		print();
		fire("submit");
		fire("reject");
		fire("revise");
		fire("submit");
		fire("approve");

		try {
			fire("submit"); // non valido: il documento e' gia' pubblicato
		} catch (IllegalStateException e) {
			System.out.println("Errore atteso: " + e.getMessage());
		}
	}

	private static void fire(String event) {
		Node next = current.goTo(event);
		if (next == null)
			throw new IllegalStateException("Operazione '" + event + "' non consentita nello stato " + current);

		System.out.println(current.get(event).asString());
		System.out.println("   >> transizione: " + current + " -> " + next);
		current = next;
		print();
	}

	private static void print() {
		System.out.println("Stato corrente: " + current);
	}

}
