package PrismaMod.monsters.bossMonsters;

import PrismaMod.DefaultMod;
import PrismaMod.actions.MonsterActions.Gilgamesh.*;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.powers.monsters.DivinityPower;
import PrismaMod.powers.monsters.KeyOfKingsLawPower;
import basemod.animations.SpriterAnimation;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeMonsterAnimationPath;

public class Gilgamesh extends AbstractPrismaMonster {

    public static final String ID = DefaultMod.makeID("Gilgamesh");
    public static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Gilgamesh");
    public static final String NAME = monsterStrings.NAME;
    public static final String DIALOG[] = monsterStrings.DIALOG;
    public static final String MOVES[] = monsterStrings.MOVES;
    public static int HP = 500;
    public int ascension = AbstractDungeon.ascensionLevel;
    public boolean channel = false;
    public int turnNumber;
    public boolean firstMove;
    public int npCooldown;
    public GilgameshAnimation animHandler;

    public boolean temporaryAnimationShutoff = false;  //Used for temporary custom animation/sfx turn off for 1.2.0 build

    //Animation Variables
    public static SpriterAnimation[] animationArrayList = new SpriterAnimation[]{
            /*new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/GilgameshDAMAGED.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/GilgameshBIG_DAMAGED.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/GilgameshATTACK.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/GilgameshBIG_ATTACK.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/GilgameshCHANNEL.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/GilgameshBIG_CHANNEL.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/GilgameshIDLE.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/GilgameshIDLE_2.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTGilgameshDAMAGED.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTGilgameshBIG_DAMAGED.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTGilgameshATTACK.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTGilgameshBIG_ATTACK.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTGilgameshCHANNEL.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTGilgameshBIG_CHANNEL.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTGilgameshIDLE.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTGilgameshIDLE_2.scml"))*/
            new SpriterAnimation(makeMonsterAnimationPath("Gilgamesh" + "/Animations/GilgameshIDLE.scml")),
    };
    public float[] durationTable = {1.17F, 1.17F, 1.17F, 1.17F, 1.17F, 1.17F, 2.17F};

    public Gilgamesh(){
        super(NAME, ID, HP, 0.0F, 95.0F, 440.0F, 350.0F, (String) null);
        //for(String location : animLocations) {
        //    animationArrayList.add(new SpriterAnimation(location));
        //}
        animHandler = new GilgameshAnimation(animationArrayList, durationTable, this);
        animation = new SpriterAnimation(makeMonsterAnimationPath("Gilgamesh" + "/Animations/GilgameshIDLE.scml"));
        this.dialogX = -50.0F * Settings.scale;// 52
        this.dialogY = 50.0F * Settings.scale;// 53
        this.type = EnemyType.BOSS;
        this.firstMove = true;
        this.npCooldown = 10;
        this.turnNumber = 0;
        damage.add(new DamageInfo(this, 30, DamageInfo.DamageType.NORMAL));  //Basic Attack
        damage.add(new DamageInfo(this, 25, DamageInfo.DamageType.NORMAL));  //Treasury
        damage.add(new DamageInfo(this, 50, DamageInfo.DamageType.NORMAL));  //Enuma Elis
    }

