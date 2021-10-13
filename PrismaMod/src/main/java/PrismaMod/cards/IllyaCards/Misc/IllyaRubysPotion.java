package PrismaMod.cards.IllyaCards.Misc;

import PrismaMod.actions.CardActions.Illya.Misc.RubysPotionAction;
import PrismaMod.characters.Illya;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaRubysPotion extends CustomCard {

    public static final String ID = DefaultMod.makeID(IllyaRubysPotion.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/RubysPotion.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 1;
    private static final int UpCost = 0;
    private static final int UPGRADE_PLUS_HEAL = 5;

    private static final int HEAL = 5;


    public IllyaRubysPotion() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = this.heal = HEAL;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new RubysPotionAction(p, this.heal));
        } else {
            AbstractDungeon.actionManager.addToTop(new HealAction(p, p, this.heal));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UpCost);
            //upgradeHeal(UPGRADE_PLUS_HEAL);
            initializeDescription();
        }
    }

    public void upgradeHeal(int amt){
        this.heal = this.heal + amt;
        upgradeMagicNumber(amt);
    }
}
