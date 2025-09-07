package generateurnom;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader; // facilite la lecture du document (ligne par ligne)
import java.io.FileReader;
import java.util.Random;

public class Generateur {
	private Matrice matrice = new Matrice(); // Modèle de transitions entre caractères
	private List<String> nomsAjoutes = new ArrayList<>(); // Liste des noms déjà ajoutés

	// Charge un fichier de noms, ligne par ligne, et enregistre les transitions
	public void chargerFichier(String chemin) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(chemin));
		String ligne;
		while ((ligne = reader.readLine()) != null) {
			ligne = ligne.trim().toLowerCase();
			if (!ligne.isEmpty()) {
				nomsAjoutes.add(ligne);
				// On ajoute des symboles spéciaux pour marquer le début (^) et la fin ($)
				String nom = "^" + ligne + "$";
				// On enregistre toutes les transitions entre caractères consécutifs
				for (int i = 0; i < nom.length() - 1; i++) {
					matrice.enregistrerTransition(nom.charAt(i), nom.charAt(i + 1));
				}

			}
		}

		reader.close();
	}

	// Réinitialise la matrice puis recharge les données depuis un fichier
	public void entrainerDepuisFichier(String chemin) throws Exception {
		matrice.reinitialiser();
		chargerFichier(chemin);
	}

	// Permet d'ajouter un nom à la main (par l'utilisateur)
	public void ajouterNom(String nom) {
		nom = nom.trim().toLowerCase();
		nomsAjoutes.add(nom);
		// On enregistre la transition à partir du début spécial '^'
		int debut = '^';
		for (int i = 0; i < nom.length(); i++) {
			int courant = nom.charAt(i);
			matrice.enregistrerTransition(debut, courant);
			debut = courant;
		}
		// Et on termine avec la transition vers '$'
		matrice.enregistrerTransition(debut, '$');
	}

	// Génère un nom aléatoire à partir de la matrice entraînée
	public String genererNom() {
		Random rand = new Random();
		String resultat = "";

		// On rejette les noms trop courts (<3 caractères)
		do {
			resultat = "";
			int courant = '^'; // On commence toujours au caractère de début

			while (true) {
				int suivant = tirerCaractereSuivant(courant, rand);
				if (suivant == '$') // Si on atteint la fin, on arrête
					break;
				resultat += (char) suivant;
				courant = suivant;
			}
		} while (resultat.length() < 3);

		return resultat;
	}

	// Tire un caractère suivant aléatoire à partir de la matrice
	private int tirerCaractereSuivant(int courant, Random rand) {
		return matrice.getProchainCaractere((char) courant, rand);
	}

	// Affiche les transitions enregistrées dans la matrice
	public void afficherMatrice() {
		matrice.afficherTransitions();
	}

	// Affiche tous les noms actuellement ajoutés ou chargés
	public void afficherNoms() {
		if (nomsAjoutes.isEmpty()) {
			System.out.println("Oups! Vous n'avez pas encore ajouté de nom.");
		} else {
			System.out.println("Noms ajoutés :");
			for (String nom : nomsAjoutes) {
				System.out.println(nom);
			}
		}
	}

	// Réinitialise le générateur : vide la matrice et la liste de noms
	public void reinitialiser() {
		matrice.reinitialiser();
		nomsAjoutes.clear();
	}

	// Vérifie si la matrice est vide (aucune transition enregistrée)
	public boolean estVide() {
		int[][] contenu = matrice.getContenu();
		for (int i = 0; i < contenu.length; i++) {
			for (int j = 0; j < contenu[i].length; j++) {
				if (contenu[i][j] > 0)
					return false;
			}
		}
		return true;
	}

}
