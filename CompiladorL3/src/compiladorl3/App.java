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
        Sintatico2 sintatico2 = new Sintatico2(lexico);
        sintatico2.P();
    }

}
