public class Exceptions extends Exception {
	Exceptions(int a){
		switch (a){
		case 1:
			System.out.println("Wyszed�e� poza plansz�!");
			break;
		case 2:
			System.out.println("Nie mo�esz postawi� tu pionka!");
			break;
		case 3:
			System.out.println("Nie mo�esz postawi� na w�asnym pionku!");
			break;
		case 4:
			System.out.println("To nie s� pionki aktywnego gracza. Nie mo�esz ich poruszy� w tej kolejce.");
			break;
		case 5:
			System.out.println("Tu nic nie ma.");
			break;
		case 6:
			System.out.println("Istnieje mo�liwo�� bicia");
			break;
		}
	}
}
