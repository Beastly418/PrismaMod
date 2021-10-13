package PrismaMod.cards.ChloeCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeShiv extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeShiv.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/Arrow.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private int DAMAGE = 4;
    private int UPGRADE_DAMAGE = 2;


    // /STAT DECLARATION/

    public ChloeShiv() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = this.damage = DAMAGE;
        this.exhaust = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded){
            upgradeName();
            initializeDescription();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }
}