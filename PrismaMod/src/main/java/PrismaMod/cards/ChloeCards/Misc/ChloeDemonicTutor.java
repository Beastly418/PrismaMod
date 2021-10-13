package PrismaMod.cards.ChloeCards.Misc;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Chloe.AddCardFromGridSelectAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.relics.PrismaModTraceOnUpgradeRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeDemonicTutor extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeDemonicTutor.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/ChloeRacer2.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 1;
    private final int NUM_CARDS = 1;
    private final int NUM_CARDS_UP = 1;


    // /STAT DECLARATION/

    public ChloeDemonicTutor() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = NUM_CARDS;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToTop(new AddCardFromGridSelectAction(p, p.drawPile, this.magicNumber, ""));

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