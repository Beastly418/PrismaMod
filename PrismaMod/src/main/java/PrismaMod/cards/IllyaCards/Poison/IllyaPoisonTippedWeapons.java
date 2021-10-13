package PrismaMod.cards.IllyaCards.Poison;

import PrismaMod.actions.CardActions.Illya.Poison.PoisonTippedWeaponsAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;
import com.megacrit.cardcrawl.powers.EnvenomPower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaPoisonTippedWeapons extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaPoisonTippedWeapons.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/PoisonTippedWeapons.png");

    public static final String NAME = cardStrings.NAME;
    public static String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 2;

    // /STAT DECLARATION/

    public IllyaPoisonTippedWeapons() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        //this.TEXT[2] = "Power";
        this.magicNumber = this.baseMagicNumber = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new PoisonTippedWeaponsAction(p));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnvenomPower(p, 1)));//, 1, AbstractGameAction.AttackEffect.NONE));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {

            upgradeName();
            this.retain = true;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
