package PrismaMod.cards.IllyaCards.Misc;

import PrismaMod.actions.CardActions.Illya.Misc.GrailKunAction;
import PrismaMod.characters.Illya;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.unique.AttackFromDeckToHandAction;
import com.megacrit.cardcrawl.actions.unique.SkillFromDeckToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;

import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaGrailKun extends CustomCard {

    public static final String ID = DefaultMod.makeID(IllyaGrailKun.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/GrailKun.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;
    //private static final int DAMAGE = 1;


    public IllyaGrailKun() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        //baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //Choose one attack and skill card from your deck and add it to your hand Maybe remake this into a single action
        if(p instanceof Illya) {
            AbstractDungeon.actionManager.addToTop(new GrailKunAction(p, 1));
        } else {
            AbstractDungeon.actionManager.addToTop(new AttackFromDeckToHandAction(1));
            AbstractDungeon.actionManager.addToTop(new SkillFromDeckToHandAction(1));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
