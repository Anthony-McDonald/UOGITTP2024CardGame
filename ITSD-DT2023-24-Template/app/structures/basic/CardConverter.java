package structures.basic;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import allCards.BadOmen;
import allCards.Beamshock;
import allCards.BloodmoonPriestess;
import allCards.DarkTerminus;
import allCards.GloomChaser;
import allCards.HornOfTheForsaken;
import allCards.IroncliffGuardian;
import allCards.NightsorrowAssassin;
import allCards.RockPulveriser;
import allCards.SaberspineTiger;
import allCards.ShadowWatcher;
import allCards.Shadowdancer;
import allCards.SilverguardKnight;
import allCards.SilverguardSquire;
import allCards.SkyrockGolem;
import allCards.SundropElixir;
import allCards.SwampEntangler;
import allCards.Truestrike;
import allCards.WraithlingSwarm;
import allCards.YoungFlamewing;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;


/*
    This class exists to convert the default Card class objects, into differentiated Classes for each of the cards.
    For example, a card with the name Bad Omen will become a BadOmen object.
 */

public class CardConverter {
    private ArrayList<Card> hand = new ArrayList<>();
    private HashMap<String, Class<? extends Card>> convertMap = new HashMap<>();
    private HashMap<String, Unit> unitMap = new HashMap<>();
    private HashMap<String, Boolean> userOwnedMap = new HashMap<>();
    private HashMap<String, ArrayList<Integer>> valueMap = new HashMap<>();

    private int lastCardID = 1;
    Player player;

