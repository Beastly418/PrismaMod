package PrismaMod.cards.ChloeCards.Misc;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeReshuffleDiscard extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeReshuffleDiscard.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/Wink.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 1;
    private int NUM_DRAW = 2;
    private int NUM_DRAW_UP = 2;


    // /STAT DECLARATION/

    public ChloeReshuffleDiscard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = NUM_DRAW;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {  //Shuffle your discard pile into you deck and draw 2(4) cards
        AbstractDungeon.actionManager.addToTop(new DiscardPileToTopOfDeckAction(p));
        p.drawPile.shuffle();
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.magicNumber));

    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            initializeDescription();
            upgradeMagicNumber(NUM_DRAW_UP);
        }
    }
}