package PrismaMod.cards.IllyaCards.Poison;

import PrismaMod.actions.CardActions.Illya.Poison.StunPoisonAction;
import PrismaMod.characters.Illya;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaStunPoison extends CustomCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaStunPoison.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/StunPoison.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 3;


    // /STAT DECLARATION/

    public IllyaStunPoison() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        // Aside from baseDamage/MagicNumber/Block there's also a few more.
        // Just type this.base and let intelliJ auto complete for you, or, go read up AbstractCard

        baseDamage = damage = DAMAGE;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new StunPoisonAction(p, m, damage, damageType));
        } else {
            //Deal 9 damage if they are poisoned then deal 9 more
            AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));//, AbstractGameAction.AttackEffect.NONE)); // The animation the damage action uses to hit.
            if (m.hasPower(PoisonPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));//, AbstractGameAction.AttackEffect.NONE)); // The animation the damage action uses to hit.
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
