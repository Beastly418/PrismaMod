package PrismaMod.cards.MiyuCards.Misc;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Misc.DevilAction;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.characters.Chloe;
import PrismaMod.characters.Illya;
import PrismaMod.characters.Miyu;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class MiyuDevil extends CustomCard {
    public static final String ID = DefaultMod.makeID(MiyuDevil.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Miyu/Devil.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final CardColor COLOR = Miyu.Enums.COLOR_MIYU_BLUE;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 4;

    private static final int Draw = 2;


    public MiyuDevil() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = this.draw = Draw;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToTop(new DevilAction(p, m, damage, damageType, draw));
        } else {
            AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, draw));
        }

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
