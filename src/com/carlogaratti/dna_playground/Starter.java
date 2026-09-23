package com.carlogaratti.dna_playground;

public class Starter {

	public static void main(String[] args) {
		System.out.println("=== V1: transizione gestita dal Context ===");
		DocumentContext ctx = new DocumentContext();

		print(ctx);
		ctx.submit();
		print(ctx);
		ctx.reject();
		print(ctx);
		ctx.revise();
		print(ctx);
		ctx.submit();
		ctx.approve();
		print(ctx);

		try {
			ctx.submit(); // non valido: il documento e' gia' pubblicato
		} catch (IllegalStateException e) {
			System.out.println("Errore atteso: " + e.getMessage());
		}
	}

	private static void print(DocumentContext ctx) {
		System.out.println("Stato corrente: " + ctx.getState().getClass().getSimpleName());
	}

}
