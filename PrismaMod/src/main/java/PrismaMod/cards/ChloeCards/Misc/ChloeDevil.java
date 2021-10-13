package PrismaMod.cards.ChloeCards.Misc;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeDevil extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeDevil.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/ChloeDevil.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 1;

    private int DAMAGE = 5;
    private int UPGRADED_DAMAGE = 2;
    private int HEAL = 2;
    private int UPGRADED_HEAL = 2;


    // /STAT DECLARATION/

    public ChloeDevil() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.exhaust = true;
        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = HEAL;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
        AbstractDungeon.actionManager.addToTop(new HealAction(p, p, this.magicNumber));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            this.upgraded = true;
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeMagicNumber(UPGRADED_HEAL);
        }
    }
}
