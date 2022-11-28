package compiladorl3;

import java.util.Objects;

public class ListaCircular {

    private ListaCircularNode sentinel;

    public ListaCircular() {
        this.sentinel = new ListaCircularNode(0, "", null, null);
        this.sentinel.setant(this.sentinel);
        this.sentinel.setprox(this.sentinel);
    }

    public boolean isEmpty() {
        return getTail() == this.sentinel && getHead() == this.sentinel;
    }

    public void addFirst(int tipo, String var) {
        if (isEmpty()) {
            ListaCircularNode newCircularNode = new ListaCircularNode(tipo, var, this.sentinel, this.sentinel);
            this.sentinel.setprox(newCircularNode);
            this.sentinel.setant(newCircularNode);
        } else {
            ListaCircularNode newCircularNode = new ListaCircularNode(tipo, var, this.sentinel,
                    this.sentinel.getprox());
            this.sentinel.getprox().setant(newCircularNode);
            this.sentinel.setprox(newCircularNode);
        }
    }

    public ListaCircularNode getTail() {
        return this.sentinel.getant();
    }

    public int size() {
        if (isEmpty()) {
            return 0;
        } else {
            ListaCircularNode currentNode = this.sentinel;
            int size = 0;
            do {
                size++;
                currentNode = currentNode.getprox();
            } while (currentNode.getprox() != this.sentinel);
            return size;
        }
    }

    public void addLast(int tipo, String var) {
        if (isEmpty()) {
            addFirst(tipo, var);
        } else {
            ListaCircularNode newCircularNode = new ListaCircularNode(tipo, var, this.sentinel.getant(),
                    this.sentinel);
            this.sentinel.getant().setprox(newCircularNode);

            this.sentinel.setant(newCircularNode);
        }
    }

    public void reverse() {
        ListaCircularNode currentNode = getHead();
        ListaCircularNode proxNode = currentNode;
        ListaCircularNode antNode = this.sentinel;

        do {
            proxNode = proxNode.getprox();
            currentNode.setant(currentNode.getprox());
            currentNode.setprox(antNode);
            antNode = currentNode;
            currentNode = proxNode;

        } while (currentNode != this.sentinel);
        currentNode.setant(currentNode.getprox());
        currentNode.setprox(antNode);
        this.sentinel.setprox(antNode);
    }

    public ListaCircularNode getHead() {
        return this.sentinel.getprox();
    }

    public ListaCircularNode search(String var) {
        ListaCircularNode currentNode = getHead();
        do {
            if (Objects.equals(currentNode.getvar(), var)) {
                return currentNode;
            }
            currentNode = currentNode.getprox();
        } while (currentNode != this.sentinel);

        return null;
    }

    public void delete(ListaCircularNode nodeDelete) {
        ListaCircularNode currentNode = getHead();

        do {

            if (currentNode == nodeDelete) {
                if (currentNode.getant() == this.sentinel && currentNode.getprox() == this.sentinel) {
                    this.sentinel.setprox(this.sentinel);
                    this.sentinel.setant(this.sentinel);
                } else if (currentNode.getant() == this.sentinel && currentNode.getprox() != this.sentinel) {
                    this.sentinel.setprox(currentNode.getprox());
                    this.sentinel.getprox().setant(this.sentinel);
                } else if (currentNode.getprox() == this.sentinel && currentNode.getant() != this.sentinel) {
                    currentNode.getant().setprox(this.sentinel);
                    this.sentinel.setant(currentNode.getant());
                } else if (currentNode.getprox() != this.sentinel && currentNode.getant() != this.sentinel) {
                    currentNode.getant().setprox(currentNode.getprox());
                    currentNode.getprox().setant(currentNode.getant());
                }
                return;
            }

            currentNode = currentNode.getprox();
        } while (currentNode != this.sentinel);

    }

}