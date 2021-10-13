package PrismaMod.cards.IllyaCards.Poison;

import PrismaMod.actions.CardActions.Illya.Poison.BlindPoisonTossAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.unique.BouncingFlaskAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaBlindPoisonToss extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaBlindPoisonToss.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/BlindPoisonToss.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 1;
    private static final int POISON_AMOUNT = 2;
    private static final int POISON_UPGRADE = 1;
    private static final int NUM_BOUNCE = 3;

    private static int poisonAmount;
    private static boolean upgradedPoison = false;


    // /STAT DECLARATION/

    public IllyaBlindPoisonToss() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = NUM_BOUNCE;
        this.defaultBaseSecondMagicNumber = defaultSecondMagicNumber = poisonAmount = POISON_AMOUNT;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new BlindPoisonTossAction(p, this.poisonAmount, this.magicNumber));
        } else {
            AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
            AbstractDungeon.actionManager.addToBottom(new BouncingFlaskAction(randomMonster, this.poisonAmount, this.magicNumber));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradePoison(POISON_UPGRADE);
            initializeDescription();
        }
    }

    public void upgradePoison(int amt) {
        poisonAmount += amt;
        upgradedPoison = true;
        upgradeDefaultSecondMagicNumber(amt);
    }
}
