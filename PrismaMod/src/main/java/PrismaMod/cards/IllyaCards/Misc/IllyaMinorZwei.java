package PrismaMod.cards.IllyaCards.Misc;

import PrismaMod.DefaultMod;
import PrismaMod.actions.CardActions.Illya.Normal.CommonAttackAction;
import PrismaMod.actions.CardActions.Illya.Normal.MinorZweiAction;
import PrismaMod.cards.IllyaCards.Normal.IllyaCheapDefend;
import PrismaMod.characters.Illya;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static PrismaMod.DefaultMod.enablePlaceholder;
import static PrismaMod.DefaultMod.makeCardPath;

//Add burns to hand give big block for one turn
public class IllyaMinorZwei extends CustomCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IllyaMinorZwei.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Illya/MinorZwei.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = Illya.Enums.COLOR_ILLYA_PINK;

    private static final int COST = 0;
    private static final int BLOCK = 15;
    private static final int UPGRADE_BLOCK = 5;
    private static final int NUM_BURNS = 2;
    private static final int UP_NUM_BURNS = -1;
    // /STAT DECLARATION/

    public IllyaMinorZwei() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        // Aside from baseDamage/MagicNumber/Block there's also a few more.
        // Just type this.base and let intelliJ auto complete for you, or, go read up AbstractCard

        this.baseBlock = this.block = BLOCK;
        this.baseMagicNumber = this.magicNumber = NUM_BURNS;
        //this.tags.add(BaseModCardTags.BASIC_STRIKE); //Tag your strike, defend and form (Wraith form, Demon form, Echo form, etc.) cards so that they function correctly.
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof Illya && !enablePlaceholder) {
            AbstractDungeon.actionManager.addToBottom(new MinorZweiAction(p, this.block, this.magicNumber));
        } else {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Burn(), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgraded = true;
            upgradeBlock(UPGRADE_BLOCK);
            upgradeMagicNumber(UP_NUM_BURNS);
            initializeDescription();
        }
    }
}