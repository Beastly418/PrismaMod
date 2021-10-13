package PrismaMod.monsters.bossMonsters;

import PrismaMod.DefaultMod;
import PrismaMod.actions.MonsterActions.Rider.*;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.powers.monsters.BridleOfChivalryPower;
import PrismaMod.powers.monsters.ShapelessIslandPower;
import basemod.animations.SpriterAnimation;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeMonsterAnimationPath;

public class Rider extends AbstractPrismaMonster {

    public static final String ID = DefaultMod.makeID("Rider");
    public static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Rider");
    public static final String NAME = monsterStrings.NAME;
    public static final String DIALOG[] = monsterStrings.DIALOG;
    public static final String MOVES[] = monsterStrings.MOVES;
    public static int HP = 400;
    public int ascension = AbstractDungeon.ascensionLevel;
    public RiderAnimation animHandler;
    public boolean NobelPhantasm;
    public boolean firstMove;

    public boolean temporaryAnimationShutoff = false;  //Used for temporary custom animation/sfx turn off for 1.2.0 build

    //Animation Variables
    public static SpriterAnimation[] animationArrayList = new SpriterAnimation[]{
            /*new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/RiderDAMAGED.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/RiderBIG_DAMAGED.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/RiderATTACK.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/RiderBIG_ATTACK.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/RiderCHANNEL.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/RiderBIG_CHANNEL.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/RiderIDLE.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/RiderIDLE_2.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTRiderDAMAGED.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTRiderBIG_DAMAGED.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTRiderATTACK.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTRiderBIG_ATTACK.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTRiderCHANNEL.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTRiderBIG_CHANNEL.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTRiderIDLE.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTRiderIDLE_2.scml"))*/
            new SpriterAnimation(makeMonsterAnimationPath("Rider" + "/Animations/RiderIDLE.scml")),
    };
    public float[] durationTable = {1.17F, 1.17F, 1.17F, 1.17F, 1.17F, 1.17F, 2.17F};

    public Rider(){
        super(NAME, ID, HP, 0.0F, 95.0F, 440.0F, 350.0F, (String) null);
        //for(String location : animLocations) {
        //    animationArrayList.add(new SpriterAnimation(location));
        //}
        animHandler = new RiderAnimation(animationArrayList, durationTable, this);
        animation = new SpriterAnimation(makeMonsterAnimationPath("Rider" + "/Animations/RiderIDLE.scml"));
        this.dialogX = -50.0F * Settings.scale;// 52
        this.dialogY = 50.0F * Settings.scale;// 53
        this.type = EnemyType.BOSS;
        this.NobelPhantasm = false;
        this.firstMove = true;
        damage.add(new DamageInfo(this, 20, DamageInfo.DamageType.NORMAL));  //Basic Attack
        damage.add(new DamageInfo(this, 25, DamageInfo.DamageType.NORMAL));  //Unicorn Attack
        damage.add(new DamageInfo(this, 30, DamageInfo.DamageType.NORMAL));  //Heretical Mount
    }