    public CardConverter(Player player) {
        this.player = player;
        this.fillHashMaps();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public HashMap<String, Unit> getUnitMap() {
        return unitMap;
    }

    public void setUnitMap(HashMap<String, Unit> unitMap) {
        this.unitMap = unitMap;
    }

    public HashMap<String, Boolean> getUserOwnedMap() {
        return userOwnedMap;
    }

    public void setUserOwnedMap(HashMap<String, Boolean> userOwnedMap) {
        this.userOwnedMap = userOwnedMap;
    }

    public HashMap<String, ArrayList<Integer>> getValueMap() {
        return valueMap;
    }

    public void setValueMap(HashMap<String, ArrayList<Integer>> valueMap) {
        this.valueMap = valueMap;
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

    // This is the main card differentiator method
    public Card cardDifferentiator(Card card) {
        // Get all the card variables
        int id = lastCardID++;
        int manacost = card.getManacost();
        MiniCard miniCard = card.getMiniCard();
        BigCard bigCard = card.getBigCard();
        boolean isCreature = card.getIsCreature();
        String unitConfig = card.getUnitConfig();

        String cardNameToGet = card.getCardname();
        String cardNameCamelCased = camelCasifier(cardNameToGet);
//        int health = this.getValueMap().get(cardNameToGet).get(1);
        int attack = 1;
        int health = 1;
//        System.out.println(health);

        // Set attack and health to the values in the valuemap defined above
        try {
//            System.out.println("--nextiteration---");

            attack = this.getValueMap().get(cardNameToGet).get(0);
            health = this.getValueMap().get(cardNameToGet).get(1);
        } catch (Exception e) {
            System.out.println("Null pointer found " + cardNameToGet);
        }
//        System.out.println(attack);
        Unit unit = this.getUnitMap().get(cardNameToGet);
//        System.out.println(unit);
//        boolean userOwned = this.getUserOwnedMap().get(cardNameToGet);
        boolean userOwned = true;
//        System.out.println(userOwned);

//        int maxHealth, int currentHealth, int attack, int turnSummoned, int lastTurnMoved, Unit unit, boolean userOwned
        //         a               a            b                0                   0           c             d
//        ArrayList = valueMap.get(card.getCardname());

//        Unit unit = BasicObjectBuilders.loadUnit(StaticConfFiles.)




        Class<? extends  Card> classReturned = this.getConvertMap().get(cardNameToGet);
//        System.out.println(classReturned);
        // If the line above isn't null
        if (classReturned != null) {
            // try checking if the class returned is a creature
            try {
                if (Creature.class.isAssignableFrom(classReturned)) {
                    // if it is, create a creature of that class and return that object
                    Constructor<? extends Card> constructor = classReturned.getDeclaredConstructor(int.class, String.class, int.class, MiniCard.class, BigCard.class, boolean.class, String.class);
                    return constructor.newInstance(id, cardNameToGet, manacost, miniCard, bigCard, isCreature, unitConfig);
                } else if (Spell.class.isAssignableFrom(classReturned)) {
                    // If it isn't, create a spell of that class and return that object
                    Constructor<? extends Card> constructor = classReturned.getDeclaredConstructor(int.class, String.class, int.class, MiniCard.class, BigCard.class, boolean.class, String.class);
                    return constructor.newInstance(id, cardNameToGet, manacost, miniCard, bigCard, isCreature, unitConfig);
                }
            } catch (Exception e) {
                System.out.println("exception called");
                e.printStackTrace();
            }
        }

        return null;
    }

    // fills the hashmaps used by the card differentiator
    private void fillHashMaps() {
        // Assigning all of the card names to variable names for easier usage
        // Player
        String badOmen = "Bad Omen";
        String hornOfTheForsaken = "Horn of the Forsaken";
        String gloomChaser = "Gloom Chaser";
        String shadowWatcher = "Shadow Watcher";
        String wraithlingSwarm = "Wraithling Swarm";
        String nightsorrowAssassin = "Nightsorrow Assassin";
        String rockPulveriser = "Rock Pulveriser";
        String darkTerminus = "Dark Terminus";
        String bloodmoonPriestess = "Bloodmoon Priestess";
        String shadowdancer = "Shadowdancer";
        // Ai
        String skyrockGolem = "Skyrock Golem";
        String swampEntangler = "Swamp Entangler";
        String silverguardKnight = "Silverguard Knight";
        String saberspineTiger = "Saberspine Tiger";
        String beamshock = "Beamshock";
        String youngFlamewing = "Young Flamewing";
        String silverguardSquire = "Silverguard Squire";
        String ironcliffGuardian = "Ironcliff Guardian";
        String sundropElixir = "Sundrop Elixir";
        String truestrike = "Truestrike";


        // Create an arraylist of spells and add every spell into it
        ArrayList<Integer> spellArray = new ArrayList<>();
        spellArray.add(-1);
        spellArray.add(-1);
        spellArray.add(-1);
        this.getValueMap().put(hornOfTheForsaken, spellArray);
        this.getValueMap().put(darkTerminus, spellArray);
        this.getValueMap().put(beamshock, spellArray);
        this.getValueMap().put(sundropElixir, spellArray);
        this.getValueMap().put(truestrike, spellArray);
        this.getValueMap().put(wraithlingSwarm, spellArray);

//        this.getValueMap().get(cardNameToGet).get(0));
        // Add in each creature to the hashmap ConvertMap
        this.getConvertMap().put(badOmen, BadOmen.class);
        this.getUserOwnedMap().put(badOmen, true);
//        System.out.println("attempting to load badOmenUnit");
        this.getUnitMap().put(badOmen, BasicObjectBuilders.loadUnit(StaticConfFiles.badOmen, 2000, Unit.class));
//        System.out.println("loaded badomen unit");
        ArrayList<Integer> badOmenIntegerValues = new ArrayList<>();
        // First value is attack, second value is health
        badOmenIntegerValues.add(0);
        badOmenIntegerValues.add(1);
        this.getValueMap().put(badOmen, badOmenIntegerValues);

        this.getConvertMap().put(hornOfTheForsaken, HornOfTheForsaken.class);
//        this.getUserOwnedMap().put(hornOfTheForsaken, true);

        this.getConvertMap().put(gloomChaser, GloomChaser.class);
        this.getUserOwnedMap().put(gloomChaser, true);
        this.getUnitMap().put(gloomChaser, BasicObjectBuilders.loadUnit(StaticConfFiles.gloomChaser, 2001, Unit.class));
        ArrayList<Integer> gloomChaserIntegerValues = new ArrayList<>();
        gloomChaserIntegerValues.add(3);
        gloomChaserIntegerValues.add(1);
        this.getValueMap().put(gloomChaser, gloomChaserIntegerValues);

        this.getConvertMap().put(shadowWatcher, ShadowWatcher.class);
        this.getUserOwnedMap().put(shadowWatcher, true);
        this.getUnitMap().put(shadowWatcher, BasicObjectBuilders.loadUnit(StaticConfFiles.shadowWatcher, 2002, Unit.class));
        ArrayList<Integer> shadowWatcherIntegerValues = new ArrayList<>();
        shadowWatcherIntegerValues.add(3);
        shadowWatcherIntegerValues.add(2);
        this.getValueMap().put(shadowWatcher, shadowWatcherIntegerValues);

        this.getConvertMap().put(wraithlingSwarm, WraithlingSwarm.class);
//        this.getUserOwnedMap().put(wraithlingSwarm, true);


        this.getConvertMap().put(nightsorrowAssassin, NightsorrowAssassin.class);
        this.getUserOwnedMap().put(nightsorrowAssassin, true);
        this.getUnitMap().put(nightsorrowAssassin, BasicObjectBuilders.loadUnit(StaticConfFiles.nightsorrowAssassin, 2003, Unit.class));
        ArrayList<Integer> nightsorrowAssassinIntegerValues = new ArrayList<>();
        nightsorrowAssassinIntegerValues.add(4);
        nightsorrowAssassinIntegerValues.add(2);
        this.getValueMap().put(nightsorrowAssassin, nightsorrowAssassinIntegerValues);

        this.getConvertMap().put(rockPulveriser, RockPulveriser.class);
        this.getUserOwnedMap().put(rockPulveriser, true);
        this.getUnitMap().put(rockPulveriser, BasicObjectBuilders.loadUnit(StaticConfFiles.rockPulveriser, 2004, Unit.class));
        ArrayList<Integer> rockPulveriserIntegerValues = new ArrayList<>();
        rockPulveriserIntegerValues.add(1);
        rockPulveriserIntegerValues.add(4);
        this.getValueMap().put(rockPulveriser, rockPulveriserIntegerValues);

        this.getConvertMap().put(darkTerminus, DarkTerminus.class);
//        this.getUserOwnedMap().put(darkTerminus, true);

        this.getConvertMap().put(bloodmoonPriestess, BloodmoonPriestess.class);
        this.getUserOwnedMap().put(bloodmoonPriestess, true);
        this.getUnitMap().put(bloodmoonPriestess, BasicObjectBuilders.loadUnit(StaticConfFiles.bloodmoonPriestess, 2005, Unit.class));
        ArrayList<Integer> bloodmoonPriestessIntegerValues = new ArrayList<>();
        bloodmoonPriestessIntegerValues.add(3);
        bloodmoonPriestessIntegerValues.add(3);
        this.getValueMap().put(bloodmoonPriestess, bloodmoonPriestessIntegerValues);

        this.getConvertMap().put(shadowdancer, Shadowdancer.class);
        this.getUserOwnedMap().put(shadowdancer, true);
        this.getUnitMap().put(shadowdancer, BasicObjectBuilders.loadUnit(StaticConfFiles.shadowdancer, 2006, Unit.class));
        ArrayList<Integer> shadowdancerIntegerValues = new ArrayList<>();
        shadowdancerIntegerValues.add(5);
        shadowdancerIntegerValues.add(4);
        this.getValueMap().put(shadowdancer, shadowdancerIntegerValues);

// Should be bot
        this.getConvertMap().put(skyrockGolem, SkyrockGolem.class);
        this.getUserOwnedMap().put(skyrockGolem, false);
        this.getUnitMap().put(skyrockGolem, BasicObjectBuilders.loadUnit(StaticConfFiles.skyrockGolem, 2007, Unit.class));
        ArrayList<Integer> skyrockGolemIntegerValues = new ArrayList<>();
        skyrockGolemIntegerValues.add(4);
        skyrockGolemIntegerValues.add(2);
        this.getValueMap().put(skyrockGolem, skyrockGolemIntegerValues);

        this.getConvertMap().put(swampEntangler, SwampEntangler.class);
        this.getUserOwnedMap().put(swampEntangler, false);
        this.getUnitMap().put(swampEntangler, BasicObjectBuilders.loadUnit(StaticConfFiles.swampEntangler, 2008, Unit.class));
        ArrayList<Integer> swampEntanglerIntegerValues = new ArrayList<>();
        swampEntanglerIntegerValues.add(0);
        swampEntanglerIntegerValues.add(3);
        this.getValueMap().put(swampEntangler, swampEntanglerIntegerValues);

        this.getConvertMap().put(silverguardKnight, SilverguardKnight.class);
        this.getUserOwnedMap().put(silverguardKnight, false);
        this.getUnitMap().put(silverguardKnight, BasicObjectBuilders.loadUnit(StaticConfFiles.silverguardKnight, 2009, Unit.class));
        ArrayList<Integer> silverguardKnightIntegerValues = new ArrayList<>();
        silverguardKnightIntegerValues.add(1);
        silverguardKnightIntegerValues.add(5);
        this.getValueMap().put(silverguardKnight, silverguardKnightIntegerValues);

        this.getConvertMap().put(saberspineTiger, SaberspineTiger.class);
        this.getUserOwnedMap().put(saberspineTiger, false);
        this.getUnitMap().put(saberspineTiger, BasicObjectBuilders.loadUnit(StaticConfFiles.saberspineTiger, 2010, Unit.class));
        ArrayList<Integer> saberspineTigerIntegerValues = new ArrayList<>();
        saberspineTigerIntegerValues.add(3);
        saberspineTigerIntegerValues.add(2);
        this.getValueMap().put(saberspineTiger, saberspineTigerIntegerValues);

        this.getConvertMap().put(beamshock, Beamshock.class);
//        this.getUserOwnedMap().put(beamshock, false);

        this.getConvertMap().put(youngFlamewing, YoungFlamewing.class);
        this.getUserOwnedMap().put(youngFlamewing, false);
        this.getUnitMap().put(youngFlamewing, BasicObjectBuilders.loadUnit(StaticConfFiles.youngFlamewing, 2011, Unit.class));
        ArrayList<Integer> youngFlamewingIntegerValues = new ArrayList<>();
        youngFlamewingIntegerValues.add(5);
        youngFlamewingIntegerValues.add(4);
        this.getValueMap().put(youngFlamewing, youngFlamewingIntegerValues);

        this.getConvertMap().put(silverguardSquire, SilverguardSquire.class);
        this.getUserOwnedMap().put(silverguardSquire, false);
        this.getUnitMap().put(silverguardSquire, BasicObjectBuilders.loadUnit(StaticConfFiles.silverguardSquire, 2012, Unit.class));
        ArrayList<Integer> silverguardSquireIntegerValues = new ArrayList<>();
        silverguardSquireIntegerValues.add(1);
        silverguardSquireIntegerValues.add(1);
        this.getValueMap().put(silverguardSquire, silverguardSquireIntegerValues);

        this.getConvertMap().put(ironcliffGuardian, IroncliffGuardian.class);
        this.getUserOwnedMap().put(ironcliffGuardian, false);
        this.getUnitMap().put(ironcliffGuardian, BasicObjectBuilders.loadUnit(StaticConfFiles.ironcliffGuardian, 2013, Unit.class));
        ArrayList<Integer> ironcliffGuardianIntegerValues = new ArrayList<>();
        ironcliffGuardianIntegerValues.add(3);
        ironcliffGuardianIntegerValues.add(10);
        this.getValueMap().put(ironcliffGuardian, ironcliffGuardianIntegerValues);

        this.getConvertMap().put(sundropElixir, SundropElixir.class);
//        this.getUserOwnedMap().put(sundropElixir, false);


        this.getConvertMap().put(truestrike, Truestrike.class);
//        this.getUserOwnedMap().put(truestrike, false);

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

// Used for the homogenisation of the formatting of strings entered
    private String camelCasifier(String cardName) {

            StringBuilder camelcasify = new StringBuilder();
            String[] separateWords = cardName.split(" ");

            camelcasify.append(separateWords[0].toLowerCase());

            for (int i = 1; i < separateWords.length; i++) {
                String word = separateWords[i];

                camelcasify.append(Character.toUpperCase(word.charAt(0)));
                camelcasify.append(word.substring(1).toLowerCase());
            }

        return camelcasify.toString();
    }
}