    @Override
    public void takeTurn() {
        switch(this.nextMove){
            case 1: //Charisma (Strength up)
                this.useCharisma();
                break;
            case 2: //Golden Rule Heal 45 hp and apply 10 block
                this.useGoldenRule();
                break;
            case 3: //Treasury of Babylon (Multiple Attacks) 15 x 3
                this.useTreasuryOfBabylon();
                break;
            case 4: //Magic Resistance Gain 10 Plated armor
                this.useMagicResistance();
                break;
            case 5: //Basic Attack just basic attack
                this.useBasicAttack();
                break;
            case 6: //Divinity Every card the player plays now costs 1 more to cast for the turn
                this.useDivinity();
                break;
            case 7: //Enûma Eliš big nuke (6 hits) on 10th turn deal a 30 x 6
                this.useEnumaElis();
                break;
            case 8: //Key of Kings Law NP Cooldown reduced in half
                //this.useKeyOfKingsLaw();
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        turnNumber++;
        if(ascension >= 8 && firstMove){
            this.npCooldown = this.npCooldown/2;
            this.firstMove = false;
            this.setMove(MOVES[7], (byte)8, Intent.BUFF);
        } else {
            if (turnNumber % npCooldown == 0) {
                this.setMove(MOVES[6], (byte)7, Intent.ATTACK, damage.get(2).base, 6, true);
            } else {
                int rand = (int)(Math.random()*6+1);
                switch(rand) {
                    case 1:
                        this.setMove(MOVES[0], (byte) 1, Intent.BUFF);
                        break;
                    case 2:
                        this.setMove(MOVES[1], (byte) 2, Intent.DEFEND_BUFF);
                        break;
                    case 3:
                        this.setMove(MOVES[2], (byte) 3, Intent.ATTACK, damage.get(1).base, 3, true);
                        break;
                    case 4:
                        this.setMove(MOVES[3], (byte) 4, Intent.BUFF);
                        break;
                    case 5:
                        this.setMove((byte) 5, Intent.ATTACK, damage.get(0).base);
                        break;
                    case 6:
                        this.setMove(MOVES[5], (byte) 6, Intent.DEBUFF);
                        break;
                }
            }
        }

    }
    //==========================================
    //========== ENEMY MOVE FUNCTIONS ==========
    //==========================================

    //NONE OF THE ACTIONS ARE RIGHT EXCEPT THE BASIC ATTACK MIGHT BE CORRECT CHANGE ALL OF THEM LATER TIME TO DO THE ANIMATIONS WOOOO
    private void useTreasuryOfBabylon(){
        if(enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new TreasuryOfBabylon(this, AbstractDungeon.player, damage.get(1), 3));
        } else {
            for(int i = 0; i < 3; i++)  //Hit 5 times
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, damage.get(1)));
        }
    }

    private void useGoldenRule(){
        if(enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new GoldenRule(this, 15, 45));
        } else {
            AbstractDungeon.actionManager.addToTop(new HealAction(this, this, 45));
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(this, this, 15));
        }
    }

    public void useCharisma() {
        if (!enablePlaceholder && temporaryAnimationShutoff) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new StrengthPower(this, 4)));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new StrengthPower(this, 4)));
        }
    }

    private void useMagicResistance(){
        if(enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new MagicResistance(this, 10));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 10)));
        }
    }

    private void useEnumaElis(){
        if(enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new EnumaElis(this, AbstractDungeon.player, damage.get(2),6));
        } else {
            for(int i = 0; i < 6; i++)  //Hit 5 times
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, damage.get(2)));
        }
    }

    private void useBasicAttack(){
        if(enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new BasicAttack(this, AbstractDungeon.player, damage.get(0)));
        } else {
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, damage.get(0)));
        }
    }

    private void useKeyOfKingsLaw(){
        if(enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new KeyOfKingsLaw(this));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new KeyOfKingsLawPower(this, this, 1)));
        }
    }

    private void useDivinity(){
        if(enablePlaceholder && temporaryAnimationShutoff){
            AbstractDungeon.actionManager.addToTop(new Divinity(this, AbstractDungeon.player));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DivinityPower(AbstractDungeon.player, AbstractDungeon.player, 1)));
        }
    }

    /*
    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (!enablePlaceholder && temporaryAnimationShutoff) {
            if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
                if (info.output - this.currentBlock < 15) {
                    animHandler.setAnimation(MonsterAnimationEnums.DAMAGED);
                } else {
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
    //========== GILGAMESH SPECIFIC ANIMATIONS ==========
    //===============================================

    public class GilgameshAnimation extends PrismaAnimation {
        private boolean NPIdle = false;

        public GilgameshAnimation(SpriterAnimation[] animationArrayList, float[] durationTable, Gilgamesh owner) {
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

        public GilgameshAnimation getHandler(){
            return animHandler;
        }
    }
}
