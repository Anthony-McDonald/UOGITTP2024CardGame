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
    private Card cardDifferentiator(Card card) {
        String cardNameToGet = card.getCardname();

        Class<? extends  Card> classReturned = this.getConvertMap().get(cardNameToGet);

        if (classReturned != null) {
            try {
                return classReturned.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void drawToHand(Card card) {
        int maxHandSize = 5;

        if (this.getHand().size() < maxHandSize) {
            player.addToHand(cardDifferentiator(card));
        } else {
            player.addToDiscardPile(card);
        }
    }

    private void fillHashMap() {
        this.getConvertMap().put("Bad Omen", BadOmen.class);
        this.getConvertMap().put("Bloodmoon Priestess", BloodmoonPriestess.class);
        this.getConvertMap().put("Truestrike", Truestrike.class);
        this.getConvertMap().put("Horn of the Forsaken", HornOfTheForsaken.class);
        this.getConvertMap().put("Silverguard Squire", SilverguardSquire.class);
        this.getConvertMap().put("Silverguard Knight", SilverguardKnight.class);
        this.getConvertMap().put("Shadowdancer", Shadowdancer.class);
        this.getConvertMap().put("Wraithling Swarm", WraithlingSwarm.class);
        this.getConvertMap().put("Nightsorrow Assassin", NightsorrowAssassin.class);
        this.getConvertMap().put("Gloom Chaser", GloomChaser.class);
        this.getConvertMap().put("Beamshock", Beamshock.class);
    }

//    private void fillHashMap() {
//        ArrayList<String> cardNamesFormatted = formatAsClassifier(getCardNames());
//        ArrayList<Class<? extends Card>> cardClassesExtendingCard = getCardClasses();
//
//        for (Class<? extends Card> classReflected : cardClassesExtendingCard) {
//            for (String cardName : cardNamesFormatted) {
//                if (classReflected.getSimpleName().equals(cardName)) {
//                    convertMap.put(cardName, classReflected);
//                }
//            }
//        }
//    }

//    private ArrayList<String> getCardNames() {
//        ArrayList<String> cardNames = new ArrayList<>();
//        ObjectMapper objectMapper = new ObjectMapper();
//        File cardJsonDirectory = new File("conf/gameconfs/cards");
//
//        File[] individualJsons = cardJsonDirectory.listFiles();
//
//        for (File cardJson : individualJsons) {
//
//            try {
//                JsonNode node = objectMapper.readTree(cardJson);
//
//                String cardName = node.get("cardname").asText();
//                cardNames.add(cardName);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return cardNames;
//    }

//    @SuppressWarnings("unchecked")
//    private ArrayList<Class<? extends Card>> getCardClasses() {
//        ArrayList<Class<? extends Card>> cardClasses = new ArrayList<>();
//
////        // subject to change, directory here wrong atm
//        File cardClassDirectory = new File(".");
//        File[] classList = cardClassDirectory.listFiles();
//
//        if (cardClassDirectory != null) {
//            for (File cardClass : classList) {
//                String className = cardClass.getName().replace(".class", "");
//                try {
//                    Class<?> readClass = Class.forName(className);
//                    if (isCardSubclass(readClass)) {
//                        cardClasses.add((Class<? extends Card>) readClass);
//                    }
//                } catch (ClassNotFoundException e) {
////                    Uncommenting this will show every missing file still needing to be added
////                    e.printStackTrace();
//                }
//            }
//        }
//
//        return cardClasses;
//    }
//    private boolean isCardSubclass(Class<?> clazz) {
//        return Card.class.isAssignableFrom(clazz);
//    }


//    private ArrayList<String> formatAsClassifier(ArrayList<String> cardNames) {
//        ArrayList<String> arrayFormatted = new ArrayList<String>();
//
//        for (String cardName : cardNames) {
//            StringBuilder formatAsClass = new StringBuilder();
//            String[] separateWords = cardName.split(" ");
//
//            formatAsClass.append(separateWords[0].toUpperCase());
//
//            for (int i = 1; i < separateWords.length; i++) {
//                String word = separateWords[i];
//
//                formatAsClass.append(Character.toUpperCase(word.charAt(0)));
//                formatAsClass.append(word.substring(1).toLowerCase());
//            }
//            arrayFormatted.add(formatAsClass.toString());
//        }
//        return arrayFormatted;
//    }
}