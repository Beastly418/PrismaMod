package PrismaMod.cards.ChloeCards.Special;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeRhongomyniad extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeRhongomyniad.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/Rhongomyniad.png");

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
    private static final int DAMAGE = 10;
    private static final int UP_DAMAGE = 10;




    // /STAT DECLARATION/

    public ChloeRhongomyniad() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = this.damage = DAMAGE;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage)));
        m.loseBlock();
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
