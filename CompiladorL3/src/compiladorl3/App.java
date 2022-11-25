package compiladorl3;
/**
 *
 * @author Equipe: Do Prata ao Global
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Lexico lexico = new Lexico("CompiladorL3\\src\\compiladorl3\\codigo.txt");
        Sintatico1 sintatico1 = new Sintatico1(lexico);
        sintatico1.S();
    }

}
