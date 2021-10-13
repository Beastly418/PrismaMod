package PrismaMod.cards.IllyaCards.Normal;

import PrismaMod.actions.CardActions.Illya.Normal.PartialAssassinAction;
import PrismaMod.characters.Illya;
import PrismaMod.powers.PartialInstallAssassinPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaPartialInstallAssassin extends CustomCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaPartialInstallAssassin.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/PartialInstallAssassin.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 0;
    private static final int UPGRADE_COST = 0;
    private static final int DAMAGE = 2;
    private static final int UPGRADE_PLUS_DMG = 2;

    // /STAT DECLARATION/

    public IllyaPartialInstallAssassin() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        // Aside from baseDamage/MagicNumber/Block there's also a few more.
        // Just type this.base and let intelliJ auto complete for you, or, go read up AbstractCard

        baseDamage = damage = DAMAGE;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new PartialAssassinAction(p, damage));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PartialInstallAssassinPower(p, p, 1, damage)));//,1, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if(this.upgraded){
            this.retain = true;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            this.retain = true;
            //upgradeBaseCost(UPGRADE_COST);
            //this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
