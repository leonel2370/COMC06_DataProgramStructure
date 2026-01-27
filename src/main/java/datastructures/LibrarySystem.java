package datastructures;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class LibrarySystem {
    private static Book[] books = new Book[100];
    private static int bookCount = 0;
    private static BorrowingHistory history = new BorrowingHistory();
    private static ActivityStack activities = new ActivityStack();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedData(); // Données de test
        int choice;

        do {
            printMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addBook();
                case 2 -> searchBook();
                case 3 -> sortBooks();
                case 4 -> {
                    System.out.println("\n--- Activités Récentes ---");
                    activities.display();
                }
                case 5 -> displayAllBooks();
                case 0 -> System.out.println("Fermeture du système...");
                default -> System.out.println("Option invalide.");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n====================================");
        System.out.println("   GESTIONNAIRE DE BIBLIOTHÈQUE");
        System.out.println("====================================");
        System.out.println("1. Ajouter un livre");
        System.out.println("2. Rechercher un livre");
        System.out.println("3. Trier les livres (Algorithmes)");
        System.out.println("4. Historique des activités");
        System.out.println("5. Afficher tous les livres");
        System.out.println("0. Quitter");
        System.out.print("Votre choix: ");
    }

    // --- OPÉRATIONS ---

    private static void addBook() {
        System.out.print("Titre: "); String t = scanner.nextLine();
        System.out.print("Auteur: "); String a = scanner.nextLine();
        System.out.print("ISBN: "); String i = scanner.nextLine();
        System.out.print("Année: "); int y = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Genre: "); String g = scanner.nextLine();

        books[bookCount++] = new Book(t, a, i, y, g);
        activities.push("Ajout du livre: " + t);
        System.out.println("Livre ajouté avec succès.");
    }

    private static void searchBook() {
        System.out.println("1. Recherche Linéaire | 2. Recherche Binaire (nécessite un tri)");
        int type = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Entrez le titre à chercher: ");
        String query = scanner.nextLine();

        int index = -1;
        if (type == 1) {
            // Recherche linéaire
            for (int i = 0; i < bookCount; i++) {
                if (books[i].getTitle().equalsIgnoreCase(query)) { index = i; break; }
            }
        } else {
            // Binaire (Simulée ici : nécessite un tri préalable par titre)
            Arrays.sort(books, 0, bookCount, Comparator.comparing(Book::getTitle));
            int low = 0, high = bookCount - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                int res = query.compareToIgnoreCase(books[mid].getTitle());
                if (res == 0) { index = mid; break; }
                if (res > 0) low = mid + 1; else high = mid - 1;
            }
        }

        if (index != -1) System.out.println("Trouvé : " + books[index]);
        else System.out.println("Livre non trouvé.");
    }

    private static void sortBooks() {
        System.out.println("Trier par: 1. Titre | 2. Année");
        int crit = scanner.nextInt();
        System.out.println("Algorithme: 1. Bubble | 2. Selection | 3. Quicksort");
        int algo = scanner.nextInt();

        long start = System.nanoTime();
        if (algo == 1) bubbleSort(crit);
        else if (algo == 2) selectionSort(crit);
        else quickSort(0, bookCount - 1, crit);
        long end = System.nanoTime();

        activities.push("Tri effectué en " + (end - start) / 1000 + " µs");
        System.out.println("Tri terminé.");
    }

    // --- ALGORITHMES DE TRI ---

    private static void bubbleSort(int crit) {
        for (int i = 0; i < bookCount - 1; i++) {
            for (int j = 0; j < bookCount - i - 1; j++) {
                boolean swap = (crit == 1) 
                    ? books[j].getTitle().compareToIgnoreCase(books[j+1].getTitle()) > 0
                    : books[j].getPublicationYear() > books[j+1].getPublicationYear();
                if (swap) {
                    Book temp = books[j];
                    books[j] = books[j+1];
                    books[j+1] = temp;
                }
            }
        }
    }

    private static void selectionSort(int crit) {
        for (int i = 0; i < bookCount - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < bookCount; j++) {
                boolean smaller = (crit == 1)
                    ? books[j].getTitle().compareToIgnoreCase(books[minIdx].getTitle()) < 0
                    : books[j].getPublicationYear() < books[minIdx].getPublicationYear();
                if (smaller) minIdx = j;
            }
            Book temp = books[minIdx];
            books[minIdx] = books[i];
            books[i] = temp;
        }
    }

    private static void quickSort(int low, int high, int crit) {
        if (low < high) {
            int pi = partition(low, high, crit);
            quickSort(low, pi - 1, crit);
            quickSort(pi + 1, high, crit);
        }
    }

    private static int partition(int low, int high, int crit) {
        Book pivot = books[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            boolean condition = (crit == 1)
                ? books[j].getTitle().compareToIgnoreCase(pivot.getTitle()) < 0
                : books[j].getPublicationYear() < pivot.getPublicationYear();
            if (condition) {
                i++;
                Book temp = books[i];
                books[i] = books[j];
                books[j] = temp;
            }
        }
        Book temp = books[i + 1];
        books[i + 1] = books[high];
        books[high] = temp;
        return i + 1;
    }

    private static void displayAllBooks() {
        for (int i = 0; i < bookCount; i++) System.out.println(books[i]);
    }

    private static void seedData() {
        books[bookCount++] = new Book("Le Petit Prince", "Saint-Exupery", "123", 1943, "Fable");
        books[bookCount++] = new Book("1984", "George Orwell", "456", 1949, "Dystopie");
        books[bookCount++] = new Book("L'Etranger", "Albert Camus", "789", 1942, "Philosophie");
    }
}