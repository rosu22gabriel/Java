

import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;

public class Program01_AfisareStructuraXML {
    public static void afisareNod(Node node, int nivel) {

        // 1. Afisam spatiu in functie de nivelul pe care se afla in arbore
        // si denumirea nodului
        for (int i = 0; i < nivel; i++) {
            System.out.println("    ");
        }
        System.out.println(node.getNodeName());

        // 2. Parcurgem lista de copii si apelam recursiv functia
        // pentru nodurile de tip element (ignoram textul / comentariile / ...)
        for (Node child = node.getFirstChild();
             child != null;
             child = child.getNextSibling()) {
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                afisareNod(child, nivel + 1);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 1. Obtinem obiectul factory si parser-ul
        var factory = DocumentBuilderFactory.newInstance();
        var builder = factory.newDocumentBuilder();

        // 2. Citim continutul din fisier
        // var document = builder.parse("date\\carti.xml");
        // var radacina = document.getDocumentElement();

        var document = builder.newDocument();
        var radacina = document.getDocumentElement();
        document.appendChild(radacina);

        var copil = document.createElement("copil");
        radacina.appendChild(copil);

        var copil2 = document.createElement("copil");
        radacina.appendChild(copil2);

        copil.setAttribute("cod", "13");
        copil.setTextContent("un text");

        afisareNod(radacina, 0);
    }
}
