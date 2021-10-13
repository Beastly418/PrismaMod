package PrismaMod.monsters.bossMonsters;

import PrismaMod.DefaultMod;
import PrismaMod.actions.MonsterActions.SaberAlter.*;
import PrismaMod.monsters.AbstractPrismaMonster;
import PrismaMod.powers.monsters.InstinctPower;
import PrismaMod.powers.monsters.ManaBurstPower;
import PrismaMod.powers.monsters.MemoriesOfTheDragonPower;
import basemod.animations.SpriterAnimation;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeMonsterAnimationPath;

public class SaberAlter extends AbstractPrismaMonster {

    private static final Logger logger = LogManager.getLogger(SaberAlter.class.getName());

    public static final String ID = DefaultMod.makeID("SaberAlter");
    public static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SaberAlter");
    public static final String NAME = monsterStrings.NAME;
    public static final String DIALOG[] = monsterStrings.DIALOG;
    public static final String MOVES[] = monsterStrings.MOVES;
    public static int HP = 450;
    public int ascension = AbstractDungeon.ascensionLevel;
    public boolean buffRound;// = true;
    public boolean channel;// = false;
    public boolean nobelPhantasm;
    public int turnNumber;
    public boolean firstMove;
    public SaberAlterAnimation animHandler;

    private boolean temporaryAnimationShutoff = false;  //Used for temporary custom animation/sfx turn off for 1.2.0 build

    //Animation Variables

    public static SpriterAnimation[] animationArrayList = new SpriterAnimation[]{
            /*new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/SaberAlterDAMAGED.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/SaberAlterBIG_DAMAGED.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/SaberAlterATTACK.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/SaberAlterBIG_ATTACK.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/SaberAlterCHANNEL.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/SaberAlterBIG_CHANNEL.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/SaberAlterIdle.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/SaberAlterIDLE_2.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTSaberAlterDAMAGED.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTSaberAlterBIG_DAMAGED.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTSaberAlterATTACK.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTSaberAlterBIG_ATTACK.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTSaberAlterCHANNEL.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTSaberAlterBIG_CHANNEL.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTSaberAlterIdle.scml")),
            new SpriterAnimation(makeMonsterAnimationPath(NAME + "/Animations/FASTSaberAlterIDLE_2.scml"))*/
            new SpriterAnimation(makeMonsterAnimationPath("SaberAlter" + "/Animations/SaberAlterIdle.scml")),
    };

    public float[] durationTable = {1.17F, 1.17F, 1.17F, 1.17F, 1.17F, 1.17F, 2.167F};

    public SaberAlter() {
        super(NAME, ID, HP, 0.0F, 95.0F, 440.0F, 350.0F, (String) null);
        //for(String location : animLocations) {
        //    animationArrayList.add(new SpriterAnimation(location));
        //}
        animHandler = new SaberAlterAnimation(animationArrayList, durationTable, this);
        animation = new SpriterAnimation(makeMonsterAnimationPath("SaberAlter" + "/Animations/SaberAlterIdle.scml"));
        this.dialogX = -50.0F * Settings.scale;// 52
        this.dialogY = 50.0F * Settings.scale;// 53
        this.type = EnemyType.BOSS;
        turnNumber = 0;
        buffRound = true;
        channel = false;
        firstMove = true;
        damage.add(new DamageInfo(this, 20, DamageInfo.DamageType.NORMAL));  //Basic Attack
        damage.add(new DamageInfo(this, 11, DamageInfo.DamageType.NORMAL));  //NP
    }

    @Override
    public void takeTurn() {
        logger.info(this.nextMove);
        switch (this.nextMove) {
            case 1: //Mana Burst Next turn double hit
                this.useManaBurst();
                break;
            case 2: //Instict 3x damage
                this.useInstinct();
                break;
            case 3: //Charisma Strength up
                this.useCharisma();
                break;
            case 4: //Magic Resistance Gain Plated Armor
                this.useMagicResistance();
                break;
            case 5: //Excalibur Morgan big nuke (3 hits)
                this.useExcaliburMorgan();
                this.turnNumber = 0;
                //this.animHandler.setNPIdle(false);
                break;
            case 6: //Basic Attack just basic attack
                this.useBasicAttack();
                break;
            case 7: //Memories of the Dragon (Skill unlocked in later ascensions) At start ramp up stats
                this.useMemoriesOfTheDragon();
                break;
            case 8: //Channel just a charge turn so it wont do anything but it needs to say stuffs
                this.useChannel();
                //this.animHandler.setNPIdle(true);
        }
        //this.resetBuffs();

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int i) {
        //setMove((byte)1, Intent.ATTACK); << This is how you set up this.nextMove
        //Going to go buff then attack
        //On the 5th turn start the channel for Nobel Phantasm

        this.turnNumber++;
        //NEED TO GO IN AND MAKE THE SET MOVES DIFFERENT SINCE ITS AN OVERLOADED METHOD!!!!!!
        if (this.firstMove && ascension >= 8) {
            this.firstMove = false;
            this.buffRound = false;
            this.setMove(MOVES[6],(byte)7, Intent.BUFF); //Memories of a Dragon
        } else if (turnNumber%5 == 0) {
            this.channel = true;
            this.setMove(MOVES[7], (byte)8, Intent.UNKNOWN); //Channel
        } else {
            if (this.channel) {
                this.channel = false;
                this.buffRound = true;
                this.setMove(MOVES[4], (byte)5, Intent.ATTACK, damage.get(1).base, 5, true);//Only cast the NP if it has already channeled
            } else if (!this.buffRound) {
                this.buffRound = true;
                boolean manaburst = false;
                boolean instinct = false;
                for (AbstractPower pow : this.powers) {
                    if (pow instanceof ManaBurstPower) {
                        manaburst = true;
                        break;
                    } else if (pow instanceof InstinctPower){
                        instinct = true;
                        break;
                    }
                }
                if (manaburst) {
                    this.setMove((byte) 6, Intent.ATTACK, damage.get(0).base, 2, true);
                } else if (instinct) {
                    this.setMove((byte) 6, Intent.ATTACK, damage.get(0).base);
                } else {
                    this.setMove((byte) 6, Intent.ATTACK, damage.get(0).base);    //Basic attack
                }
            } else {
                int rand = (int) ((Math.random() * 4) + 1);
                this.buffRound = false;
                this.setMove(MOVES[rand-1], (byte) rand, Intent.BUFF);
            }
        }
    }

