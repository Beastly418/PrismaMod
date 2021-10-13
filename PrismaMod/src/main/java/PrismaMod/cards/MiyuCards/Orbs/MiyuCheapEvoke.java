package PrismaMod.cards.MiyuCards.Orbs;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Miyu;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class MiyuCheapEvoke extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(MiyuCheapEvoke.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Miyu/CheapEvoke.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Miyu.Enums.COLOR_MIYU_BLUE;
    private static final int COST = 0;



    // /STAT DECLARATION/

    public MiyuCheapEvoke() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.showEvokeValue = true;
        if(upgraded){
            //isInnate = true;
            retain = true;
        }
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new DiscardPileToHandAction(1));
        //AbstractDungeon.actionManager.addToTop(new DrawCardAction(1));
        AbstractDungeon.actionManager.addToTop(new EvokeOrbAction(1));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            isInnate = true;
            retain = true;
        }
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if(upgraded)
            this.retain = true;
    }
}
