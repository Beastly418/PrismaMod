package PrismaMod.cards.MiyuCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.cards.MiyuCards.Misc.MiyuChristmas;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Miyu;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.DefaultMod.makeCardPath;

public class MiyuCarnivalDefend extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(MiyuCarnivalDefend.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Miyu/CarnivalDefend.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Miyu.Enums.COLOR_MIYU_BLUE;
    private static final int COST = 1;
    private static final int BLOCK = 14;
    private static final int UPGRADE_PLUS_BLOCK = 9;
    private static final int NUM_DEX = 1;

    // /STAT DECLARATION/

    public MiyuCarnivalDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseBlock = this.block = BLOCK;
        this.baseMagicNumber = this.magicNumber = NUM_DEX;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            initializeDescription();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }
}