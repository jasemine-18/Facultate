import ro.jas.snake.jocanimalempp.model.Configuratie;

public class test {
    public static void main(String[] args) {
        Configuratie c = new Configuratie(1L, "pisica", 2, 3, "img.jpg");
        System.out.println(c.getLinie()); // trebuie să meargă dacă Lombok e ok
    }
}
