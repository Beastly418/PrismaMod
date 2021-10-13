package PrismaMod.cards.ChloeCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeUncommonDefend extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeUncommonDefend.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/SnowSuit.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

        private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 2;
    private static final int BLOCK = 15;
    private static final int UP_BLOCK = 5;


    // /STAT DECLARATION/

    public ChloeUncommonDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.block = this.baseBlock = BLOCK;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            initializeDescription();
            upgradeBlock(UP_BLOCK);
        }
    }
}