    @Override
    public void takeTurn() {
        switch(this.nextMove){
            case 1: //Mystic Eyes Apply 2 Frail, Weak, and Vulnerable
                this.useMysticEyes();
                break;
            case 2: //Monstrous Strength (Add 2 Strength)
                this.useMonstrousStrength();
                break;
            case 3: //Basic Attack
                this.useBasicAttack();
                break;
            case 4: //Get on the unicorn (Trigger when she reaches 50%)
                this.useBridleOfChivalry();
                //this.animHandler.setNPIdle(true);   //Set it so that she is on the pegasus
                break;
            case 5: //Big unicorn attack
                this.useUnicornAttack();
                break;
            case 6: //Shapeless Island Give the player random curse every 4 turns
                this.useShapelessIsland();
                break;
            case 7: //Turn to stone Apply 3 Frail, Weak, and Vulnerable; Apply -1 Strength and -1 Dex
                this.useCursedEyes();
                break;
            case 8: //Heretical Mount (Give 15 Block and deal damage) gain 2 Strength
                this.useHereticalMount();
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        //setMove((byte)1, Intent.ATTACK); << This is how you set up this.nextMove
        //Going to go buff then attack
        //On the 5th turn start the channel for Nobel Phantasm
        if(firstMove && ascension >= 8){
            //Use shapeless Island
            this.firstMove = false;
            this.setMove(MOVES[5], (byte)6, Intent.BUFF);
        } else {
            if(!NobelPhantasm && this.currentHealth <= (this.maxHealth/2)){
                //Use bridle of Chivalry
                NobelPhantasm = true;
                this.setMove(MOVES[3], (byte)4, Intent.DEBUFF);
            } else if (!NobelPhantasm) {    //Basic attacks
                int rand = (int) (Math.random()*3+1);
                switch(rand){
                    case 1:
                        //use Mystic eyes
                        this.setMove(MOVES[0], (byte)1, Intent.DEBUFF);
                        break;
                    case 2:
                        //use Monstrous Strength
                        this.setMove(MOVES[1], (byte)2, Intent.BUFF);
                        break;
                    case 3:
                        //use Basic Attack
                        this.setMove((byte)3, Intent.ATTACK, damage.get(0).base);
                        break;
                }
            } else {                        //Noble Phantasm Attacks
                int rand = (int) (Math.random()*3+1);
                switch(rand){
                    case 1:
                        //Pegasus attack
                        this.setMove(MOVES[4], (byte)5, Intent.ATTACK, damage.get(1).base, 2, true);
                        break;
                    case 2:
                        //Cursed Eyes
                        this.setMove(MOVES[6], (byte)7, Intent.DEBUFF);
                        break;
                    case 3:
                        //Heretical Mount
                        this.setMove(MOVES[7], (byte)8, Intent.ATTACK_BUFF, damage.get(2).base);
                        break;
                }
            }
        }

    }
    //==========================================
    //========== ENEMY MOVE FUNCTIONS ==========
    //==========================================

    //NONE OF THE ACTIONS ARE RIGHT EXCEPT THE BASIC ATTACK MIGHT BE CORRECT CHANGE ALL OF THEM LATER TIME TO DO THE ANIMATIONS WOOOO
    private void useMysticEyes(){
        if(!enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new MysticEyes(this, AbstractDungeon.player, 2,2,2));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, 2, false)));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 2, false)));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, 2, false)));
        }
    }

    private void useMonstrousStrength(){
        if(!enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new StrengthPower(this, 4)));
            //Maybe put a dummy action here for the animation/sfx
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new StrengthPower(this, 4)));
        }
    }

    private void useShapelessIsland(){
        if(!enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new ShapelessIsland(this, AbstractDungeon.player));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ShapelessIslandPower(AbstractDungeon.player, AbstractDungeon.player, 1)));
        }
    }

    private void useBridleOfChivalry(){
        if(!enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new BridleOfChivalry(this));//), AbstractDungeon.player, damage.get(0)));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new BridleOfChivalryPower(this, this, 1)));
        }
    }

    private void useUnicornAttack(){
        if(!enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new UnicornAttack(this, AbstractDungeon.player, damage.get(1), 2));
        } else {
            for(int i = 0; i < 2; i++)
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, damage.get(1)));
        }
    }

    private void useBasicAttack(){
        if(!enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new BasicAttack(this, AbstractDungeon.player, damage.get(0)));
        } else {
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, damage.get(0)));
        }
    }

    private void useCursedEyes(){
        if(!enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new CursedEyes(this, AbstractDungeon.player, 3,3,3,-1,-1));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, 3, false)));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 3, false)));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, 3, false)));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, -1)));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, -1)));
        }
    }

    private void useHereticalMount(){
        if(!enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new HereticalMount(this, AbstractDungeon.player, damage.get(2), 20, 2));
        } else {
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, damage.get(2)));
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(this, this, 20));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new StrengthPower(this, 2)));
        }
    }

    /*
    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (!enablePlaceholder && temporaryAnimationShutoff) {
            if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
                if (info.output - this.currentBlock < 15) {
                    if (!NobelPhantasm)
                        animHandler.setAnimation(MonsterAnimationEnums.DAMAGED);
                } else {
                    if (!NobelPhantasm)
                        animHandler.setAnimation(MonsterAnimationEnums.BIG_DAMAGED);
                }
            }
        }
    }*/

    @Override
    public void update() {
        super.update();
        animHandler.update();
    }

    //===============================================
    //========== RIDER SPECIFIC ANIMATIONS ==========
    //===============================================

    public class RiderAnimation extends PrismaAnimation {
        private boolean NPIdle = false;

        public RiderAnimation(SpriterAnimation[] animationArrayList, float[] durationTable, Rider owner) {
            super(animationArrayList, durationTable, owner);
            this.animationArrayList = animationArrayList;
            this.durationTable = durationTable;
            this.owner = owner;
        }

        @Override
        public SpriterAnimation getAnimation(MonsterAnimationEnums animEnum) {
            if (!temporaryAnimationShutoff){
                duration = durationTable[6];
                return animationArrayList[0];
            } else {
                switch (animEnum) {
                    case DAMAGED:
                        duration = durationTable[1];
                        if (Settings.FAST_MODE) {
                            duration = durationTable[1] / 2;
                            return animationArrayList[8];
                        }
                        return animationArrayList[0];
                    case BIG_DAMAGED:
                        duration = durationTable[1];
                        if (Settings.FAST_MODE) {
                            duration = durationTable[1] / 2;
                            return animationArrayList[9];
                        }
                        return animationArrayList[1];
                    case ATTACK:
                        duration = durationTable[1];
                        if (Settings.FAST_MODE) {
                            duration = durationTable[1] / 2;
                            return animationArrayList[10];
                        }
                        return animationArrayList[2];
                    case BIG_ATTACK:
                        duration = durationTable[1];
                        if (Settings.FAST_MODE) {
                            duration = durationTable[1] / 2;
                            return animationArrayList[11];
                        }
                        return animationArrayList[3];
                    case CHANNEL:
                        duration = durationTable[1];
                        if (Settings.FAST_MODE) {
                            duration = durationTable[1] / 2;
                            return animationArrayList[12];
                        }
                        return animationArrayList[4];
                    case BIG_CHANNEL:
                        duration = durationTable[1];
                        if (Settings.FAST_MODE) {
                            duration = durationTable[1] / 2;
                            return animationArrayList[13];
                        }
                        return animationArrayList[5];
                    case IDLE:
                        duration = durationTable[6];
                        if (Settings.FAST_MODE) {
                            duration = durationTable[6];
                            return animationArrayList[14];
                        }
                        return animationArrayList[6];
                    default:    //Idle 2
                        duration = durationTable[6];
                        if (Settings.FAST_MODE) {
                            duration = durationTable[6];
                            return animationArrayList[15];
                        }
                        return animationArrayList[7];
                }
            }
        }

        public void setNPIdle(boolean NP) {
            this.NPIdle = NP;
        }

        @Override
        public void resetAnimation() {
            if (!NPIdle) {
                setAnimation(MonsterAnimationEnums.IDLE);
            } else {
                setAnimation(MonsterAnimationEnums.IDLE_2);
            }
        }

        public RiderAnimation getHandler(){
            return animHandler;
        }
    }
}
