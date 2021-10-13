package PrismaMod.cards.ChloeCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.makeCardPath;

public class ChloeCommonAttack extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ChloeCommonAttack.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Chloe/Chloe13.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chloe.Enums.COLOR_CHLOE_RED;

    private static final int COST = 1;

    private static final int DAMAGE = 6;
    private static final int UP_DAMAGE = 3;


    // /STAT DECLARATION/

    public ChloeCommonAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = this.damage = DAMAGE;
        this.tags.add(BaseModCardTags.BASIC_STRIKE); //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
        this.tags.add(CardTags.STRIKE);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, this.damageType)));
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
