package PrismaMod.cards.MiyuCards.Orbs;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.cards.MiyuCards.Normal.MiyuUncommonStrBuff;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Miyu;
import PrismaMod.powers.OneTurnFocusPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.DefaultMod.makeCardPath;

public class MiyuChannelDefendEvoke extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(MiyuChannelDefendEvoke.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Miyu/ChannelDefendEvoke.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Miyu.Enums.COLOR_MIYU_BLUE;
    private static final int COST = 2;
    private static final int BLOCK = 3;
    private static final int UP_BLOCK = 2;
    private static final int NUM_TIMES = 3;

    // /STAT DECLARATION/

    public MiyuChannelDefendEvoke() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        //this.showEvokeValue = true;
        this.baseBlock = this.block = BLOCK;
        this.baseMagicNumber = this.magicNumber = NUM_TIMES;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; i++)
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber)));
        AbstractDungeon.actionManager.addToTop(new DecreaseMaxOrbAction(1));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            initializeDescription();
            upgradeBlock(UP_BLOCK);
        }
    }
}

