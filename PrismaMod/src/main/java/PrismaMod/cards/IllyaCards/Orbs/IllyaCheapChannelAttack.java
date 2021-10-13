package PrismaMod.cards.IllyaCards.Orbs;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Normal.CarnivalAttackAction;
import PrismaMod.cards.IllyaCards.Normal.IllyaCarnivalAttack;
import PrismaMod.characters.Illya;
import PrismaMod.orbs.IllyaAttackOrb;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

public class IllyaCheapChannelAttack extends CustomCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaCheapChannelAttack.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/CheapChannelAttack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int NUM_ORBS = 1;

    // /STAT DECLARATION/

    public IllyaCheapChannelAttack() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        // Aside from baseDamage/MagicNumber/Block there's also a few more.
        // Just type this.base and let intelliJ auto complete for you, or, go read up AbstractCard


        this.baseMagicNumber = this.magicNumber = NUM_ORBS;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaAttackOrb(), true));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new IllyaAttackOrb(), true));
        }

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UP_COST);
            initializeDescription();
        }
    }
}
