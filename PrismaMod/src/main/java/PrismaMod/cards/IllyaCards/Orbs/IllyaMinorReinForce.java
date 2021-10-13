package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Orbs.ReinForceAction;
import PrismaMod.characters.Illya;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

//rapid fire but half of the trigger times plus exhausts only 1 cost
public class IllyaMinorReinForce extends CustomCard {

    public static final String ID = DefaultMod.makeID(IllyaMinorReinForce.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/MinorReinForce.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 1;
    private static final int DRAW = 1;
    private static final int BLOCK = 5;
    private static final int UP_BLOCK = 5;

    public IllyaMinorReinForce() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        //this.exhaust = true;
        /*magicNumber = baseMagicNumber = Illya.ORB_SLOTS;
        block = baseBlock = 4;//Illya.ORB_SLOTS;
        */
        this.baseBlock = this.block = BLOCK;
        this.baseMagicNumber = this.magicNumber = DRAW;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            /*int numOrbs = p.maxOrbs/2;
            if (!upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, block));
                }
            } else if (upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, block));
                }
            }*/
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.magicNumber));
        } else {
            //Original card effect
            /*int numOrbs = p.maxOrbs/2;
            if (!upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, block));
                }
            } else if (upgraded) {
                for (int i = 0; i < numOrbs; i++) {
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, block));
                }
            }*/
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.magicNumber));
        }
    }
/*
    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        this.magicNumber = this.baseMagicNumber = AbstractDungeon.player.maxOrbs;
    }

    @Override
    public void triggerWhenDrawn() {
        this.magicNumber = this.baseMagicNumber = AbstractDungeon.player.maxOrbs;
    }

 */

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UP_BLOCK);
            //upgradedMagicNumber = true;
            //block = block*2;
            //baseBlock = baseBlock*2;
            //upgradedBlock = true;
            initializeDescription();
        }
    }


    @Override
    public void update() {
        super.update();
        //if (AbstractDungeon.player != null)
        //    this.magicNumber = this.baseMagicNumber = AbstractDungeon.player.maxOrbs/2;
    }
}
