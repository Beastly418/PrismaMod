package PrismaMod.cards.ChloeCards.Special;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeCaladbolg extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeCaladbolg.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/caladbolg.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int NUM_HITS = 2;
    private static final int UP_DAMAGE = 2;
    private static final int UP_NUM_HITS = 1;

    // /STAT DECLARATION/

    public ChloeCaladbolg() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = NUM_HITS;
        this.damage = this.baseDamage = DAMAGE;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; i++)
            AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, new int[]{this.damage, this.damage, this.damage, this.damage, this.damage}, this.damageType, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            upgradeName();
            initializeDescription();
            upgradeDamage(UP_DAMAGE);
            upgradeMagicNumber(UP_NUM_HITS);
        }
    }
}
