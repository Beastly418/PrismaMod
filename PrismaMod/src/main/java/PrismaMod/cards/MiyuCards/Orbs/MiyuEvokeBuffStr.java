package PrismaMod.cards.MiyuCards.Orbs;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Chloe.ExhaustCardFromGridSelectAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Miyu;
import PrismaMod.powers.OneTurnStrPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.unique.ExhaustAllNonAttackAction;
import com.megacrit.cardcrawl.actions.unique.RandomCardFromDiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.DefaultMod.makeCardPath;

public class MiyuEvokeBuffStr extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(MiyuEvokeBuffStr.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Miyu/EvokeBuffStr.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Miyu.Enums.COLOR_MIYU_BLUE;
    private static final int COST = 1;
    private static final int UP_COST = 1;
    private static final int NUM_STR = 1;
    private static final int UPGRADE_NUM_STR = 1;


    // /STAT DECLARATION/

    public MiyuEvokeBuffStr() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.showEvokeValue = true;
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = 25;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new OneTurnStrPower(p, this.magicNumber)));
        //AbstractDungeon.actionManager.addToTop(new EvokeOrbAction(1));
        //AbstractDungeon.actionManager.addToNextCombat(new ReducePowerAction(p, p, StrengthPower.POWER_ID, this.magicNumber));
        //for(int i = 0; i < this.magicNumber; i++)
        //    AbstractDungeon.actionManager.addToTop(new RandomCardFromDiscardPileToHandAction());
        AbstractDungeon.actionManager.addToTop(new ExhaustAllNonAttackAction());
        AbstractDungeon.actionManager.addToTop(new GainGoldAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            initializeDescription();
            upgradeMagicNumber(25);
            //upgradeBaseCost(UP_COST);
        }
    }
}