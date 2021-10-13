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

public class ChloeRuleBreaker extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeRuleBreaker.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/Rule_breaker.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 2;
    private static final int UP_COST = 2;
    private static final int DAMAGE = 1;


    // /STAT DECLARATION/

    public ChloeRuleBreaker() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        //this.exhaust = true;
        this.baseDamage = this.damage = DAMAGE;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage)));
        if(m.type != AbstractMonster.EnemyType.BOSS)
            m.powers.clear();
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            initializeDescription();
            upgradeBaseCost(UP_COST);
        }
    }
}
