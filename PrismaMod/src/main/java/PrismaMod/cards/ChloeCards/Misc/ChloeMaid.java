package PrismaMod.cards.ChloeCards.Misc;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.powers.MaidPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeMaid extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeMaid.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/Maid.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 1;
    private static final int UP_COST = 0;


    // /STAT DECLARATION/

    public ChloeMaid() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new MaidPower(p, p, 1)));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            initializeDescription();
            upgradeBaseCost(UP_COST);
        }
    }
}
