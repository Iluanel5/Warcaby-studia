public class Exceptions extends Exception {
	Exceptions(int a){
		switch (a){
		case 1:
			System.out.println("Wyszed³eœ poza planszê!");
			break;
		case 2:
			System.out.println("Nie mo¿esz postawiæ tu pionka!");
			break;
		case 3:
			System.out.println("Nie mo¿esz postawiæ na w³asnym pionku!");
			break;
		case 4:
			System.out.println("To nie s¹ pionki aktywnego gracza. Nie mo¿esz ich poruszyæ w tej kolejce.");
			break;
		case 5:
			System.out.println("Tu nic nie ma.");
			break;
		case 6:
			System.out.println("Istnieje mo¿liwoœæ bicia");
			break;
		}
	}
}
