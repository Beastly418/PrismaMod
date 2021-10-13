package PrismaMod.cards.IllyaCards.Misc;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Misc.BrideAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaBride extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IllyaBride.class.getSimpleName());
    public static final String IMG = makeCardPath("Illya/Bride.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;


    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;

    public static final int COST = 1;
    private static final int UPGRADED_COST = 0;


    public IllyaBride(){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaustOnUseOnce = true;
        this.baseMagicNumber = this.magicNumber = 2;

        if(upgraded){
            isInnate = true;
            retain = true;
        }

    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isInnate = true;
            retain = true;
            upgradeBaseCost(UPGRADED_COST);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if(upgraded)
            this.retain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new BrideAction(p, magicNumber));
        } else {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
        }
    }

}
