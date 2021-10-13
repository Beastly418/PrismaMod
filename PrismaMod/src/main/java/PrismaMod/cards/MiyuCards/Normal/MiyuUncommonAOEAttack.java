package PrismaMod.cards.MiyuCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Miyu;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.DefaultMod.makeCardPath;

public class MiyuUncommonAOEAttack extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(MiyuUncommonAOEAttack.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Miyu/UncommonAOEAttack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final CardColor COLOR = Miyu.Enums.COLOR_MIYU_BLUE;
    private static final int COST = 2;
    private static final int DAMAGE = 16;
    private static final int UP_DAMAGE = 7;
    private static final int FRAIL = 1;
    private static final int UP_FRAIL = 1;


    // /STAT DECLARATION/

    public MiyuUncommonAOEAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.damage = this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = FRAIL;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
            //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mon, mon, new FrailPower(mon, this.magicNumber, false)));
            AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(mon, p));
        }
        AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, new int[]{damage, damage, damage, damage}, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            initializeDescription();
            //upgradeMagicNumber(UP_FRAIL);
            upgradeDamage(UP_DAMAGE);
        }
    }
}
