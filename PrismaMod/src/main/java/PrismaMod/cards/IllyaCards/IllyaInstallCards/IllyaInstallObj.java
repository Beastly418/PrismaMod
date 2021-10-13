package PrismaMod.cards.IllyaCards.IllyaInstallCards;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class IllyaInstallObj {

    private String name;

    private int numOrbsInc;
    private int evokeNumOrbs;
    private int skillCostDec;
    private int numRandomCards;
    private int numEvades;
    private int numPoisonPerAttack;
    private int numShivs;   //Shivs per turn
    private boolean AssassinDamage; //Half as normal damage half as hp damage
    private int numAssassinOrbs;
    private int aoeDamagePerTurn;
    private boolean halfnonBossDamage; //Incoming damage
    private int strengthInc;
    private boolean dealDouble;
    private boolean takeDouble;
    private int energyInc;
    private boolean betterOrbs;
    private int evokeCostDec;
    private boolean gainRandomOrb;
    private boolean doubleOrbEvokes;
    private boolean addPoisonFromOrbs;
    private int numApplyWeak;
    private int numApplyFrail;
    private int numApplyVulnerable;
    private int numApplyStrength;
    private int numColorless;
    private boolean doubleOrbPassives;
    private int numDex;
    private int moreDamagetononBoss;
    private boolean invulnerable;
    private int numBeneficial;
    private int dealMoreDamageToBosses;
    private int healPerTurn;
    private int blockPerTurn;
    public AbstractCard card;

    public IllyaInstallObj(String className, AbstractInstallCard card) {
        this(className);
        this.card = card;
    }

    public IllyaInstallObj(String className) {

        name = className;

        //Choose what type of Install you want and run the function to set all of its variables to the install values
        if (name.equals("AlterEgo")){
            makeAlterEgo();

        }else if (name.equals(("Archer"))){
            makeArcher();

        }else if (name.equals(("Assassin"))){
            makeAssassin();

        }else if (name.equals(("Avenger"))){
            makeAvenger();

        }else if (name.equals(("Berserker"))){
            makeBerserker();

        }else if (name.equals(("Caster"))){
            makeCaster();

        }else if (name.equals(("Foreigner"))){
            makeForeigner();

        }else if (name.equals(("Lancer"))){
            makeLancer();

        }else if (name.equals(("MoonCancer"))){
            makeMoonCancer();

        }else if (name.equals(("Rider"))){
            makeRider();

        }else if (name.equals(("Ruler"))){
            makeRuler();

        }else if (name.equals(("Saber"))){
            makeSaber();

        }else if (name.equals(("Shielder"))){
            makeShielder();

        }else throw new IllegalArgumentException("You need a real class dummy!");
    }

    public void makeAlterEgo(){
        numOrbsInc = 1;
        evokeNumOrbs = 1;
        skillCostDec = 1;
    }
    public void makeArcher(){
        numRandomCards = 1;
        strengthInc = 1;
    }
    public void makeAssassin(){
        numEvades = 1;
        numPoisonPerAttack = 2;
        numShivs = 1;
        AssassinDamage = true;
        numAssassinOrbs = 1;
    }
    public void makeAvenger(){
        halfnonBossDamage = true;
        aoeDamagePerTurn = 5;
        strengthInc = 1;
    }
    public void makeBerserker(){
        dealDouble = true;
        takeDouble = true;
    }
    public void makeCaster(){
        energyInc = 1;
        betterOrbs = true;
        evokeCostDec = 1;
        numOrbsInc = 1;
        gainRandomOrb = true;
    }
    public void makeForeigner(){
        doubleOrbEvokes = true;
        addPoisonFromOrbs = true;
    }
    public void makeLancer(){
        numApplyFrail = 1;
        numApplyStrength = 0;
        numApplyVulnerable = 1;
        numApplyWeak = 1;
    }
    public void makeMoonCancer(){
        numColorless = 1;
        doubleOrbPassives = true;
    }
    public void makeRider(){
        numDex = 1;
        moreDamagetononBoss = 5;
    }
    public void makeRuler(){
        int rand = (int) Math.random()*10;
        if (rand <= 1) {
            invulnerable = true;
        }
        numBeneficial = 1;
    }
    public void makeSaber(){
        strengthInc = 1;
        dealMoreDamageToBosses = 5;
        healPerTurn = 1;

    }
    public void makeShielder(){
        blockPerTurn = 15;
        healPerTurn = 1;
    }

    public String toString(){
        return name;
    }


    //================================================================================
    //***************************          GETTERS         ***************************
    //================================================================================


    public AbstractCard getCard() {
        return card;
    }

    public int getNumOrbsInc() {
        return numOrbsInc;
    }

    public int getEvokeNumOrbs() {
        return evokeNumOrbs;
    }

    public int getSkillCostDec() {
        return skillCostDec;
    }

    public int getNumRandomCards() {
        return numRandomCards;
    }

    public int getNumEvades() {
        return numEvades;
    }

    public int getAoeDamagePerTurn() {
        return aoeDamagePerTurn;
    }

    public int getEnergyInc() {
        return energyInc;
    }

    public int getEvokeCostDec() {
        return evokeCostDec;
    }

    public int getNumAssassinOrbs() {
        return numAssassinOrbs;
    }

    public int getNumPoisonPerAttack() {
        return numPoisonPerAttack;
    }

    public int getNumShivs() {
        return numShivs;
    }

    public int getStrengthInc() {
        return strengthInc;
    }

    public int getNumDex() {
        return numDex;
    }

    public int getBlockPerTurn() {
        return blockPerTurn;
    }

    public int getMoreDamagetononBoss() {
        return moreDamagetononBoss;
    }

    public int getNumApplyFrail() {
        return numApplyFrail;
    }

    public int getDealMoreDamageToBosses() {
        return dealMoreDamageToBosses;
    }

    public int getNumApplyWeak() {
        return numApplyWeak;
    }

    public int getNumApplyVulnerable() {
        return numApplyVulnerable;
    }

    public int getHealPerTurn() {
        return healPerTurn;
    }

    public int getNumApplyStrength() {
        return numApplyStrength;
    }

    public int getNumBeneficial() {
        return numBeneficial;
    }

    public int getNumColorless() {
        return numColorless;
    }

    public boolean isAssassinDamage() {
        return AssassinDamage;
    }

    public boolean isAddPoisonFromOrbs() {
        return addPoisonFromOrbs;
    }

    public boolean isBetterOrbs() {
        return betterOrbs;
    }

    public boolean isDealDouble() {
        return dealDouble;
    }

    public boolean isDoubleOrbEvokes() {
        return doubleOrbEvokes;
    }

    public boolean isGainRandomOrb() {
        return gainRandomOrb;
    }

    public boolean isHalfnonBossDamage() {
        return halfnonBossDamage;
    }

    public boolean isTakeDouble() {
        return takeDouble;
    }

    public boolean isDoubleOrbPassives() {
        return doubleOrbPassives;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public String getName() {
        return name;
    }
}
