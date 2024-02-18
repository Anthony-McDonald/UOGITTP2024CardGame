package structures.basic;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Hand {
    private ArrayList<Card> hand = new ArrayList<>();
    private HashMap<String, Class<? extends Card>> convertMap = new HashMap<>();
    Player player;

    public Hand(Player player) {
        this.player = player;
        this.fillHashMap();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public HashMap<String, Class<? extends Card>> getConvertMap() {
        return convertMap;
    }

    public void setConvertMap(HashMap<String, Class<? extends Card>> convertMap) {
        this.convertMap = convertMap;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
    public void addToHand(Card card) {
        this.getHand().add(card);
    }

    public void removeCard(Card card) {
        this.getHand().remove(card);
    }

    // Makes use of hashmap defined
    private Class<? extends Card> cardDifferentiator(Class<? extends Card> card) {
        for (Map.Entry<String, Class<? extends Card>> entry : this.getConvertMap().entrySet()) {
            String key = entry.getKey();
            if (card.getSimpleName().equals(key)) {
                return this.getConvertMap().get(key);
            }
        }
        return null;
    }

    public void drawToHand(Class<? extends Card> card) {
        int maxHandSize = 5;

        if (this.getHand().size() < maxHandSize) {
            player.addToHand(cardDifferentiator(card));
        } else {
            player.addToDiscardPile(card);
        }
    }

    private void fillHashMap() {
        ArrayList<String> cardNamesFormatted = camelCaseifier(getCardNames());
        ArrayList<Class<? extends Card>> cardClassesExtendingCard = getCardClasses();

        for (Class<? extends Card> classReflected : cardClassesExtendingCard) {
            for (String cardName : cardNamesFormatted) {
                if (classReflected.getSimpleName().equals(cardName)) {
                    convertMap.put(cardName, classReflected);
                }
            }
        }
    }

    private ArrayList<String> getCardNames() {
        ArrayList<String> cardNames = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        File cardJsonDirectory = new File("conf/gameconfs/cards");

        File[] individualJsons = cardJsonDirectory.listFiles();

        for (File cardJson : individualJsons) {

            try {
                JsonNode node = objectMapper.readTree(cardJson);

                String cardName = node.get("cardname").asText();
                cardNames.add(cardName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cardNames;
    }

    private ArrayList<Class<? extends Card>> getCardClasses() {
        ArrayList<Class<? extends Card>> cardClasses = new ArrayList<>();

//        // subject to change, directory here wrong atm
//        File cardClassDirectory = new File("structures/basic");
//        File[] classList = cardClassDirectory.listFiles();
//
//        for (File cardClass : classList) {
//
//            String cardClassName = cardClass.getName();
//            cardClassName = cardClassName.substring(0, cardClassName.length() - 6);
//
//            try {
//                Class<?> classReflect = Class.forName(cardClassName);
//
//                if (Card.class.isAssignableFrom(classReflect)) {
//                    if (!classReflect.getSimpleName().equals("Creature") && (!classReflect.getSimpleName().equals("Spell"))) {
//                        //cardClasses.add((Class<? extends Card>) classReflect);
//                    }
//
//                } else {
//                    System.out.println("Does not extend from Card");
//                }
//
//            } catch (ClassNotFoundException c) {
//                c.printStackTrace();
//            }
//        }

        return cardClasses;
    }

    private ArrayList<String> camelCaseifier(ArrayList<String> cardNames) {
        ArrayList<String> arrayFormatted = new ArrayList<String>();

        for (String cardName : cardNames) {
            StringBuilder camelify = new StringBuilder();
            String[] separateWords = cardName.split(" ");

            camelify.append(separateWords[0].toLowerCase());

            for (int i = 1; i < separateWords.length; i++) {
                String word = separateWords[i];

                camelify.append(Character.toUpperCase(word.charAt(0)));
                camelify.append(word.substring(1).toLowerCase());
            }
            arrayFormatted.add(camelify.toString());
        }
        return arrayFormatted;
    }
}