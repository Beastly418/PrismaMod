package PrismaMod.cards.ChloeCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.relics.PrismaModTraceOnRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeCheapDefendTwo extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeCheapDefendTwo.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/Chloe4.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 0;
    private static final int BLOCK = 3;
    private static final int UPGRADED_BLOCK = 3;
    //private static final int PLATED = 2;


    // /STAT DECLARATION/

    public ChloeCheapDefendTwo() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.block = this.baseBlock = this.BLOCK;
        //this.baseMagicNumber = this.magicNumber = this.PLATED;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
        //DefaultMod.logger.info(((Chloe)p).chloeItemHolder.traceCards.size());
        //DefaultMod.logger.info("Trace Card size: " + ((PrismaModTraceOnRelic)p.getRelic(PrismaModTraceOnRelic.ID)).traceCards.size());
        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new PlatedArmorPower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            initializeDescription();
            upgradeBlock(UPGRADED_BLOCK);
        }
    }
}