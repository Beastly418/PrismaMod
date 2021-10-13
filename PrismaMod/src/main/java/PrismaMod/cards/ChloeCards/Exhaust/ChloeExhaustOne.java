package PrismaMod.cards.ChloeCards.Exhaust;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Chloe.ExhaustCardFromGridSelectAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.cards.ChloeCards.Normal.ChloeShiv;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeExhaustOne extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeExhaustOne.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/ChloeOilPaint.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 1;
    private static final int NUMSHIVS = 2;
    private static final int UP_NUMSHIVS = 1;


    // /STAT DECLARATION/

    public ChloeExhaustOne() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = NUMSHIVS;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new ExhaustCardFromGridSelectAction(p, p.drawPile, this.magicNumber, "", 1));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            initializeDescription();
            upgradeMagicNumber(UP_NUMSHIVS);
        }
    }
}