package PrismaMod.cards.ChloeCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.cards.ChloeCards.ChloeCarnivalCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeCarnivalDefend extends ChloeCarnivalCard {
    public static final String ID = DefaultMod.makeID(ChloeCarnivalDefend.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/SwimSuit.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 1;
    private static final int BLOCK = 12;
    private static final int UP_BLOCK = 3;
    private static final int DEX = 1;


    // /STAT DECLARATION/

    public ChloeCarnivalDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseBlock = this.block = BLOCK;
        this.baseMagicNumber = this.magicNumber = DEX;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber)));

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
