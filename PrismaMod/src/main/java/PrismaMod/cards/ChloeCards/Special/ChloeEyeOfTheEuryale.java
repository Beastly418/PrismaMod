package PrismaMod.cards.ChloeCards.Special;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.powers.Chloe.ChloeStunPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeEyeOfTheEuryale extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeEyeOfTheEuryale.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/EyeOfTheEuryale.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 2;
    private static final int UP_COST = 1;
    private static final int DAMAGE = 8;
    private static final int UP_DAMAGE = 3;

    // /STAT DECLARATION/

    public ChloeEyeOfTheEuryale() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        //this.exhaust = true;
        this.damage = this.baseDamage = DAMAGE;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
        if(m.type != AbstractMonster.EnemyType.BOSS && m.type != AbstractMonster.EnemyType.ELITE)
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new ChloeStunPower(m, 1)));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            initializeDescription();
            upgradeBaseCost(UP_COST);
            upgradeDamage(UP_DAMAGE);
        }
    }
}
