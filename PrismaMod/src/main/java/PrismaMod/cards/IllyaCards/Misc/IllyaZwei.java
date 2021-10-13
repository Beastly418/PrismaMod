package PrismaMod.cards.IllyaCards.Misc;

import PrismaMod.actions.CardActions.Illya.Misc.ZweiAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import PrismaMod.orbs.IllyaZweiOrb;
import PrismaMod.powers.AbstractInstallPower;
import PrismaMod.powers.Illya.IllyaZweiDebuffPower;
import PrismaMod.powers.Illya.IllyaZweiPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaZwei extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaZwei.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/Zwei.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    public static int STRENGTHUP = 8;
    public static int ENERGYGAIN = 13;
    public static int NUMDRAW = 5;

    public static int STRENGTHDOWN = -99;
    public static int VULNERABLEUP = 99;

    private static final int COST = 3;


    // /STAT DECLARATION/

    public IllyaZwei() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.exhaust = true;

        this.magicNumber = this.baseMagicNumber = STRENGTHUP;
        this.defaultBaseSecondMagicNumber = this.defaultSecondMagicNumber = NUMDRAW;
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder)
        AbstractDungeon.actionManager.addToTop(new ZweiAction(p));
        if(p.hasPower(IllyaZweiPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new DamageAction(p, new DamageInfo(p, 9999, DamageInfo.DamageType.NORMAL)));
        }

        for (int i = 0; i < p.powers.size(); i++) {
            if (AbstractInstallPower.class.isAssignableFrom(p.powers.get(i).getClass())) {
                p.powers.remove(i);
            }
        }

        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IllyaZweiPower(p, p, 1)));//,1, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToTop(new EvokeAllOrbsAction());
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, STRENGTHUP)));//, 1, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(ENERGYGAIN));
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, NUMDRAW));

        this.magicNumber = p.maxOrbs;
        for (int i = 0; i < p.maxOrbs; i++) {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new IllyaZweiOrb(),true));
        }
        AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new IllyaZweiDebuffPower(p, p, 1)));

    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}
