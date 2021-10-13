package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.actions.CardActions.Illya.Orbs.AssassinRevengeAction;
import PrismaMod.characters.Illya;
import PrismaMod.powers.AssassinRevengePower;
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

public class IllyaAssassinRevenge extends CustomCard {

    public static final String ID = DefaultMod.makeID(IllyaAssassinRevenge.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/AssassinRevenge.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 2;
    private static final int UP_Cost = 1;
    private static final int POISON_APPLY = 1;

    public IllyaAssassinRevenge() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.showEvokeValue = true;
        this.magicNumber = this.baseMagicNumber = POISON_APPLY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new AssassinRevengeAction(p, magicNumber));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new AssassinRevengePower(p, p, 1, magicNumber)));//,1, AbstractGameAction.AttackEffect.NONE));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UP_Cost);
            initializeDescription();
        }
    }
}
