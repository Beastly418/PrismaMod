package PrismaMod.cards.MiyuCards.Orbs;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Miyu;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.DefaultMod.makeCardPath;

public class MiyuEvokeAllAttack extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(MiyuEvokeAllAttack.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Miyu/EvokeAllAttack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final CardColor COLOR = Miyu.Enums.COLOR_MIYU_BLUE;
    private static final int COST = 2;
    private static final int UP_COST = 1;
    private static final int DAMAGE = 6;
    private static final int UP_DAMAGE = 3;
    private static final int NUM_TIMES = 4;


    // /STAT DECLARATION/

    public MiyuEvokeAllAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.showEvokeValue = true;
        this.damage = this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = NUM_TIMES;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; i++)
            AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
        AbstractDungeon.actionManager.addToTop(new EvokeAllOrbsAction());
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            initializeDescription();
            upgradeDamage(UP_DAMAGE);
            //upgradeBaseCost(UP_COST);
        }
    }
}