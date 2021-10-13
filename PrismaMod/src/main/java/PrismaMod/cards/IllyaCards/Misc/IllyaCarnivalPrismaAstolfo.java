package PrismaMod.cards.IllyaCards.Misc;

import PrismaMod.actions.CardActions.Illya.Misc.AstolfoAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Illya;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaCarnivalPrismaAstolfo extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaCarnivalPrismaAstolfo.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/CarnivalPrismaAstolfo.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static int COST = 0;
    private static int VALUE_UP = 2;
    private static int UPGRADE_VAL = 1;

    private int value;
    private int baseVal;
    private boolean upgradedVal = false;

    // /STAT DECLARATION/

    public IllyaCarnivalPrismaAstolfo() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = this.baseVal = this.value = VALUE_UP;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new AstolfoAction(p, value));
        } else {
            int rand = (int) (Math.random()*5);
            switch(rand) {
                case 0:
                    AbstractDungeon.actionManager.addToTop(new HealAction(p, p, value));
                    break;
                case 1:
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, value)));
                    break;
                case 2:
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, value)));
                    break;
                case 3:
                    p.increaseMaxHp(value, true);
                    break;
                case 4:
                    p.gainGold(value * 10);
                    break;
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeVal(UPGRADE_VAL);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void upgradeVal(int amt) {
        value = value + amt;
        upgradedVal = true;
        this.upgradeMagicNumber(amt);
    }


}
