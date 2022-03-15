package entity;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Hod {
    private int id;
    private int pos;
    private int playerId;

    public Hod() {
    }

    public Hod(int id, int pos, int playerId) {
        this.id = id;
        this.pos = pos;
        this.playerId = playerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getName() {
        return playerId;
    }

    public void setName(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "Hod{" +
                "pos=" + pos +
                ", name='" + playerId + '\'' +
                '}';
    }

    // метод записи в XML файл с помощью JDOM
    public static String write(List<Player> players, List<Hod> hods, boolean draw) throws IOException {
        Document doc = new Document();
        // создаем корневой элемент с пространством имен
        doc.setRootElement(new Element("Gameplay",
                Namespace.getNamespace("")));
        int i=0;
        for(Player player : players){
            if (i==2) break;
            Element plElement = new Element("Player",
                    Namespace.getNamespace("")).
                    setAttribute("id", String.valueOf(player.getId())).
                    setAttribute("name", String.valueOf(player.getName())).
                    setAttribute("symbol", String.valueOf(player.getSymbol()));
            doc.getRootElement().addContent(plElement);
            i++;
        }


        //последний игрок в коллекции победитель
        Player winner = players.get(players.size()-1);

        Element gameResult = new Element("GameResult");
        if (!draw) {
            gameResult.addContent(new Element("Player")
                    .setAttribute("id", String.valueOf(winner.getId()))
                    .setAttribute("name", String.valueOf(winner.getName()))
                    .setAttribute("symbol", String.valueOf(winner.getSymbol())));
        }
        else{
            gameResult.setText("Draw!");
        }
        doc.getRootElement().addContent(gameResult);
        // Документ JDOM сформирован и готов к записи в файл
        XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());
        // сохнаряем в файл
        StringBuffer path = new StringBuffer("src/main/resources/List.xml");
        File file = new File(String.valueOf(path));
        i=0;
        boolean flag=true;
        if (!file.exists()){
            xmlWriter.output(doc, new FileOutputStream(String.valueOf(path)));
        }
        else {
            while (flag) {
                file = new File(String.valueOf(path));
                if (file.exists()) {
                    path.insert(path.length() - 4, String.valueOf(i));
                    flag = false;
                }
                i++;
            }
            xmlWriter.output(doc, new FileOutputStream(String.valueOf(path)));
//        }
        }
        return String.valueOf(path);
    }



}
