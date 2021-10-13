package PrismaMod.cards.IllyaCards.Poison;

import PrismaMod.actions.CardActions.Illya.Poison.FriendMakerAction;
import PrismaMod.characters.Illya;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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

public class IllyaFriendMaker extends CustomCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaFriendMaker.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/FriendMaker.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int POISON_AMOUNT = 2;
    private static final int UPGRADE_PLUS_POISON = 4;
    private int poisonAmount;
    private boolean poisonUpgraded = false;


    // /STAT DECLARATION/

    public IllyaFriendMaker() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        // Aside from baseDamage/MagicNumber/Block there's also a few more.
        // Just type this.base and let intelliJ auto complete for you, or, go read up AbstractCard

        baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = poisonAmount = POISON_AMOUNT;


    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new FriendMakerAction(p, m, poisonAmount, damage, damageType));
        } else {
            AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));//, AbstractGameAction.AttackEffect.NONE)); // The animation the damage action uses to hit.
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new PoisonPower(m, m, poisonAmount)));//,1, AbstractGameAction.AttackEffect.NONE));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradePoison(UPGRADE_PLUS_POISON);
            initializeDescription();
        }
    }

    public void upgradePoison(int amt){
        poisonAmount += amt;
        poisonUpgraded = true;
        upgradeMagicNumber(amt);
    }
}
