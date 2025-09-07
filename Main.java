package generateurnom;

import java.util.Scanner;

/*Auteurs : Chaiwat Aikaew AIKC76320200
 *          Rosalie Mercier MERR05599704
 *          Dave-Olwyn Eby Ettien ETTD76360600
 *          Kacou Marc-Allen Tondoh TONK77340600
 *          Koutouan Paul-Marie Régis KOUP72310100
 * 
 * Description :
 * Un programme permettant de générer des noms, offrant des fonctionnalités dédiées à l'entraînement
 * d'un modèle à partir de noms existants, à l'ajout manuel de noms, à l'affichage de la matrice de 
 * transitions, ainsi qu'à la génération aléatoire de nouveaux noms via une interface utilisateur en console.
 */

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Generateur generateur = new Generateur();

		// Chargement automatique d’un fichier contenant 100 noms au démarrage
		try {
			generateur.entrainerDepuisFichier("src/noms(inf1004).txt");
			System.out.println("Fichier de 100 noms chargé automatiquement.");
		} catch (Exception e) {
			System.out.println("Erreur lors du chargement automatique : " + e.getMessage());
		}

		boolean quitte = false;

		// Boucle principale du menu en console
		while (!quitte) {
			System.out.println("\n--- Bienvenu dans le menu ---");
			System.out.println("1. Charger les noms depuis un fichier.");// entrer le chemin du code
			System.out.println("2. Afficher les tous les noms ajoutés.");
			System.out.println("3. Générer un ou plusieurs noms.");
			System.out.println("4. Afficher la matrice.");
			System.out.println("5. Ajouter un ou plusieurs noms.");
			System.out.println("6. Réinitialiser la matrice.");
			System.out.println("7. Quitter le menu.");
			System.out.println("Veuillez entrer votre choix : ");

			String choix = scanner.nextLine().trim();

			switch (choix) {
			case "1":
				// Permet à l’utilisateur d’entrer le chemin d’un fichier pour charger des noms
				System.out.println("Chemin du fichier : ");
				String chemin = scanner.nextLine().trim();
				try {
					generateur.entrainerDepuisFichier(chemin);
					System.out.println("Fichier chargé avec succès.");
				} catch (Exception e) {
					System.out.println("Erreur : " + e.getMessage());
				}
				break;
			case "2":
				// Affiche les noms déjà chargés ou ajoutés manuellement
				generateur.afficherNoms();
				break;
			case "3":
				// Vérifie s'il y a de(s) nom(s) dans la matrice
				if (generateur.estVide()) {
					System.out.println("Erreur : la matrice est vide, veuillez charger ou ajouter de(s) nom(s)");
					break;
				}
				// Demande combien de noms générer, puis les affiche un par un
				System.out.println("Combien de noms voulez-vous générer?");
				try {
					int n = Integer.parseInt(scanner.nextLine().trim());
					for (int i = 0; i < n; i++) {
						System.out.println((i + 1) + ". " + generateur.genererNom());
					}
				} catch (NumberFormatException e) {
					System.out.println("Entrée invalide.");
				}
				break;
			case "4":
				// Affiche la matrice de transitions entre caractères
				generateur.afficherMatrice();
				break;
			case "5":
				// Permet d’ajouter manuellement un ou plusieurs noms
				System.out.println("Combien de noms voulez-vous ajouter?");
				try {
					int nombre = Integer.parseInt(scanner.nextLine().trim());
					for (int i = 0; i < nombre; i++) {
						System.out.println("Entrez le nom #" + (i + 1) + " : ");
						String nom = scanner.nextLine();
						generateur.ajouterNom(nom);
					}
					System.out.println(nombre + " nom(s) ajouté(s).");
				} catch (NumberFormatException e) {
					System.out.println("Entrée invalide.");
				}
				break;
			case "6":
				// Vide complètement la matrice et la liste des noms
				generateur.reinitialiser();
				System.out.println("Matrice réinitialisée.");
				break;
			case "7":
				// Quitte le programme
				quitte = true;
				System.out.println("Au revoir.");
				break;
			default:
				System.out.println("Choix invalide, veuillez réessayer.");
			}

		}
		scanner.close(); // Ferme le scanner avant de quitter le programme

	}

}
