package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.actions.CardActions.Illya.Orbs.UpgradeOrbsAction;
import PrismaMod.characters.Illya;
import PrismaMod.powers.UpgradeOrbsPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import PrismaMod.DefaultMod;
import com.megacrit.cardcrawl.powers.FocusPower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaUpgradeOrbs extends CustomCard {

    public static final String ID = DefaultMod.makeID(IllyaUpgradeOrbs.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/UpgradeOrbs.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 3;
    private static final int UP_Cost = 2;

    public IllyaUpgradeOrbs() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.showEvokeValue = true;
        //this.exhaust = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new UpgradeOrbsAction(p));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new UpgradeOrbsPower(p, p, 1)));//,1, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FocusPower(p, 1)));//,1, AbstractGameAction.AttackEffect.NONE));
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
