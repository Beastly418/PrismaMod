package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Orbs.RapidFireAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

//Rapid fire but half the number of hits plus exhausts only 1 cost
public class IllyaMinorRapidFire extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IllyaMinorRapidFire.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/MinorRapidFire.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UP_DAMAGE = 5;
    private static final int DRAW = 1;


    public IllyaMinorRapidFire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        //this.exhaust = true;
        //this.baseDamage = this.damage = 4;//Illya.ORB_SLOTS;
        //this.magicNumber = this.baseMagicNumber = Illya.ORB_SLOTS/2;
        //this.baseDamage = damage = baseMagicNumber = magicNumber = AbstractDungeon.player.orbs.size();
        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = DRAW;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            /*int numOrbs = p.maxOrbs/2;
            if (!upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, damage)));//, AbstractGameAction.AttackEffect.NONE));
                }
            } else if (upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, damage)));//, AbstractGameAction.AttackEffect.NONE));
                }
            }
             */
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)); // The animation the damage action uses to hit.
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.magicNumber));

        } else {
            /*
            int numOrbs = p.maxOrbs/2;
            if (!upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, damage)));//, AbstractGameAction.AttackEffect.NONE));
                }
            } else if (upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, damage)));//, AbstractGameAction.AttackEffect.NONE));
                }
            }
             */
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)); // The animation the damage action uses to hit.
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.magicNumber));
        }
    }
/*
    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        this.magicNumber = this.baseMagicNumber = AbstractDungeon.player.maxOrbs/2;
    }


    @Override
    public void triggerWhenDrawn() {
        this.magicNumber = this.baseMagicNumber = AbstractDungeon.player.maxOrbs/2;
    }

 */


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UP_DAMAGE);
            initializeDescription();
        }
    }


    @Override
    public void update() {
        super.update();
        //if(AbstractDungeon.player != null)
        //    this.magicNumber = this.baseMagicNumber = AbstractDungeon.player.maxOrbs/2;
    }
}