    //==========================================
    //========== ENEMY MOVE FUNCTIONS ==========
    //==========================================

    //NONE OF THE ACTIONS ARE RIGHT EXCEPT THE BASIC ATTACK MIGHT BE CORRECT CHANGE ALL OF THEM LATER TIME TO DO THE ANIMATIONS WOOOO
    public void useManaBurst() {
        if (!enablePlaceholder && temporaryAnimationShutoff) {
            AbstractDungeon.actionManager.addToTop(new ManaBurst(this));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new ManaBurstPower(this, this, 1)));
        }
    }

    public void useInstinct() {
        if (!enablePlaceholder && temporaryAnimationShutoff) {
            AbstractDungeon.actionManager.addToTop(new Instinct(this));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new InstinctPower(this, this, 1)));
        }
    }

    public void useCharisma() {
        if (!enablePlaceholder && temporaryAnimationShutoff) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new StrengthPower(this, 4)));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new StrengthPower(this, 4)));
        }
    }

    public void useMagicResistance() {
        if (!enablePlaceholder && temporaryAnimationShutoff) {
            AbstractDungeon.actionManager.addToTop(new MagicResistance(this));
        } else {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(this, this, 15));
        }
    }

    public void useExcaliburMorgan() {
        if (!enablePlaceholder && temporaryAnimationShutoff) {
            AbstractDungeon.actionManager.addToTop(new TalkAction(this, DIALOG[0]));
            AbstractDungeon.actionManager.addToTop(new ExcaliburMorgan(this, AbstractDungeon.player, damage.get(1)));
        } else {
            for(int i = 0; i < 5; i++)  //Hit 5 times
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, damage.get(1)));
        }
    }

    public void useBasicAttack() {
        if (!enablePlaceholder && temporaryAnimationShutoff) {
            boolean tmp = false;
            boolean instinct = false;
            for (AbstractPower pow : this.powers) {
                if (pow instanceof ManaBurstPower) {
                    tmp = true;
                    break;
                } else if (pow instanceof InstinctPower){
                    instinct = true;
                    break;
                }
            }
            if (tmp) {
                AbstractDungeon.actionManager.addToTop(new BasicAttack(this, AbstractDungeon.player, damage.get(0), 2));
            } else if (instinct) {
                AbstractDungeon.actionManager.addToTop(new BasicAttack(this, AbstractDungeon.player, new DamageInfo(damage.get(0).owner, damage.get(0).base*3, damage.get(0).type), 1));
            } else {
                AbstractDungeon.actionManager.addToTop(new BasicAttack(this, AbstractDungeon.player, damage.get(0), 1));
            }
        } else {
            boolean tmp = false;
            boolean instinct = false;
            for (AbstractPower pow : this.powers) {
                if (pow instanceof ManaBurstPower) {
                    tmp = true;
                    break;
                } else if (pow instanceof InstinctPower){
                    instinct = true;
                    break;
                }
            }
            if (tmp) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, damage.get(0)));
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, damage.get(0)));
            } else if (instinct) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(damage.get(0).owner, damage.get(0).base*3, damage.get(0).type)));
            } else {
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, damage.get(0)));
            }
        }
    }

    public void useMemoriesOfTheDragon() {
        if (!enablePlaceholder && temporaryAnimationShutoff) {
            AbstractDungeon.actionManager.addToTop(new MemoriesOfTheDragon(this));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new MemoriesOfTheDragonPower(this, this, 1)));
        }
    }

    public void useChannel() {
        if (!enablePlaceholder && temporaryAnimationShutoff) {
            AbstractDungeon.actionManager.addToTop(new Channel(this));
        } else {
            //DO NOTHING??
        }
    }

    /*@Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (!enablePlaceholder && temporaryAnimationShutoff) {
            if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
                if (info.output - this.currentBlock < 15) {
                    if (!channel)
                    animHandler.setAnimation(MonsterAnimationEnums.DAMAGED);
                } else {
                    if (!channel)
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

    public class SaberAlterAnimation extends PrismaAnimation {
        private boolean NPIdle = false;

        public SaberAlterAnimation(SpriterAnimation[] animationArrayList, float[] durationTable, SaberAlter owner) {
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

        public SaberAlterAnimation getHandler(){
            return animHandler;
        }
    }
}
