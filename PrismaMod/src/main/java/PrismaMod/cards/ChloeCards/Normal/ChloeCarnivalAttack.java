package PrismaMod.cards.ChloeCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.cards.ChloeCards.ChloeCarnivalCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeCarnivalAttack extends ChloeCarnivalCard {
    public static final String ID = DefaultMod.makeID(ChloeCarnivalAttack.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/CarnivalAttack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 1;
    private static final int DAMAGE = 12;
    private static final int UP_DAMAGE = 3;
    private static final int STRENGTH = 1;


    // /STAT DECLARATION/

    public ChloeCarnivalAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = this.damage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = STRENGTH;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage)));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            initializeDescription();
            upgradeDamage(UP_DAMAGE);
        }
    }
}
