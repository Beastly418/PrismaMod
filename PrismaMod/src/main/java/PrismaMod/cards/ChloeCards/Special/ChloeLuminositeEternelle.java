package PrismaMod.cards.ChloeCards.Special;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

@Deprecated //cant find good art
public class ChloeLuminositeEternelle extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeLuminositeEternelle.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/Zwei.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 3;


    // /STAT DECLARATION/

    public ChloeLuminositeEternelle() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {

    }
}
