package PrismaMod.cards.ChloeCards.Special;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeGaeBolg extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeGaeBolg.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/GaeBolg.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int UP_DAMAGE = 10;
    private static final int LOSE = 1;
    private static final int UP_LOSE = 1;



    // /STAT DECLARATION/

    public ChloeGaeBolg() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = LOSE;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage)));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new FrailPower(m, this.magicNumber, false)));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new VulnerablePower(m, this.magicNumber, false)));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new WeakPower(m, this.magicNumber, false)));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            initializeDescription();
            upgradeDamage(UP_DAMAGE);
            //upgradeMagicNumber(UP_LOSE);
        }
    }
}
