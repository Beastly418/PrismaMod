package PrismaMod.cards.MiyuCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Miyu;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.DefaultMod.makeCardPath;

public class MiyuUberAOEAttack extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(MiyuUberAOEAttack.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Miyu/UberAOEAttack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final CardColor COLOR = Miyu.Enums.COLOR_MIYU_BLUE;
    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UP_DAMAGE = 5;
    private static final int ENERGY = 2;


    // /STAT DECLARATION/

    public MiyuUberAOEAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = ENERGY;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new EnergizedPower(p, 2)));
        //AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
        AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, new int[]{damage, damage, damage, damage}, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new VulnerablePower(m, this.magicNumber, false)));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            initializeDescription();
            upgradeDamage(UP_DAMAGE);
        }
    }
}