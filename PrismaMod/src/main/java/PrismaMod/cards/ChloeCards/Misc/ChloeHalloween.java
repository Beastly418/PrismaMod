package PrismaMod.cards.ChloeCards.Misc;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeHalloween extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeHalloween.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/Halloween2.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 1;
    private final int NUM_CARDS = 1;
    private final int NUM_CARDS_UP = 1;


    // /STAT DECLARATION/
                                //Trick or Treat
    public ChloeHalloween() {   //Get a random curse(2 curse) or random card(0 cost cards)
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = NUM_CARDS;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        double rand  = Math.random();
        if(rand < .4) {
            for(int i = 0; i < this.magicNumber; i++) {
                p.hand.addToHand(AbstractDungeon.returnRandomCurse());
            }
        } else {
            for(int i = 0; i < this.magicNumber; i++) {
                AbstractCard c = AbstractDungeon.returnTrulyRandomCard();
                c.cost = 0;
                p.hand.addToHand(c);
            }
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeMagicNumber(NUM_CARDS_UP);
        }
    }
}
