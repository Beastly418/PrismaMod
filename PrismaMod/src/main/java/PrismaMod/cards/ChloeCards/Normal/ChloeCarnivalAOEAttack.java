package PrismaMod.cards.ChloeCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Chloe.ExhaustCardFromGridSelectAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.cards.ChloeCards.ChloeCarnivalCard;
import PrismaMod.characters.Chloe;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeCarnivalAOEAttack extends ChloeCarnivalCard {

    //Exhaust X number of cards equal to how many enemies you hit and gain shivs
    public static final String ID = DefaultMod.makeID(ChloeCarnivalAOEAttack.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/CarivalAOE.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UP_DAMAGE = 3;

    private int numMonsters;

    // /STAT DECLARATION/

    public ChloeCarnivalAOEAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        try {
            this.numMonsters = AbstractDungeon.getMonsters().monsters.size();
        } catch (NullPointerException e) {
            this.numMonsters = 1;
        }
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, new int[]{this.damage, this.damage, this.damage, this.damage, this.damage}, this.damageType, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToTop(new ExhaustCardFromGridSelectAction(p, p.hand, 2, "TESTSTRING", this.numMonsters, this));
        //AbstractDungeon.actionManager.addToTop(new ExhaustCardAction(p, 2, "TESTSTRING", this.numMonsters));
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
