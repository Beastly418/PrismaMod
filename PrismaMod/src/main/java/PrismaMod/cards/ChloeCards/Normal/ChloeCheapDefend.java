package PrismaMod.cards.ChloeCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Chloe.ExhaustCardAction;
import PrismaMod.actions.CardActions.Chloe.ExhaustCardFromGridSelectAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeCheapDefend extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeCheapDefend.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/Chloe3.png");

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
    private static final int UPGRADE_BLOCK = 1;
    private static final int EXHAUST = 1;
    private static final int NUM_SHIVS = 2;


    // /STAT DECLARATION/

    public ChloeCheapDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.defaultBaseSecondMagicNumber = this.defaultSecondMagicNumber = NUM_SHIVS;
        this.block = this.baseBlock = BLOCK;
        this.baseMagicNumber = this.magicNumber = EXHAUST;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
        if(upgraded) {
            AbstractDungeon.actionManager.addToTop(new ExhaustCardFromGridSelectAction(p, p.hand, this.defaultSecondMagicNumber, "", this.magicNumber, this));
            //AbstractDungeon.actionManager.addToTop(new ExhaustCardAction(p, this.defaultSecondMagicNumber, "TESTSTRING", this.magicNumber));
            //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new DexterityPower(p, this.magicNumber)));
        }
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }
}
