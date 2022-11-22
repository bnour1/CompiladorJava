/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorl3;

/**
 *
 * @author tarci
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Lexico lexico = new Lexico("CompiladorL3\\src\\compiladorl3\\codigo.txt");
        Sintatico sintatico = new Sintatico(lexico);
        sintatico.S();
        /*Token t = null;
        while ((t = lexico.nextToken()) != null) {
            System.out.println(t.toString());
        }*/
    }

}
