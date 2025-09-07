package generateurnom;

import java.util.Random;

public class Matrice {

	public static final int SIZE = 128; // Taille maximale de la matrice (128 caractères ASCII)
	private int[][] matriceTransition = new int[SIZE][SIZE]; // Matrice qui stocke les transitions entre caractères
	private int[] total = new int[SIZE]; // Total des transitions sortantes pour chaque caractère

	// Réinitialise complètement la matrice et le total des transitions
	public void reinitialiser() {
		for (int i = 0; i < SIZE; i++) {
			total[i] = 0;
			for (int j = 0; j < SIZE; j++) {
				matriceTransition[i][j] = 0;
			}
		}
	}

	// Enregistre une transition d'un caractère vers un autre (ex : 'a' -> 'b')
	public void enregistrerTransition(int actuel, int suivant) {
		if (actuel < SIZE && suivant < SIZE) {
			matriceTransition[actuel][suivant]++;
			total[actuel]++;
		}
	}

	// Tire aléatoirement le prochain caractère à partir du caractère actuel
	public char getProchainCaractere(char caractereActuel, Random aleatoire) {
		int i = (int) caractereActuel;
		int totalLigne = total[i];

		if (totalLigne == 0)
			return '$'; // Aucun caractère suivant enregistré, on termine
		// On choisit une transition au hasard en fonction de leur fréquence
		int positionAleatoire = aleatoire.nextInt(totalLigne) + 1;
		int cumul = 0;

		for (int j = 0; j < SIZE; j++) {
			cumul += matriceTransition[i][j];
			if (positionAleatoire <= cumul) {
				return (char) j;
			}
		}
		return '$'; // Sécurité : ne devrait jamais arriver si les totaux sont corrects
	}

	// Retourne le contenu brut de la matrice (utile pour tests ou affichages
	// avancés)
	public int[][] getContenu() {
		return matriceTransition;
	}

	// Affiche uniquement les transitions existantes (non nulles) de façon lisible
	public void afficherTransitions() {
		boolean vide = true;
		System.out.println("Matrice de transition :");

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (matriceTransition[i][j] > 0) {
					char actuel = (char) i;
					char suivant = (char) j;
					// Affiche les caractères spéciaux ^ et $ de façon explicite
					String sActuel = (actuel == '^') ? "^" : (actuel == '$') ? "$" : Character.toString(actuel);
					String sSuivant = (suivant == '^') ? "^" : (suivant == '$') ? "$" : Character.toString(suivant);
					System.out.printf("'%s' -> '%s' : %d\n", sActuel, sSuivant, matriceTransition[i][j]);
					vide = false;
				}
			}
		}
		if (vide) {
			System.out.println("La matrice est vide.");
		}
	}

}
