package PrismaMod.cards.IllyaCards.Normal;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Normal.CheapAttackAction;
import PrismaMod.actions.CardActions.Illya.Normal.CommonAttackAction;
import PrismaMod.characters.Illya;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaCheapAttack extends CustomCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaCheapAttack.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/CheapAttack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 0;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int NUM_WEAK = 1;
    private static final int UP_NUM_WEAK = 1;

    // /STAT DECLARATION/

    public IllyaCheapAttack() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        // Aside from baseDamage/MagicNumber/Block there's also a few more.
        // Just type this.base and let intelliJ auto complete for you, or, go read up AbstractCard

        baseDamage = damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = NUM_WEAK;

        //this.tags.add(BaseModCardTags.BASIC_STRIKE); //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
        this.tags.add(AbstractCard.CardTags.STRIKE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToBottom(new CheapAttackAction(p, m, damage, damageTypeForTurn, this.magicNumber));
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)); // The animation the damage action uses to hit.
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false)));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UP_NUM_WEAK);
            initializeDescription();
        }
    }
}
