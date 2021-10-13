package PrismaMod.powers.Chloe;

import PrismaMod.DefaultMod;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.lang.reflect.Field;

public class ChloeStunPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("ChloeStunPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Reflex_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Reflex_power32.png");

    private byte moveByte;
    private AbstractMonster.Intent moveIntent;
    private EnemyMoveInfo move;

    public ChloeStunPower(AbstractMonster owner) {
        this(owner, 1);// 29
    }// 30

    public ChloeStunPower(AbstractMonster owner, int amount) {
        this.name = NAME;// 34
        this.ID = "stslib:Stunned";// 35
        this.owner = owner;// 36
        this.amount = amount;// 37
        this.type = PowerType.DEBUFF;// 38
        this.isTurnBased = true;// 39
        this.updateDescription();// 40
        this.img = ImageMaster.loadImage("images/stslib/powers/32/stun.png");// 41
    }// 42

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }


    public void atEndOfRound() {
        if (this.amount <= 0) {// 58
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));// 59
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));// 61
        }

    }// 63

    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {// 69
            public void update() {
                if (ChloeStunPower.this.owner instanceof AbstractMonster) {// 74
                    ChloeStunPower.this.moveByte = ((AbstractMonster)ChloeStunPower.this.owner).nextMove;// 75
                    ChloeStunPower.this.moveIntent = ((AbstractMonster)ChloeStunPower.this.owner).intent;// 76

                    try {
                        Field f = AbstractMonster.class.getDeclaredField("move");// 78
                        f.setAccessible(true);// 79
                        ChloeStunPower.this.move = (EnemyMoveInfo)f.get(ChloeStunPower.this.owner);// 80
                        ChloeStunPower.this.move.intent = AbstractMonster.Intent.STUN;// 81
                        ((AbstractMonster)ChloeStunPower.this.owner).createIntent();// 82
                    } catch (NoSuchFieldException | IllegalAccessException var2) {// 83
                        var2.printStackTrace();// 84
                    }
                }

                this.isDone = true;// 87
            }// 88
        });
    }// 90

    public void onRemove() {
        if (this.owner instanceof AbstractMonster) {// 95
            AbstractMonster m = (AbstractMonster)this.owner;// 96
            if (this.move != null) {// 97
                m.setMove(this.moveByte, this.moveIntent, this.move.baseDamage, this.move.multiplier, this.move.isMultiDamage);// 98
            } else {
                m.setMove(this.moveByte, this.moveIntent);// 100
            }

            m.createIntent();// 102
            m.applyPowers();// 103
        }

    }// 105
}
