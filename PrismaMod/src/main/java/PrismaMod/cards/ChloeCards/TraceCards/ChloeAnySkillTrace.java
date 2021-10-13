package PrismaMod.cards.ChloeCards.TraceCards;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Chloe.TraceAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.cards.ChloeCards.AbstractTraceCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Map;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeAnySkillTrace extends AbstractTraceCard {
    public static final String ID = DefaultMod.makeID(ChloeAnySkillTrace.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/TraceOnSkill.png"); //Default


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private ArrayList<AbstractCard> cards = new ArrayList<>(20);

    // /TEXT DECLARATION/

    private CardType fakeType = CardType.SKILL;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;


    // /STAT DECLARATION/

    public ChloeAnySkillTrace() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.getCards();
        AbstractDungeon.actionManager.addToTop(new TraceAction(p, this.cards));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }

    private void getCards() {
        this.cards = new ArrayList<>(20);
        for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            if (c.getValue().type == this.fakeType && !(c.getValue() instanceof AbstractTraceCard)) {
                cards.add(c.getValue());
            }
        }
    }
}
